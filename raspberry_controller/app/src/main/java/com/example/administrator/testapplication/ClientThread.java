package com.example.administrator.testapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.example.socket.R;

import static android.content.ContentValues.TAG;
import static com.example.administrator.testapplication.MainActivity.duankou;
import static com.example.administrator.testapplication.MainActivity.ip;

public class ClientThread implements Runnable  {
    private Socket s;
    // 定义向UI线程发送消息的Handler对象
    Handler handler;
    ImageView imageview ;
    // 定义接收UI线程的Handler对象
    Handler revHandler;
    // 该线程处理Socket所对用的输入输出流
    BufferedReader br = null;
    OutputStream os = null;


    public ClientThread(Handler handler) {
        this.handler = handler;
    }
    @SuppressLint("HandlerLeak") @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            s = new Socket();

            //Log.d("111111111111", "@@@@@@@@@@@@@@@@@@@@");
         	//s = new Socket("192.168.0.78", 8888);//此方法不能设定连接时限
            s.connect(new InetSocketAddress(ip,Integer.parseInt(duankou)));
            //Log.d("111111111111", "@@@@@@@@@@@@@@@@@@@@");
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = s.getOutputStream();
            // 启动一条子线程来读取服务器相应的数据
            new Thread() {
                @Override
                public void run() {
                    String content = null;
                    // 不断的读取Socket输入流的内容
                    try {
                        while ((content = br.readLine()) != null) {
                            // 每当读取到来自服务器的数据之后，发送的消息通知程序
                            // 界面显示该数据
                            Message msg = new Message();
                            msg.what = 0x123;
                            msg.obj = content;
                            handler.sendMessage(msg);
                            Log.d("1111111111111111", content);
                        }
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }

            }.start();
            // 为当前线程初始化Looper
            Looper.prepare();
            // 创建revHandler对象
            revHandler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    // 接收到UI线程的中用户输入的数据
                    if (msg.what == 0x345) {
                        // 将用户在文本框输入的内容写入网络
                        try {
                            os.write((msg.obj.toString())// + "\r\n")
                                    .getBytes("utf-8"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            };
            // 启动Looper
            Looper.loop();
        } catch (SocketTimeoutException e) {
            Message msg = new Message();
            msg.what = 0x123;
            msg.obj = "网络连接超时！";
            handler.sendMessage(msg);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

}
