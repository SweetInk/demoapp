package com.huashukang.demoapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huashukang.demoapp.db.DBOperator;
import com.huashukang.demoapp.pojo.PicEnity;
import com.huashukang.demoapp.pojo.UserEnity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;


/**
 * Created by Administrator on 2016/2/1.
 */
public class TakePhotoActivity extends AppCompatActivity {
    private static final int TAKE_PHOTO = 1;//拍照
    private static final int CROP_PHOTO = 3;
    private static final int SCALE = 5;//照片缩小比例
    private static final String TAG ="TakePhotoActivity";
    private ImageView picture;
    private TextView tvId,tvName,tvBedNo;

    private Uri imageUri,rl;
    private File rootPath;
    UserEnity userEnity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("患者图片采集");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvId = (TextView) findViewById(R.id.tv_id);
        tvBedNo = (TextView) findViewById(R.id.tv_bedno);
        picture = (ImageView) findViewById(R.id.picture);

        rootPath = new File(Environment.getExternalStorageDirectory()+File.separator+"hskpicCache");
         userEnity = (UserEnity) getIntent().getSerializableExtra("userbean");
        Toast.makeText(this,userEnity.id+","+userEnity.name+","+userEnity.bedno,Toast.LENGTH_SHORT).show();
        tvName.setText(userEnity.name);
        tvId.setText(String.valueOf(userEnity.id));
        tvBedNo.setText(String.valueOf(userEnity.bedno));
        if(!rootPath.exists()){
            rootPath.mkdir();
        }
        Log.i(TAG,rootPath.getAbsolutePath());
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){

                    case R.id.item_tkpic:

                        File outputInage = new File(rootPath, "out.jpg");
                        try {
                            if (!outputInage.exists()) {
                                outputInage.createNewFile();

                            }else {
                                // outputInage.
                                outputInage.delete();
                                //.d
                                outputInage.createNewFile();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        imageUri = Uri.parse("file:///storage/emulated/0/hskpicCache/out.jpg");
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, TAKE_PHOTO);//启动相机程序

                        break;
                    case R.id.item_upload:
                        Toast.makeText(TakePhotoActivity.this,"上传",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
       /* picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //指定照片保存路径（SD卡），out.jpg为一个临时文件，每次拍照后这个图片都会被替换
                File outputInage = new File(rootPath, "out.jpg");
                try {
                    if (!outputInage.exists()) {
                        outputInage.createNewFile();

                    }else {
                       // outputInage.
                        outputInage.delete();
                        //.d
                        outputInage.createNewFile();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imageUri = Uri.parse("file:///storage/emulated/0/hskpicCache/out.jpg");
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);//启动相机程序
            }
        });*/
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {

                   /* Intent intent = new Intent("com.android.camera.action.CROP");
                    //图片命名规则 userid_时间戳_bedno.jpg
                    File f = new File(rootPath,userEnity.id+new Date().getTime()+userEnity.bedno+".jpg");

                    Log.i(TAG,userEnity.id+"_"+new Date().getTime()+"_"+userEnity.bedno);

                    if(!f.exists())
                        try {
                            f.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    rl = Uri.fromFile(f);
                    intent.setDataAndType(imageUri, "image*//*");
                    intent.putExtra("scale", true);
                    intent.putExtra("crop", "true");
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    intent.putExtra("outputX", 84);
                    intent.putExtra("outputY", 84);
                    intent.putExtra("return-data", false);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, rl);
                    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                    intent.putExtra("noFaceDetection", true); // no face detection
                    startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序*/
                    try {
                        picture.setImageBitmap(BitmapFactory.decodeStream
                                (getContentResolver()
                                        .openInputStream(imageUri)));
                        Log.i(TAG,imageUri.getPath());
                        Toast.makeText(TakePhotoActivity.this,"Chenggong",Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG,"OK");
                    Toast.makeText(this,"ok_over",Toast.LENGTH_SHORT).show();
                    PicEnity picEnity = new PicEnity();
                    picEnity.userid = userEnity.id;
                    picEnity.path = rl.getPath();
                        //更新图片信息到数据库
                        DBOperator.getInstance().open(this).insertPicInfo(picEnity);

                    Bitmap bitmap = null;
                    if(rl!=null)
                    try {
                        bitmap = BitmapFactory.decodeStream
                             (getContentResolver()
                                 .openInputStream(rl));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    picture.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
                }
                break;
            default:
                break;


        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture, menu);
        return true;
    }
}

