package com.huashukang.picgrab.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.huashukang.picgrab.R;


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
        toolbar.setTitle("人像采集");
        setSupportActionBar(toolbar);
    }
    public void bedseatManage(View view){
      //  Toast.makeText(this,"bedseatManage",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,BedListAcitivity.class);
        startActivity(intent);
    }
    public void userManage(View view){
      //  Toast.makeText(this,"userManage",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,UserListActivity.class);
        startActivity(intent);
    }

    public void imgUpload() {
        Intent intent2 = new Intent(this, ChoosePhoto.class);
        startActivity(intent2);

    }
}
