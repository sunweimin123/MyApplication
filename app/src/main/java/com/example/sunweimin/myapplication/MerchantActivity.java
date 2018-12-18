package com.example.sunweimin.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MerchantActivity extends AppCompatActivity {

    //商家兼职信息查看




    int mid;
    ResultSet result = null;
    Connection conn;

    private static Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        Intent intent = getIntent();

        mid = intent.getIntExtra("mid", 0);
        //Toast.makeText(MerchantActivity.this,mid+"   ",Toast.LENGTH_LONG).show();

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
//                        Toast.makeText(MerchantActivity.this,mid,Toast.LENGTH_LONG).show();
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
                                MerchantActivity.this, android.R.layout.simple_list_item_1, data);
                        ListView listView = (ListView) findViewById(R.id.merchant_list_view);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // TODO: Implement this method
                                Toast.makeText(MerchantActivity.this,data[position],Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(MerchantActivity.this,MerchantNews.class);
                                intent.putExtra("nid",nid.get(position));
                                intent.putExtra("mid", mid);
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
        getMenuInflater().inflate(R.menu.merchant, menu);
        return true;
    }

    //menu的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.merchant_put:
                Intent i2 = new Intent(MerchantActivity.this,MerchantNewsPutActivity.class);
                i2.putExtra("mid",mid);
                startActivity(i2);
                finish();
                break;
            case R.id.merchant_recru:
                Intent i = new Intent(MerchantActivity.this,MerchantRecruChecActivity.class);
                i.putExtra("mid",mid);
                startActivity(i);
                finish();
                break;
            case R.id.merchant_check:
                break;

        }
        return true;
    }









}
