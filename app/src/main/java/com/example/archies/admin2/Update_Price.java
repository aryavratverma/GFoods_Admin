package com.example.archies.admin2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Update_Price extends AppCompatActivity {
private Button b;
EditText item_name;
EditText new_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__price);
        item_name=(EditText)findViewById(R.id.item2);
        new_price=(EditText)findViewById(R.id.price2);
        b=(Button)findViewById(R.id.btn3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DataProcess4().execute();
            }
        });
    }


    class DataProcess4 extends AsyncTask<String,String,String>{
        ProgressDialog pd3;
        @Override
        protected void onPreExecute() {
            pd3= new ProgressDialog(Update_Price.this);
            pd3.setMessage("Updating Menu...");
            pd3.setCanceledOnTouchOutside(false);
            pd3.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("fname",item_name.getText().toString()));
            params.add(new BasicNameValuePair("fprice",new_price.getText().toString()));

            DefaultHttpClient httpClient=new DefaultHttpClient();
            HttpPost httpPost= new HttpPost("https://manika2mani.000webhostapp.com/update_price.php");


            try{
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                httpClient.execute(httpPost);

                }
            catch (UnsupportedEncodingException e) {
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
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),"Price updated",Toast.LENGTH_SHORT).show();
            pd3.dismiss();
        }
    }
}
