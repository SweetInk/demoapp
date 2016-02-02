package com.huashukang.demoapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.huashukang.demoapp.db.DBOperator;
import com.huashukang.demoapp.pojo.UserEnity;
import com.huashukang.demoapp.widget.DividerItemDecoration;
import com.huashukang.demoapp.widget.UserRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户列表
 */
public class UserListActivity extends AppCompatActivity  implements UserRecycleAdapter.IonSlidingViewClickListener{
    private RecyclerView recyclerView;
    private UserRecycleAdapter adapter;
    private final String TAG = "test";
    public static final int REQUSET = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("用户登记管理");

        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Toast.makeText(UserListActivity.this,"clicked",Toast.LENGTH_SHORT).show();
                switch (item.getItemId()){
                    case R.id.item_add:
                        Toast.makeText(UserListActivity.this,"Add new User",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserListActivity.this,UserAddActivity.class);
                     //   startActivity(intent);
                        startActivityForResult(intent,REQUSET);
                        break;


                }
                return true;
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.user_list);
        List<UserEnity> lists = DBOperator.getInstance().open(this).getAllUser();


        adapter = new UserRecycleAdapter(this);
        adapter.setLists(lists);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==0){
            Toast.makeText(this,"数据添加成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i(TAG,"点击项："+position);
    }

    @Override
    public void onDeleteBtnCilck(View view, int position) {
        Log.i(TAG,"删除项："+position);
        adapter.removeData(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_list, menu);
        return true;
    }
}
