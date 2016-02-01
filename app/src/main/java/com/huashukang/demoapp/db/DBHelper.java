package com.huashukang.demoapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SUCHU on 2016/2/1.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static  final int DB_VERSION = 1;
    private static final  String DB_NAME= "userdb";



    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, int version){
       // super();
        super(context,name,null,version);
    }
    //
    public DBHelper(Context context){
        this(context,DB_NAME,DB_VERSION);
        System.out.println("test.................");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("on Create a database");
        db.execSQL("create table userinfo(" +
                "_id Integer primary key Autoincrement," +
                "_name text not null," +
                "_bedno Integer not null)");
        db.execSQL("create table userpic(" +
                "_id Integer primary key Autoincrement," +
                "_path text not null," +
                "_userid Integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
