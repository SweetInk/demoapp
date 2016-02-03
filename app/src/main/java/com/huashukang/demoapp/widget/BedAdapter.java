package com.huashukang.demoapp.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huashukang.demoapp.BedListAcitivity;
import com.huashukang.demoapp.R;
import com.huashukang.demoapp.pojo.UserEnity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/2.
 */
public class BedAdapter extends RecyclerView.Adapter<BedAdapter.MyViewHolder> {
   public List<UserEnity> list;
    private Context context;


    /**
     * 设置回掉函数
     */
     public  interface OnItemClickListener{
         void OnItemClick(View view, int position);
     }

    private OnItemClickListener monItemClickListener;

    public void setOnItemClickListener(OnItemClickListener monItemClickListener){
        this.monItemClickListener=monItemClickListener;
    }

    public BedAdapter(Context context, List<UserEnity> mInfoList) {
        this.context = context;
        this.list = mInfoList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bed, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int i) {
        holder.name.setText(list.get(i).name);
        holder.bedno.setText(list.get(i).bedno+"");
     //如果设置了回调，则设置点击事件
        if(monItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monItemClickListener.OnItemClick(holder.itemView,i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        // return mInfoList.size();
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bedno;
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.bed_name);
            bedno = (TextView)view.findViewById(R.id.bed_number);


        }
    }
}
