package com.huashukang.demoapp.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.huashukang.demoapp.R;
import com.huashukang.demoapp.pojo.UserEnity;

import java.util.List;

/**
 * Created by SUCHU on 2016/2/1.
 */
public class UserRecycleAdapter extends RecyclerView.Adapter<UserRecycleAdapter.UserInfo> {
    private List<UserEnity> lists;
    private OnClickListener listener;

    public void setLists(List lists){
        this.lists = lists;
    }

    @Override
    public UserInfo onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(),"Hello",Toast.LENGTH_SHORT).show();
            }
        });
        return new UserInfo(view);

    }

    @Override
    public void onBindViewHolder(UserInfo holder, int position) {
        UserEnity userEnity = lists.get(position);
        holder.name.setText(userEnity.name);
        holder.bedno.setText(String.valueOf(userEnity.bedno));
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return lists.size();
    }
    class UserInfo extends RecyclerView.ViewHolder{
        TextView name;
        TextView bedno;
        TextView edit,delete;
        public UserInfo(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            bedno = (TextView )itemView.findViewById(R.id.bedno);
            edit = (TextView) itemView.findViewById(R.id.tv_edit);
            delete = (TextView) itemView.findViewById(R.id.tv_del);
        }
        public  void deleteUser(View view){
            //view.getParent().get
        }
    }
    public static interface OnClickListener{
        public void onClick(View view,Object Data);
    }
    public  void deleteUser(View view){
        //view.getParent().get
    }
}
