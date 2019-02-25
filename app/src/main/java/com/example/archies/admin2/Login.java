package com.example.archies.admin2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Login extends AppCompatActivity {

    Button signin;
    EditText mobile, passwrd;
    CheckBox c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signin = (Button) findViewById(R.id.btn_signin);
        mobile = (EditText) findViewById(R.id.mobile);
        passwrd = (EditText) findViewById(R.id.passwrd);
        c = (CheckBox) findViewById(R.id.chk);

        c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!c.isChecked()) {
                    passwrd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    passwrd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobile.getText().toString().length() == 0) {
                    Toast.makeText(Login.this, "Please enter your mobile number.", Toast.LENGTH_SHORT).show();
                } else if (passwrd.getText().toString().length() == 0) {
                    Toast.makeText(Login.this, "Please enter password.", Toast.LENGTH_SHORT).show();
                } else if(passwrd.getText().toString().equals("admin@glau") && mobile.getText().toString().equals("9411411785")){
                    new DataProcess2().execute();
                    Intent x=new Intent(Login.this,MainActivity.class);
                   startActivity(x);
                }
                else{
                    Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    class DataProcess2 extends AsyncTask<String, String, String> {
        ProgressDialog pd1;

        @Override
        protected void onPreExecute() {
            pd1 = new ProgressDialog(Login.this);
            pd1.setMessage("Logging you...");
            pd1.setCanceledOnTouchOutside(false);
            pd1.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("phone", mobile.getText().toString()));
            params.add(new BasicNameValuePair("password", passwrd.getText().toString()));

            DefaultHttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost("https://manika2mani.000webhostapp.com/login.php");
            InputStream inputStream = null;
            String result = null;

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity entity = httpResponse.getEntity();
                inputStream = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                result = sb.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e) {

                    }
                }

            }
            return result;

        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject obj = new JSONObject(s);
                JSONArray array = obj.getJSONArray("result");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    String name1 = object.getString("phone");
                    if (name1.equals("0")) {
                        String login_always = "";

                        Toast.makeText(Login.this, "Login failed.", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();



                    }
                }
            } catch (Exception e) {}
            pd1.dismiss();
            super.onPostExecute(s);

        }
    }
}


