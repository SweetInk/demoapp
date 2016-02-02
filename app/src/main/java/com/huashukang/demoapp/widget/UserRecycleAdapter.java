package com.huashukang.demoapp.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huashukang.demoapp.R;
import com.huashukang.demoapp.db.DBOperator;
import com.huashukang.demoapp.pojo.UserEnity;
import com.huashukang.demoapp.utils.Utils;

import java.util.List;

/**
 * Created by SUCHU on 2016/2/1.
 */
public class UserRecycleAdapter extends RecyclerView.Adapter<UserRecycleAdapter.UserInfo>  implements SlidingButtonView.IonSlidingButtonListener  {
    private List<UserEnity> lists;
    //private OnClickListener listener;

    private Context mContext;
    private SlidingButtonView mMenu = null;
    private IonSlidingViewClickListener mIDeleteBtnClickListener;
    private RecyclerView mRecyclerView;

    public UserRecycleAdapter(Context context){
        mContext = context;
        mIDeleteBtnClickListener = (IonSlidingViewClickListener) context;


    }

    public void setLists(List lists){
        this.lists = lists;
    }

    @Override
    public UserInfo onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"position:"+1,0).show();
            }
        });
        return new UserInfo(view);

    }



    @Override
    public void onBindViewHolder(final UserInfo holder, int position) {
        UserEnity userEnity = lists.get(position);
       // holder.name.setText(userEnity.name);
      //  holder.bedno.setText(String.valueOf(userEnity.bedno));

        holder.textView.setText(lists.get(position).name);
        holder.tv.setText(lists.get(position).bedno+"");
        //设置内容布局的宽为屏幕宽度
        holder.layout_content.getLayoutParams().width = Utils.getScreenWidth(mContext);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"position:"+holder.getLayoutPosition(),0).show();
            }
        });
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    int n = holder.getLayoutPosition();
                    mIDeleteBtnClickListener.onItemClick(v, n);
                }

            }


        });
        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = holder.getLayoutPosition();
                mIDeleteBtnClickListener.onDeleteBtnCilck(v, n);
            }
        });



    }

  //  public void setOnClickListener(OnClickListener listener){
    //    this.listener = listener;
 //   }


    @Override
    public int getItemCount() {
        return lists.size();
    }
    class UserInfo extends RecyclerView.ViewHolder{
      //  TextView name;
      //  TextView bedno;
      public TextView textView,tv;
        public TextView btn_Delete;
        public ViewGroup layout_content;

        public UserInfo(View itemView) {
            super(itemView);
            btn_Delete = (TextView) itemView.findViewById(R.id.tv_delete);
            layout_content = (ViewGroup) itemView.findViewById(R.id.layout_content);
            textView = (TextView) itemView.findViewById(R.id.text);
            tv = (TextView) itemView.findViewById(R.id.textView);
            // name = (TextView) itemView.findViewById(R.id.name);
        //    bedno = (TextView )itemView.findViewById(R.id.bedno);
            ((SlidingButtonView) itemView).setSlidingButtonListener(UserRecycleAdapter.this);

        }

    }

    public void addData(int position) {
      //  mDatas.add(position, "添加项");
        notifyItemInserted(position);
    }

    public void removeData(int position){
        //lists.get(position).id

       DBOperator.getInstance().open(mContext).deleteUser(  lists.get(position).id);
        lists.remove(position);
        notifyItemRemoved(position);

    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    /**
     * 滑动或者点击了Item监听
     * @param slidingButtonView
     */
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if(menuIsOpen()){
            if(mMenu != slidingButtonView){
                closeMenu();
                Log.i("TAG,","Closed");
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }
    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if(mMenu != null){
            return true;
        }
        Log.i("asd","mMenu为null");
        return false;
    }



    public static interface OnClickListener{
       public void onClick(View view,Object Data);
    }

    public interface IonSlidingViewClickListener {
        void onItemClick(View view,int position);
        void onDeleteBtnCilck(View view,int position);
    }




}
