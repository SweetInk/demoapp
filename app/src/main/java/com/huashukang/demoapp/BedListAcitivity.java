package com.huashukang.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.huashukang.demoapp.db.DBOperator;
import com.huashukang.demoapp.pojo.UserEnity;
import com.huashukang.demoapp.widget.BedAdapter;
import com.huashukang.demoapp.widget.DividerGridItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class BedListAcitivity extends AppCompatActivity {
    private List<UserEnity> infoList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private BedAdapter mAdapter;
    private DBOperator dbOpreator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_list_acitivity);
        DBOperator dbOpreator=DBOperator.getInstance();
        dbOpreator.open(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("照片采集");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.bed_list_id);
        infoList= DBOperator.getInstance().open(this).getAllUser();
        Log.i("error",String.valueOf(infoList.size()));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerView.setAdapter(mAdapter=new BedAdapter(this,infoList));

        mAdapter.setOnItemClickListener(new BedAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                //Toast.makeText(BedListAcitivity.this,"POSITION:"+position,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(BedListAcitivity.this,TakePhotoActivity.class);
                UserEnity bean = new UserEnity();
                bean = infoList.get(position);
                UserEnity userEnity  = infoList.get(position);
                Bundle bundle = new Bundle();
                Log.i("size",infoList.size()+"");
                bundle.putSerializable("userbean",userEnity);
                bundle.putSerializable("lists",(Serializable)infoList);
                intent.putExtra("position",position);
                Log.i("userbaen",userEnity.name+"_"+userEnity.id+"_"+userEnity.bedno);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
