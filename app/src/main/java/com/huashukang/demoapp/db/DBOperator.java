package com.huashukang.demoapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.huashukang.demoapp.pojo.UserEnity;

/**
 * Created by SUCHU on 2016/2/1.
 */
public class DBOperator {
    private static DBOperator instance = null;
    private SQLiteOpenHelper helper =null;
    private DBOperator(){}
    public synchronized  static DBOperator getInstance(){

            if (null == instance) {
                instance = new DBOperator();
                // helper =
            }
            return instance;
        }


    public synchronized void open(Context context){
        close();
        helper = new DBHelper(context);
    }

    public void insertUser(UserEnity userEnity){
        ContentValues contentValues = new ContentValues();
        contentValues.put("_name",userEnity.name);
        contentValues.put("_bedno",userEnity.bedno);
        helper.getWritableDatabase().insert("userinfo",null,contentValues);
    }


    public synchronized  void close(){
        if(null!=helper){
            helper.close();
            helper =null;
        }
    }
}
