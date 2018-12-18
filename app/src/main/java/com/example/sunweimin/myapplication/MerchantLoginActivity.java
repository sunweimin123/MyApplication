package com.example.sunweimin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class MerchantLoginActivity extends Activity {

    private Thread newThread;
    private int mid;
    //商家登录
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_login);

        Button metchant_sign_up = (Button)findViewById(R.id.merchant_sign_up);
        metchant_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MerchantLoginActivity.this, MerchantSignUpActivity.class);
                startActivity(intent);//执行该Intent
            }
        });

        Button metchant_log_in = (Button)findViewById(R.id.merchant_login_in);

        newThread = new Thread(new Runnable() {

            public void run() {
                //这里写入子线程需要做的工作
                metchant_log_in.setOnClickListener(new View.OnClickListener() {
                    @Override



                    public void onClick(View v) {

                        if(check()){
                            Intent intent = new Intent(MerchantLoginActivity.this, MerchantActivity.class);
                            intent.putExtra("mid", mid);
                            startActivity(intent);//执行该Intent
                        }else{
                            Toast.makeText(MerchantLoginActivity.this,"登录失败",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        newThread.start();

        //转换买家模式
        TextView merchant_type = (TextView) findViewById(R.id.user_type);
        merchant_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MerchantLoginActivity.this, UserLoginActivity.class);

                startActivity(intent);//执行该Intent
                finish();
            }
        });




    }





    public boolean check() {
        try {

            Connection conn;

            EditText username = (EditText)findViewById(R.id.username);
            EditText password = (EditText)findViewById(R.id.password);
            String user = username.getText().toString();
            String pwd = password.getText().toString();

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql:" + "//47.101.202.78:3306/AppSystem?characterEncoding=utf-8", "root", "sunweimin88");
            Statement stmt = (Statement) conn.createStatement(); //创建Statement对象
            String sql = "select * from merchant where mname ='"+user+"';";
            ResultSet rs = stmt.executeQuery(sql);//创建数据对


            while (rs.next()) {
                if(pwd.equals(rs.getString(3))){
                    mid = rs.getInt(1);
                    rs.close();
                    stmt.close();
                    conn.close();
                    return true;
                }
            }





        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "数据库链接失败\n" + e, Toast.LENGTH_LONG).show();
        }

        return false;
    }




}
