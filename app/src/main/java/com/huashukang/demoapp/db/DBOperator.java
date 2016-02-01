package com.huashukang.demoapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

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
     * 关闭数据库
     */
    public synchronized  void close(){
        if(null!=helper){
            helper.close();
            helper =null;
        }
    }
}
