package com.huashukang.picgrab.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huashukang.picgrab.R;


public class MainActivity extends AppCompatActivity {
    private Button btnUserManage;
    private Button btnBedSeatManage;
    private Button btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBedSeatManage = (Button) findViewById(R.id.btn_bedseatmange);
        btnBedSeatManage = (Button) findViewById(R.id.btn_usermanage);
        btnUpload = (Button)findViewById(R.id.btn_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        final TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("人像采集");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


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

    public void imgUpload(View view) {
        Intent intent2 = new Intent(this, ChoosePhoto.class);
        startActivity(intent2);

    }
}
