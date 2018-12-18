package com.example.sunweimin.myapplication;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class MerchantStateModiActivity extends AppCompatActivity {

    //商家兼职状态修改
    int nid;
    int mid;
    int sid;
    int lid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_state_modi);

        Intent intent = getIntent();
        nid = intent.getIntExtra("nid",0);
        mid = intent.getIntExtra("mid",0);
        sid = intent.getIntExtra("sid",0);
        lid = intent.getIntExtra("lid",0);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {



                TextView title = (TextView)findViewById(R.id.tilte);
                TextView content = (TextView)findViewById(R.id.content);

                try {

                    Connection conn;




                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql:" + "//47.101.202.78:3306/AppSystem?characterEncoding=utf-8", "root", "sunweimin88");
                    Statement stmt = (Statement) conn.createStatement(); //创建Statement对象
                    String sql = "select * from news where nid ="+nid+";";
                    ResultSet rs = stmt.executeQuery(sql);//创建数据对


                    while (rs.next()) {Looper.prepare();

                        String nmajor = rs.getString(2);
                        String salary = rs.getString(3);
                        String company = rs.getString(4);
                        String  email = rs.getString(5);
                        String tel = rs.getString(6) ;
                        String reques = rs.getString(7) ;
                        title.setText(nmajor);
                        content.setText("公司："+nmajor+"\n" +
                                "工资："+salary+"\n" +
                                "邮箱："+email+"\n" +
                                "电话："+tel+"\n" +
                                "要求："+reques+"\n" +
                                "学生："+sid+"\n");
                    }
                }catch (Exception e) {
                    e.printStackTrace();

                }
            }});

        thread.start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                Button cancel = (Button)findViewById(R.id.button1);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try{
                            Connection conn;
                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection("jdbc:mysql:" + "//47.101.202.78:3306/AppSystem?characterEncoding=utf-8", "root", "sunweimin88");
                            Statement stmt = (Statement) conn.createStatement(); //创建Statement对象



                            String sql = "update link set state=1 where lid ="+lid+";";

                            stmt.executeUpdate(sql);//创建数据
                            stmt.close();
                            conn.close();
                            Intent intent = new Intent(MerchantStateModiActivity.this,MerchantRecruChecActivity.class);
                            intent.putExtra("mid",mid);
                            startActivity(intent);
                            finish();


                        }catch (Exception e){

                        }




                    }
                });
            }
        }).start();




        new Thread(new Runnable() {
            @Override
            public void run() {
                Button submit = (Button)findViewById(R.id.button2);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            Connection conn;
                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection("jdbc:mysql:" + "//47.101.202.78:3306/AppSystem?characterEncoding=utf-8", "root", "sunweimin88");
                            Statement stmt = (Statement) conn.createStatement(); //创建Statement对象



                            String sql = "update link set state=2 where lid ="+lid+";";

                            stmt.executeUpdate(sql);//创建数据
                            stmt.close();
                            conn.close();

                            Intent intent = new Intent(MerchantStateModiActivity.this,MerchantRecruChecActivity.class);
                            intent.putExtra("mid",mid);
                            startActivity(intent);
                            finish();


                        }catch (Exception e){

                        }
                    }
                });
            }
        }).start();







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
                Intent i2 = new Intent(MerchantStateModiActivity.this,MerchantNewsPutActivity.class);
                startActivity(i2);
                finish();
                break;
            case R.id.merchant_recru:
                Intent i = new Intent(MerchantStateModiActivity.this,MerchantRecruChecActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.merchant_check:
                Intent i1 = new Intent(MerchantStateModiActivity.this,MerchantActivity.class);
                startActivity(i1);
                finish();
                break;

        }
        return true;
    }













}
