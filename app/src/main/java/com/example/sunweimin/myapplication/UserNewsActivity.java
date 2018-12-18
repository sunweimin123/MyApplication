package com.example.sunweimin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class UserNewsActivity extends AppCompatActivity {
    //兼职应聘

    int nid;
    int mid;
    int sid;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_news);

        Intent i = getIntent();
        nid = i.getIntExtra("nid",0);
        sid = i.getIntExtra("sid",0);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


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

        new Thread(new Runnable() {
            @Override
            public void run() {
                Button submit = (Button)findViewById(R.id.submit);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {

                            Class.forName("com.mysql.jdbc.Driver");
                            Connection conn = DriverManager.getConnection("jdbc:mysql:" + "//47.101.202.78:3306/AppSystem?characterEncoding=utf-8", "root", "sunweimin88");
                            Statement stmt = (Statement) conn.createStatement(); //创建Statement对象

                            String sql = "select mid from news where nid="+nid+";";
                            ResultSet rs = stmt.executeQuery(sql);//创建数据对
                            while(rs.next()){
                                mid = rs.getInt("mid");
                            }



                            sql = "insert into link(nid,mid,sid,state) values("+nid+","+mid+","+sid+",0);";
                            stmt.executeUpdate(sql);//创建数据
                            stmt.close();
                            conn.close();




                            Intent intent = new Intent(UserNewsActivity.this,UserActivity.class);
                            intent.putExtra("sid",sid);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });


            }
        }).start();





    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.users, menu);
        return true;
    }

    //menu的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.recru_stat:
                Intent i2 = new Intent(UserNewsActivity.this,UserStateActivity.class);

                i2.putExtra("sid",sid);
                startActivity(i2);
                finish();
                break;
            case R.id.recru_news:
                Intent i = new Intent(UserNewsActivity.this,UserActivity.class);
                i.putExtra("sid",sid);
                startActivity(i);
                finish();
                break;

        }
        return true;
    }
}
