package com.huashukang.demoapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.huashukang.demoapp.pojo.PicEnity;
import com.huashukang.demoapp.pojo.UserEnity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SUCHU on 2016/2/1.
 */
public class DBOperator {
    private static DBOperator instance = null;
    private SQLiteOpenHelper helper =null;
    private DBOperator(){}

    /**
     * 获取数据库操作类实例
     * @return instance
     */
    public synchronized  static DBOperator getInstance(){

            if (null == instance) {
                instance = new DBOperator();
                // helper =
            }
            return instance;
        }

    /**
     * 打开数据库
     * @param context
     */
    public synchronized DBOperator open(Context context){
        close();
        helper = new DBHelper(context);
        return this;
    }

    /**
     * 插入用户信息
     * @param userEnity
     */
    public void insertUser(UserEnity userEnity){
        ContentValues contentValues = new ContentValues();
        contentValues.put("_name",userEnity.name);
        contentValues.put("_bedno",userEnity.bedno);
        helper.getWritableDatabase().insert("userinfo",null,contentValues);
    }

    /**
     * 删除用户
     * @param id
     */
    public void deleteUser(int id){
        Log.i("DELETE",id+"");
        helper.getWritableDatabase().delete("userinfo","_id=?",new String[]{id+""});
    }



    /**
     * 插入照片信息
     * @param picEnity
     * @throws Exception
     */
    public void insertPicInfo(PicEnity picEnity) throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_path",picEnity.path);
        contentValues.put("_userid",picEnity.userid);
        helper.getWritableDatabase().insert("userpic",null,contentValues);
    }

    /**
     * 获取用户信息
     * @return List
     */
    public List<UserEnity> getAllUser(){
        Cursor cursor =  helper.getReadableDatabase().query("userinfo",null,null,null,null,null,null);
        List<UserEnity > list = new ArrayList<>();
        Log.i("TAG",cursor.getCount()+"");
        while(cursor.moveToNext()){
            UserEnity userEnity = new UserEnity();
            userEnity.id = cursor.getInt(cursor.getColumnIndex("_id"));
            userEnity.name = cursor.getString(cursor.getColumnIndex("_name"));
            userEnity.bedno = cursor.getInt(cursor.getColumnIndex("_bedno"));
            list.add(userEnity);
        }
        return list;
    }

    /**
     * 获取用户照片信息
     * @return List
     */
    public List<PicEnity> getAllUserPicture(){
        Cursor cursor =  helper.getReadableDatabase().query("userpic",null,null,null,null,null,null);
        List<PicEnity > list = new ArrayList<>();
        while(cursor.moveToNext()){
            PicEnity picEnity = new PicEnity();
            picEnity.id = cursor.getInt(cursor.getColumnIndex("_id"));
            picEnity.path = cursor.getString(cursor.getColumnIndex("_path"));
            picEnity.userid = cursor.getInt(cursor.getColumnIndex("_userid"));
            list.add(picEnity);
        }
        return list;
    }

    /**
     * 判断床位号是否被占用
     * @param bid 床位号。
     * @return
     */
    public int checkBedNumUsed(int bid){
        Cursor cursor = helper.getReadableDatabase().query("userinfo",new String[]{"count(_bedno) as counts"},"_bedno=?",new String[]{bid+""},
        null,null,null,null);
        int c = 0;
     //   while(cursor.getCount())
       while(cursor.moveToNext()){
          c =  cursor.getInt(cursor.getColumnIndex("counts"));
        //   return c;
       }
        Log.i("TAG---》数量",c+"");
        return c;
    }

    /**
     * 关闭数据库
     */
    public synchronized  void close(){
        if(null!=helper){
            helper.close();
            helper =null;
        }
    }
}
