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
    private EditText edtName,edtBedno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBedSeatManage = (Button) findViewById(R.id.btn_bedseatmange);
        btnBedSeatManage = (Button) findViewById(R.id.btn_usermanage);
        edtName = (EditText) findViewById(R.id.edit_name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("MAIN UI");
        setSupportActionBar(toolbar);
        edtBedno = (EditText) findViewById(R.id.edit_bednumber);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ab_search:
                        Toast.makeText(MainActivity.this, "action_settings", 0).show();
                        break;
                    case R.id.ab_add:
                        Toast.makeText(MainActivity.this, "action_share", 0).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public void bedseatManage(View view){
        Toast.makeText(this,"bedseatManage",Toast.LENGTH_SHORT).show();

    }
    public void userManage(View view){
        Toast.makeText(this,"userManage",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,UserListActivity.class);
        startActivity(intent);
    }

    public void insertData(View view){
        Toast.makeText(this,"Insert data",Toast.LENGTH_SHORT).show();
        DBOperator operator =  DBOperator.getInstance();
        operator.open(this);
        UserEnity userEnity = new UserEnity();
        userEnity.name = edtName.getText().toString();
        userEnity.bedno = Integer.valueOf(edtBedno.getText().toString());
        operator.insertUser(userEnity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
       return true;
    }
}
