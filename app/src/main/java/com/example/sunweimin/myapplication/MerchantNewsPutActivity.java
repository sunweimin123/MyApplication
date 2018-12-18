package com.example.sunweimin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class MerchantNewsPutActivity extends AppCompatActivity {
    //兼职信息发布


    int mid;
    ResultSet result = null;
    Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_news_put);

        mid = (getIntent()).getIntExtra("mid",0);



        Button submit = (Button)findViewById(R.id.submit);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        try {
                            EditText majorr = ((EditText)findViewById(R.id.major));
                            String major = majorr.getText().toString();


                            String salary = ((EditText)findViewById(R.id.salary)).getText().toString();

                            String company = ((EditText)findViewById(R.id.company)).getText().toString();

                            String email = ((EditText)findViewById(R.id.email)).getText().toString();

                            String tel = ((EditText)findViewById(R.id.tel)).getText().toString();

                            String request = ((EditText)findViewById(R.id.request)).getText().toString();




                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection("jdbc:mysql:" + "//47.101.202.78:3306/AppSystem?characterEncoding=utf-8", "root", "sunweimin88");
                            Statement stmt = (Statement) conn.createStatement(); //创建Statement对象

                            String sql = "insert into news(major,salary,company,email,tel,reques,mid) values('"+major+"','"+salary+"','"+company+"','"+email+"','"+tel+"','"+request+"','"+mid+"');";

                            stmt.executeUpdate(sql);//创建数据
                            stmt.close();
                            conn.close();

                            Intent intent = new Intent(MerchantNewsPutActivity.this,MerchantActivity.class);
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



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.merchant, menu);
        return true;
    }

    //menu的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.merchant_put:

                break;
            case R.id.merchant_recru:
                Intent i = new Intent(MerchantNewsPutActivity.this,MerchantRecruChecActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.merchant_check:
                Intent i1 = new Intent(MerchantNewsPutActivity.this,MerchantActivity.class);
                startActivity(i1);
                finish();
                break;

        }
        return true;
    }


}
