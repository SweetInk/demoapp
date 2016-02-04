package com.huashukang.demoapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.huashukang.demoapp.db.DBHelper;

public class LoginActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        button = (Button)findViewById(R.id.btn_login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                SQLiteOpenHelper dbHelper = new DBHelper(LoginActivity.this);
                SQLiteDatabase database = dbHelper.getReadableDatabase();
             //   dbHelper.getReadableDatabase();

                finish();
            }
        });
    }
}
