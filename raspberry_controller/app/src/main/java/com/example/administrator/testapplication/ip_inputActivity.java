package com.example.administrator.testapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.socket.R;

/**
 * Created by Administrator on 2022/3/31.
 */

public class ip_inputActivity  extends Activity{
    EditText edittext1,edittext2;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_input);
        edittext1=(EditText) findViewById(R.id.input_ip);
        edittext2=(EditText) findViewById(R.id.input_duankou);
        button=(Button)findViewById(R.id.entry);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=edittext1.getText().toString();
                String duankou=edittext2.getText().toString();
                Intent intent = new Intent(ip_inputActivity.this, MainActivity.class);
                intent.putExtra("ip",s);
                intent.putExtra("duankou",duankou);
                startActivity(intent);
            }
        });
       edittext1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               if(hasFocus){
                   edittext1.setHint(null);
               }
           }
       });
        edittext2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edittext1.setHint(null);
                }
            }
        });
    }
}
