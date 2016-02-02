package com.huashukang.demoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huashukang.demoapp.db.DBOperator;
import com.huashukang.demoapp.pojo.UserEnity;

public class MainActivity extends AppCompatActivity {
    private Button btnUserManage;
    private Button btnBedSeatManage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBedSeatManage = (Button) findViewById(R.id.btn_bedseatmange);
        btnBedSeatManage = (Button) findViewById(R.id.btn_usermanage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("功能选项");
        setSupportActionBar(toolbar);

    }

    public void bedseatManage(View view){
        Toast.makeText(this,"bedseatManage",Toast.LENGTH_SHORT).show();

    }
    public void userManage(View view){
        Toast.makeText(this,"userManage",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,UserListActivity.class);
        startActivity(intent);
    }




}
