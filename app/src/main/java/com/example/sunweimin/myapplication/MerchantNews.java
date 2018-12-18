package com.example.sunweimin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class MerchantNews extends AppCompatActivity {


    int nid;
    int mid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_news);



        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Intent in = getIntent();
                 nid = in.getIntExtra("nid",0);
                 mid = in.getIntExtra("mid",0);

                try {

                    Connection conn;

                    TextView title = (TextView)findViewById(R.id.title);
                    TextView content = (TextView)findViewById(R.id.content);


                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql:" + "//47.101.202.78:3306/AppSystem?characterEncoding=utf-8", "root", "sunweimin88");
                    Statement stmt = (Statement) conn.createStatement(); //创建Statement对象
                    String sql = "select * from news where nid ='"+nid+"';";
                    ResultSet rs = stmt.executeQuery(sql);//创建数据对

                    while (rs.next()) {
                        String nmajor = rs.getString(2);
                        String salary = rs.getString(3);
                        String company = rs.getString(4);
                        String  email = rs.getString(5);
                        String tel = rs.getString(6) ;
                        String reques = rs.getString(7) ;
                        title.setText(nmajor);
                        content.setText("公司："+company+"\n" +
                                "工资："+salary+"\n" +
                                "邮箱："+email+"\n" +
                                "电话："+tel+"\n" +
                                "要求："+reques+"\n");
                }
            }catch (Exception e) {
                    e.printStackTrace();

                }
        }});

        thread.start();


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
                Intent i2 = new Intent(MerchantNews.this,MerchantNewsPutActivity.class);
                i2.putExtra("mid",mid);
                startActivity(i2);
                finish();
                break;
            case R.id.merchant_recru:
                Intent i = new Intent(MerchantNews.this,MerchantRecruChecActivity.class);
                i.putExtra("mid",mid);
                startActivity(i);
                finish();
                break;
            case R.id.merchant_check:
                Intent i1 = new Intent(MerchantNews.this,MerchantActivity.class);
                i1.putExtra("mid",mid);
                startActivity(i1);
                finish();
                break;

        }
        return true;
    }






}