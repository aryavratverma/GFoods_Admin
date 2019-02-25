package com.example.archies.admin2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SMSactivity extends AppCompatActivity {
Button b;
TextView et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsactivity);
        b=(Button)findViewById(R.id.btnphn);
        et=(TextView) findViewById(R.id.phone);

        Bundle data_from_list= getIntent().getExtras();
        final String value_phn= data_from_list.getString("phone");
        final String value_id=data_from_list.getString("ID");
         et= (TextView) findViewById(R.id.phone);
        et.setText(value_phn);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},0);
            return;
        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsManager sms= SmsManager.getDefault();
                sms.sendTextMessage("+91"+value_phn,null,"Your order has been taken and your order ID is:"+value_id,null,null);
            }
        });


    }
}
