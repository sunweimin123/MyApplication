package com.example.sunweimin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;


public class MerchantSignUpActivity extends AppCompatActivity {

    Statement statement = null;
    ResultSet result = null;
    Connection conn;
    //商家注册
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marchant_signup);

        Button submit = (Button)findViewById(R.id.merchant_submit);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        EditText username = (EditText)findViewById(R.id.username);
                        EditText password = (EditText)findViewById(R.id.password);
                        String user = username.getText().toString();
                        String pwd = password.getText().toString();

                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection("jdbc:mysql:" + "//47.101.202.78:3306/AppSystem?characterEncoding=utf-8", "root", "sunweimin88");
                            Statement stmt = (Statement) conn.createStatement(); //创建Statement对象

                            String sql = "insert into merchant(mname,mpwd) values('"+user+"','"+pwd+"');";

                            stmt.executeUpdate(sql);//创建数据
                            stmt.close();
                            conn.close();
                            Intent intent = new Intent(MerchantSignUpActivity.this,MerchantLoginActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }




                    }
                });

            }
        });

        t.start();


    }
}
