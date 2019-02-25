package com.example.archies.admin2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

public class Order_Status extends AppCompatActivity {
    TextView order_id, status, name, total, item_qty;
    Button b;
    ListView listView;

    String url = "https://manika2mani.000webhostapp.com/getOrderDetails.php";
    ProgressDialog pd;
    ArrayList<String> a1 = new ArrayList<String>();
    ArrayAdapter<String> onj;
    String JSONData;
    String contact,temp[];
    JSONArray array;
    String phone_temp[];
    String idNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__status);

            listView = findViewById(R.id.list1);
        final SwipeRefreshLayout swipeRefreshLayout= (SwipeRefreshLayout)findViewById(R.id.refresh);
            onj=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,a1);
            listView.setAdapter(onj);
        if(ActivityCompat.checkSelfPermission(Order_Status.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Order_Status.this,new String[]{Manifest.permission.SEND_SMS},0);
            return;
        }

          new BackgroundTask().execute();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    phone_temp=a1.get(i).split("\n");
String phoneNo=phone_temp[2];
phoneNo=phoneNo.substring(10);
 idNo=phone_temp[0];
idNo=idNo.substring(5);


                    SmsManager sms= SmsManager.getDefault();
                   sms.sendTextMessage(phoneNo,null,"Your order has been taken and your order ID is "+idNo,null,null);
                    //Intent in=new Intent(Order_Status.this,SMSactivity.class);
                    //in.putExtra("phone",phoneNo);
                    //in.putExtra("ID",idNo);
                    //startActivity(in);
                    Toast.makeText(getApplicationContext(),"Message has been sent",Toast.LENGTH_LONG).show();
                   new DataProcess1().execute();

                    }

            });

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(true);
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                            int min=65;
                            int max=95;
                            Random random=new Random();
                            int var=random.nextInt(max-min+1)+min;

                        }
                    },3000);

                }



            });



        }







    class DataProcess1 extends AsyncTask<String,String,String>
    {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd=new ProgressDialog(Order_Status.this);
            pd.setMessage("Uploading...");
            pd.setCanceledOnTouchOutside(false);
            pd.setButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            //to communicate with server we need three things :URL,method(get or post) (mainly post) and gata to be send

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id",idNo));
            // params.add(new BasicNameValuePair("contact",phone));  //key ("jo isme likhna h") should be same as the php file variables name
            //params.add(new BasicNameValuePair("address",address));


            DefaultHttpClient httpClient=new DefaultHttpClient();  //httpclient se connect
            HttpPost httpPost=new HttpPost("https://manika2mani.000webhostapp.com/update_status.php");   //to open the url for posting on server
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params));   //to send entity
                httpClient.execute(httpPost);  //to execute
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            Toast.makeText(getApplicationContext(), "Status Updated successfully :)", Toast.LENGTH_LONG).show();

            super.onPostExecute(s);
        }
    }




        class BackgroundTask extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                pd = new ProgressDialog(Order_Status.this);
                pd.setMessage("Fetching Data...");
                pd.setCanceledOnTouchOutside(false);
                pd.show();

                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... strings) {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setHeader("Content-Type", "application/json");
                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity entity = httpResponse.getEntity();
                    inputStream = entity.getContent();

                    //jsonis utf-8 by default

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {

                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }

                    } catch (Exception e) {

                    }

                }

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                JSONData = s;
                try {
                    showRecord();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pd.dismiss();
                super.onPostExecute(s);
            }
        }

        public void showRecord() throws JSONException {
            JSONObject object = new JSONObject(JSONData);
             array = object.getJSONArray("result");
            String a;



            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                String id = obj.getString("id");
                String name = obj.getString("name");
                contact = obj.getString("contact");
                String address = obj.getString("items_qty");
                String status = obj.getString("status");
                String price = obj.getString("price");

                a1.add("ID : " + id + "\nName : " + name + "\nContact : " + contact + "\nItems_qty : " + address + "\nprice : " + price + "\nStatus : " + status);
                onj.notifyDataSetChanged();
            }

        }

    }

