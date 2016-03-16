package com.huashukang.picgrab.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by SUCHU on 2016/2/16.
 */
public class HttpUtils {
    private Context context;
    private CallBack callBack;
    public String url="";

    private String port;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x01:
                   callBack.OnSuccess();
                    break;
                case 0x02:

                   callBack.OnFailed("",new Exception("远程服务器未响应"));
            }
        }
    };
    //private static final String URL="http://192.168.2.22:8080/MyFileServer/ImgUpload";
    public  void upload(File file,CallBack callBack){
        this.callBack = callBack;
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        try {
            URL url = new URL(this.url);
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Charset","UTF-8");
                connection.setRequestProperty("connection","keep-alive");
                connection.setRequestProperty("Content-Type",CONTENT_TYPE+";boundary="+BOUNDARY);
                if(null!=file){
                    Log.i("HttpUtils",file.getAbsolutePath());
                    DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(PREFIX);
                    stringBuffer.append(BOUNDARY);
                    stringBuffer.append(LINE_END);
                    stringBuffer.append("Content-Disposition:form-data; name=\"uploadfile\";filename=\""+file.getName()+"\""+LINE_END);
                    stringBuffer.append("Content-Type:application/octet-stream; charset=UTF-8"+LINE_END);
                    stringBuffer.append(LINE_END);
                    output.write(stringBuffer.toString().getBytes());
                    InputStream inputStream = new FileInputStream(file);
                    byte [ ] bytes = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(bytes))!=-1){
                        output.write(bytes,0,len);
                    }
                    inputStream.close();
                    output.write(LINE_END.getBytes());
                    byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                    output.write(end_data);
                    output.flush();
                    int res = connection.getResponseCode();
                    Log.i("HttpUtils","response code:"+res);
                    if(res ==200){
                        Log.i("SUCCESS","OK");
                        handler.sendEmptyMessage(0x01);
                    }else{
                        handler.sendEmptyMessage(0x02);
                        //callBack.OnFailed(":"+res,new Exception("response code:"+res));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();

                handler.sendEmptyMessage(0x02);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(0x02);

        }
    }
    //回调接口
    public interface CallBack {
        void OnSuccess();

        void OnFailed(String string, Exception e);
    }
}
