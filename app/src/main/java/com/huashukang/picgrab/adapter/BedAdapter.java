package com.huashukang.picgrab.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huashukang.picgrab.R;
import com.huashukang.picgrab.pojo.UserEnity;

import java.util.List;

/**
 * Created by Administrator on 2016/2/2.
 */
public class BedAdapter extends RecyclerView.Adapter<BedAdapter.MyViewHolder> {
    public List<UserEnity> list;
    private Context context;
  //  private int oldPosition=-1;
    private View oldView=null;

    /**
     * 设置回掉函数
     */
     public  interface OnItemClickListener{
         void OnItemClick(View view, int position,View oldView);
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int i) {
        holder.name.setText(list.get(i).name);
        holder.bedno.setText(list.get(i).bedno+"");
        holder.itemView.setTag(i);
        if(monItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    monItemClickListener.OnItemClick(v,i,oldView);
                    oldView = v;
                }
            });
        }
      // holder.itemView.getParent();
//        if(monItemClickListener!=null){
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//                @Override
//                public void onClick(View v) {
//                    //monItemClickListener.OnItemClick(holder.itemView,i);
//                    if(i != oldPosition){
//                        if(oldView != null){
//                            oldView.setBackgroundColor(context.getResources().getColor(R.color.cardview_light_background));
//                        }
//                        oldView = v;
//                        oldPosition = i;
//                        monItemClickListener.OnItemClick(holder.itemView, i);
//                    }else{
//                        oldPosition = -1;
//                        oldView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
//                    }
//
//                }
//            });
        //}
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
