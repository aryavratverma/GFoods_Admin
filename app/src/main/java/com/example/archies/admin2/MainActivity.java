package com.example.archies.admin2;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    NavigationView navigationView;
    TextView order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        order=(TextView)findViewById(R.id.order);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView=(NavigationView)findViewById(R.id.nav_head);
        navigationView.setNavigationItemSelectedListener(this);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this,Order_Status.class);
                startActivity(in);
            }
        });

    }

        public void onBackPressed(){
             if(drawer.isDrawerOpen(GravityCompat.START)){
                 drawer.closeDrawer(GravityCompat.START);
             }
             else {
                 super.onBackPressed();}
        }

        public boolean onNavigationItemSelected(MenuItem item){
        int id=item.getItemId();
        if (id==R.id.db1){
            Intent a=new Intent(MainActivity.this, Add_Item.class);
            startActivity(a);
        }
        if (id==R.id.db2){

            Intent b=new Intent(MainActivity.this,Remove_Item.class);
            startActivity(b);
        }
        if(id==R.id.db3){
            Intent d= new Intent(MainActivity.this,Update_Price.class);
            startActivity(d);
        }


        if(id==R.id.db5){
            Intent c = new Intent(MainActivity.this,Login.class);
            c.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(c);
            Toast.makeText(getApplicationContext(),"Logging out...",Toast.LENGTH_SHORT).show();

        }

        return false;

        }



    }

