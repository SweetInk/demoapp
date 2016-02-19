package com.huashukang.picgrab.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.huashukang.picgrab.R;
import com.huashukang.picgrab.adapter.BedAdapter;
import com.huashukang.picgrab.db.DBOperator;
import com.huashukang.picgrab.pojo.UserEnity;
import com.huashukang.picgrab.widget.DividerGridItemDecoration;
import com.huashukang.picgrab.widget.MyLayoutManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class BedListAcitivity extends AppCompatActivity {
    private List<UserEnity> infoList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private BedAdapter mAdapter;
    private PopupWindow pop = null;
    private LinearLayout props;
    private DBOperator dbOpreator;
    private Intent intent=null;


    private void Init() {

        pop = new PopupWindow(BedListAcitivity.this);
        pop.setAnimationStyle(R.style.anim_popup_dir);
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
        props = (LinearLayout) findViewById(R.id.ll_popup);
        pop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //pop.setFocusable(true);
        //pop.setOutsideTouchable(true);
        pop.setContentView(view);

        //RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);

        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);

                //cameraPhoto();
                pop.dismiss();
                //props.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("T","----");
                Intent intent2 =new Intent(BedListAcitivity.this,ChoosePhoto.class);
                startActivity(intent2);
                pop.dismiss();

            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
             //   props.clearAnimation();
            }
        });
    }


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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.iconfont_fanhui);

        mRecyclerView = (RecyclerView) findViewById(R.id.bed_list_id);
        infoList= DBOperator.getInstance().open(this).getAllUser();
        Log.i("error",String.valueOf(infoList.size()));
        Init();
        intent = new Intent(BedListAcitivity.this,TakePhotoActivity.class);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new MyLayoutManager(this,4));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerView.setAdapter(mAdapter=new BedAdapter(this,infoList));

        mAdapter.setOnItemClickListener(new BedAdapter.OnItemClickListener() {

            @Override
            public void OnItemClick(View view, int position,View oldView) {

//              if (pop.isShowing()) {
//                    pop.dismiss();
//                } else {

                pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                //}

                view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                if(oldView!=null){
                    oldView.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));
                }
                //Toast.makeText(BedListAcitivity.this,"POSITION:"+position,Toast.LENGTH_SHORT).show();

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

            }
        });
    }

    public void dismisspop(View v){
        if (pop.isShowing()) pop.dismiss();
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





