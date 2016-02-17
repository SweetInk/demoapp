package com.huashukang.picgrab.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.huashukang.picgrab.R;
import com.huashukang.picgrab.adapter.UserRecycleAdapter;
import com.huashukang.picgrab.db.DBOperator;
import com.huashukang.picgrab.pojo.UserEnity;
import com.huashukang.picgrab.widget.DividerItemDecoration;

import java.util.List;

/**
 * 用户列表
 */
public class UserListActivity extends AppCompatActivity  implements UserRecycleAdapter.IonSlidingViewClickListener {
    private RecyclerView recyclerView;
    private UserRecycleAdapter adapter;
    private final String TAG = "test";
    public static final int REQUSET_ADD = 1;
    public static  final int REQUEST_UPDATE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("患者登记");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Toast.makeText(UserListActivity.this,"clicked",Toast.LENGTH_SHORT).show();
                switch (item.getItemId()){
                    case R.id.item_add:
                    //    Toast.makeText(UserListActivity.this,"Add new User",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserListActivity.this,UserAddActivity.class);
                     //   startActivity(intent);
                        startActivityForResult(intent,REQUSET_ADD);
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
        if(resultCode==RESULT_OK && requestCode==REQUSET_ADD){
            Toast.makeText(this,"数据添加成功",Toast.LENGTH_SHORT).show();
            adapter.setLists(DBOperator.getInstance().open(this).getAllUser());
            adapter.notifyDataSetChanged();
        }else if(resultCode==RESULT_OK && requestCode==REQUEST_UPDATE){
            Toast.makeText(this,"更新成功",Toast.LENGTH_SHORT).show();
            adapter.setLists(DBOperator.getInstance().open(this).getAllUser());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i(TAG,"点击项："+position);
        Intent intent = new Intent(this,UserDetailAcitivity.class);
        intent.putExtra("name",adapter.lists.get(position).name);
        intent.putExtra("bno",adapter.lists.get(position).bedno);
        intent.putExtra("id",adapter.lists.get(position).id);
        startActivityForResult(intent,REQUEST_UPDATE);
    }

    @Override
    public void onDeleteBtnCilck(View view, int position) {
        Log.i(TAG,"删除项："+position);
        adapter.removeData(position);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_list, menu);
        return true;
    }
}
