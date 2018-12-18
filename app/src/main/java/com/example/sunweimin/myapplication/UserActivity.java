package com.example.sunweimin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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

public class UserActivity extends AppCompatActivity {

    //用户兼职信息查看

    private String[] data = { "Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango" };


    int sid;
    ResultSet result = null;
    Connection conn;
    private static Handler handler=new Handler();
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_user);
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
                    String sql = "select * from news;";


                    ResultSet rs = stmt.executeQuery(sql);//创建数据对
                    while (rs.next()) {
                        dataa.add(rs.getString(2));
                        nid.add(rs.getInt(1));
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
                                UserActivity.this, android.R.layout.simple_list_item_1, data);
                        ListView listView = (ListView) findViewById(R.id.user_list_view);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // TODO: Implement this method

                                Intent intent = new Intent(UserActivity.this,UserNewsActivity.class);
                                intent.putExtra("nid",nid.get(position));
                                intent.putExtra("sid", sid);
                                startActivity(intent);
                            }
                        });

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
                Intent i2 = new Intent(UserActivity.this,UserStateActivity.class);
                i2.putExtra("sid", sid);
                startActivity(i2);
                finish();
                break;
            case R.id.recru_news:
                break;
        }
        return true;
    }







}
