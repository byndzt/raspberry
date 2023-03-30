package com.example.administrator.testapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;

import com.example.socket.R;

/**
 * Created by Administrator on 2022/3/29.
 */

public class shouyeActivity extends Activity {
   ImageButton button1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shouye);
        button1=(ImageButton) findViewById(R.id.shouye_tiaozhuan);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(shouyeActivity.this,ip_inputActivity.class);	//设置跳转到MainActivity的intent
                startActivity(intent);
            }
        });

    }
}
