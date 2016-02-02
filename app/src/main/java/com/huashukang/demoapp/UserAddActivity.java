package com.huashukang.demoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.huashukang.demoapp.db.DBOperator;
import com.huashukang.demoapp.pojo.UserEnity;

public class UserAddActivity extends AppCompatActivity {
    private EditText edtName,edtBedNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtBedNo = (EditText) findViewById(R.id.edt_bedno);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("用户登记");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_add_mesure:
                        if((edtName.getText().toString().equals("")||edtBedNo.getText().toString().equals(""))) {
                            Toast.makeText(UserAddActivity.this, "信息未填写完整", Toast.LENGTH_SHORT).show();
                        }else{
                            UserEnity userEnity = new UserEnity();
                            userEnity.name = edtName.getText().toString();
                            userEnity.bedno = Integer.valueOf(edtBedNo.getText().toString());
                            if(DBOperator.getInstance().open(UserAddActivity.this).checkBedNumUsed(userEnity.bedno)==0) {
                                DBOperator operator = DBOperator.getInstance();
                                operator.open(UserAddActivity.this);
                                operator.insertUser(userEnity);
                                Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                finish();
                            }else{
                                Toast.makeText(UserAddActivity.this, "该床位号已被使用", Toast.LENGTH_SHORT).show();
                            }
                        }break;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_add, menu);
        return true;
    }
}
