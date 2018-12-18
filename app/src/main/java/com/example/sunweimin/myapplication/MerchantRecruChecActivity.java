package com.example.sunweimin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

//应聘人员查看
public class MerchantRecruChecActivity extends AppCompatActivity {

        //商家应聘人员查看



    int mid ;
    ResultSet result = null;
    Connection conn;
    String states[]={"待审","拒绝","接受"};
    private static Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_recru_chec);

        Intent intent = getIntent();
        mid = intent.getIntExtra("mid", 0);
        showInfor();

    }


    public void showInfor(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> dataa = new ArrayList<>();
                List<Integer> nid = new ArrayList<>();
                List<Integer> sids = new ArrayList<>();
                List<Integer> lids = new ArrayList<>();
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql:" + "//47.101.202.78:3306/AppSystem?characterEncoding=utf-8", "root", "sunweimin88");
                    Statement stmt = (Statement) conn.createStatement(); //创建Statement对象
                    String sql = "select sid,state,link.nid ,lid from link , news where link.nid=news.nid and link.mid="+mid+";";


                    ResultSet rs = stmt.executeQuery(sql);//创建数据对
                    while (rs.next()) {

                        int sid = rs.getInt(1);
                        int state = rs.getInt(2);
                        nid.add(rs.getInt(3));
                        dataa.add(sid+"     "+states[state]);
                        sids.add(sid);
                        lids.add(rs.getInt(4));

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
                                MerchantRecruChecActivity.this, android.R.layout.simple_list_item_1, data);
                        ListView listView = (ListView) findViewById(R.id.list_view_state);
                        listView.setAdapter(adapter);


                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // TODO: Implement this method

                                Intent intent = new Intent(MerchantRecruChecActivity.this,MerchantStateModiActivity.class);
                                intent.putExtra("nid",nid.get(position));
                                intent.putExtra("mid", mid);
                                intent.putExtra("sid", sids.get(position));
                                intent.putExtra("lid",lids.get(position));
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
                Intent i2 = new Intent(MerchantRecruChecActivity.this,MerchantNewsPutActivity.class);
                i2.putExtra("mid",mid);
                startActivity(i2);
                finish();
                break;
            case R.id.merchant_recru:

                break;
            case R.id.merchant_check:
                Intent i1 = new Intent(MerchantRecruChecActivity.this,MerchantActivity.class);
                i1.putExtra("mid",mid);
                startActivity(i1);
                finish();
                break;

        }
        return true;
    }





}
