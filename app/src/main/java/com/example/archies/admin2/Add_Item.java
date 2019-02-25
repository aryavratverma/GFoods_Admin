package com.example.archies.admin2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Add_Item extends AppCompatActivity {
    Button b;
    EditText price,avail,itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__item);
        b=(Button)findViewById(R.id.btn1);
        itemName=(EditText)findViewById(R.id.additem);
        price=(EditText)findViewById(R.id.addprice);
        avail=(EditText)findViewById(R.id.avail);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DataProcess().execute();


                Toast.makeText(getApplicationContext(),"Updating...",Toast.LENGTH_SHORT).show();
            }
        });
    }


    class DataProcess extends AsyncTask<String,String,String>{
ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            pd= new ProgressDialog(Add_Item.this);
            pd.setMessage("Updating Menu...");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            ArrayList<NameValuePair> params= new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("fname",itemName.getText().toString()));
            params.add(new BasicNameValuePair("fprice",price.getText().toString()));
            params.add(new BasicNameValuePair("fstatus",avail.getText().toString()));

            DefaultHttpClient httpClient=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost("https://manika2mani.000webhostapp.com/add_items.php");
            InputStream inputStream=null;
            String res=null;

            try{
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse httpResponse=httpClient.execute(httpPost);
                HttpEntity entity=httpResponse.getEntity();
                inputStream=entity.getContent();

                BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"),8);
                StringBuilder sb=new StringBuilder();
                String line=null;
                while((line=reader.readLine())!=null){
                    sb.append(line+"\n");

                }
                res=sb.toString().trim();
            }
            catch (Exception e){e.printStackTrace();}

            finally {
                if(inputStream!= null){
                    try{
                        inputStream.close();
                    }
                    catch (Exception e){}
                }
            }
            return  res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),"Item added successfully",Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
    }
}
