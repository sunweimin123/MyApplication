package com.example.sunweimin.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserStateActivity extends AppCompatActivity {
    //用户应聘状态查询

    int sid;
    ResultSet result = null;
    Connection conn;
    String states[]={"待审","拒绝","接受"};
    private static Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_state);
        Intent intent = getIntent();
        sid = intent.getIntExtra("sid", 0);
        showInfor();



    }



    public void showInfor(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> dataa = new ArrayList<>();
                List<Integer> nid = new ArrayList<>();
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql:" + "//47.101.202.78:3306/AppSystem?characterEncoding=utf-8", "root", "sunweimin88");
                    Statement stmt = (Statement) conn.createStatement(); //创建Statement对象
                    String sql = "select major,state from link , news where link.nid=news.nid and sid="+sid+";";


                    ResultSet rs = stmt.executeQuery(sql);//创建数据对
                    while (rs.next()) {

                        String major = rs.getString(1);
                        int state = rs.getInt(2);

                        dataa.add(major+"     "+states[state]);

//                        Looper.prepare();
//                        Toast.makeText(MerchantActivity.this,rs.getString(2),Toast.LENGTH_LONG).show();
//                        Looper.loop();
                    }

                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String [] data = dataa.toArray(new String[dataa.size()]);


                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showInfor();
                        //完成主界面更新,拿到数据
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                UserStateActivity.this, android.R.layout.simple_list_item_1, data);
                        ListView listView = (ListView) findViewById(R.id.list_view_state);
                        listView.setAdapter(adapter);


                    }
                });

            }
        });

        t.start();
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
                break;
            case R.id.recru_news:
                Intent i2 = new Intent(UserStateActivity.this,UserActivity.class);
                i2.putExtra("sid", sid);
                startActivity(i2);
                finish();
                break;
        }
        return true;
    }




}
