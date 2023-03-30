package com.example.administrator.testapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socket.R;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {
    Handler handler;
    int sizex = 0;
    // 定义与服务器通信的子线程
    ClientThread clientThread;
    private TextView show;
    private Button button1, button2, button3, button4;
    private InputStream reader;
    private OutputStream writer;
    private DataInputStream data;
    private ImageView imageview;
    private ImageView imageview_tupian;
    private ImageView imageview_back;
    private ImageView imageview_shuaxin;
    private final String IMAGE_TYPE = "image/*";
    private Canvas canvas;
    private SurfaceHolder holder;
    private final int IMAGE_CODE = 0;   //这里的IMAGE_CODE是自己任意定义的
    URL videoUrl;
    private int w;
    private int h;
    static String ip;
    static String duankou;
    HttpURLConnection conn;
    Bitmap bmp;
    private String url;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ip= getIntent().getStringExtra("ip");
        duankou=getIntent().getStringExtra("duankou");
        show = (TextView) findViewById(R.id.textview);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        imageview = (ImageView) findViewById(R.id.imageview);
        imageview_back = (ImageView) findViewById(R.id.imageview_back);
        imageview_shuaxin = (ImageView) findViewById(R.id.imageview_shuaxin);
        show.setText("这里显示与树莓派连接的信息：");

        show.setTextSize(30);
        imageview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, shouyeActivity.class);    //设置跳转到MainActivity的intent
                startActivity(intent);
            }
        });
        imageview_shuaxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "已重启！", Toast.LENGTH_SHORT).show();
                try {
                    // 当用户按下按钮之后，将用户输入的数据封装成Message
                    // 然后发送给子线程Handler
                    Message msg = new Message();
                    msg.what = 0x345;//连接线程通讯
                    //msg.obj = "Android 网络编程--Socket通信编程";
                    msg.obj = ("4");
                    clientThread.revHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, "已点击！", Toast.LENGTH_SHORT).show();
                try {
                    // 当用户按下按钮之后，将用户输入的数据封装成Message
                    // 然后发送给子线程Handler
                    Message msg = new Message();
                    msg.what = 0x345;//连接线程通讯
                    //msg.obj = "Android 网络编程--Socket通信编程";
                    msg.obj = ("1");
                    clientThread.revHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, "已点击！", Toast.LENGTH_SHORT).show();
                try {
                    // 当用户按下按钮之后，将用户输入的数据封装成Message
                    // 然后发送给子线程Handler
                    Message msg = new Message();
                    msg.what = 0x345;//连接线程通讯
                    //msg.obj = "Android 网络编程--Socket通信编程";
                    msg.obj = ("2");
                    clientThread.revHandler.sendMessage(msg);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(MainActivity.this, "已点击！", Toast.LENGTH_SHORT).show();
                // TODO Auto-generated method stub
                try {
                    // 当用户按下按钮之后，将用户输入的数据封装成Message
                    // 然后发送给子线程Handler
                    Message msg = new Message();
                    msg.what = 0x345;//连接线程通讯
                    //msg.obj = "Android 网络编程--Socket通信编程";
                    msg.obj = ("3");
                    clientThread.revHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        /**
         * 相册功能
         */
        button4.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Toast.makeText(MainActivity.this, "已点击！", Toast.LENGTH_SHORT).show();
                                           setImage();
                                       }
                                   }
        );
        handler = new Handler() {
            //private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //    @Override
                // 如果消息来自子线程
                if (msg.what == 0x123) {
                    // 将读取的内容追加显示在文本框中
                     show.append("\n" + msg.obj.toString());
                }
            }
        };
        clientThread = new ClientThread(handler);
        // 客户端启动ClientThread线程创建网络连接、读取来自服务器的数据
        new Thread(clientThread).start();
    }


    private void setImage() {
        // TODO Auto-generated method stub
        //使用intent调用系统提供的相册功能，使用startActivityForResult是为了获取用户选择的图片
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType(IMAGE_TYPE);
        startActivityForResult(getAlbum, IMAGE_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
            Log.e("TAG->onresult", "ActivityResult resultCode error");
            return;
        }
        Bitmap bm = null;
        //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();
        //此处的用于判断接收的Activity是不是你想要的那个
        if (requestCode == IMAGE_CODE) {
            try {
                Uri originalUri = data.getData();        //获得图片的uri
                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                //显得到bitmap图片
                imageview.setImageBitmap(bm);
                //    这里开始的第二部分，获取图片的路径：
                String[] proj = {MediaStore.Images.Media.DATA};
                //好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                //按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                //将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                //最后根据索引值获取图片路径
                String path = cursor.getString(column_index);
                //imgPath.setText(path);
            } catch (IOException e) {
                Log.e("TAG-->Error", e.toString());
            }
        }
    }

}

