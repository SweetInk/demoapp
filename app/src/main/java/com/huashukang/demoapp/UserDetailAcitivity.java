package com.huashukang.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.huashukang.demoapp.db.DBOperator;
import com.huashukang.demoapp.pojo.UserEnity;

/**
 * @params
 * Create by SUCHU
 */
public class UserDetailAcitivity extends AppCompatActivity {
    private EditText edtName,edtBedNo;
    private EditText id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_user_detail_acitivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("信息修改");
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_add_mesure:
                        // define
                        Toast.makeText(UserDetailAcitivity.this,"CLICKED",Toast.LENGTH_SHORT).show();
                        // .USER.
                        UserEnity userEnity = new UserEnity();
                        userEnity.id = Integer.valueOf(id.getText().toString());
                        userEnity.name = edtName.getText().toString();
                        userEnity.bedno = Integer.valueOf(edtBedNo.getText().toString());
                        DBOperator.getInstance().open(UserDetailAcitivity.this).updateUserinfo(userEnity);

                        Intent intent1 = new Intent();
                        setResult(RESULT_OK, intent1);
                        finish();



                        break;
                }
                return true;
            }
        });

        edtName = (EditText) findViewById(R.id.edt_name);
        edtBedNo = (EditText) findViewById(R.id.edt_bedno);
        id = (EditText) findViewById(R.id.edt_id);
        edtName.setText(intent.getStringExtra("name"));
        edtBedNo.setText(String.valueOf(intent.getIntExtra("bno",0)));
        id.setText(String.valueOf(intent.getIntExtra("id",0)));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_add, menu);
        return true;
    }

}
