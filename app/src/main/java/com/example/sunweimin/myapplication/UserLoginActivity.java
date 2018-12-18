package com.example.sunweimin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;




public class UserLoginActivity extends AppCompatActivity {
    //用户登录


    ResultSet result = null;
    Connection conn;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //注册
        Button signUp = (Button)findViewById(R.id.sign_up);
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, UserSignUpActivity.class);

                startActivity(intent);//执行该Intent
                finish();
            }
        });

        //转换卖家模式
        TextView merchant_type = (TextView) findViewById(R.id.marchant_type);
        merchant_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, MerchantLoginActivity.class);
                startActivity(intent);//执行该Intent
                finish();
            }
        });


        Button sign_in = (Button)findViewById(R.id.sign_in);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check()){

                    Intent intent = new Intent(UserLoginActivity.this,UserActivity.class);
                    intent.putExtra("sid",uid);
                    startActivity(intent);
                    finish();
                }else{
                    //Toast.makeText(UserLoginActivity.this, "密码错误", Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    public boolean check() {
        try {

            EditText username = (EditText)findViewById(R.id.user_name);
            EditText password = (EditText)findViewById(R.id.password);
            String user = username.getText().toString();
            String pwd = password.getText().toString();

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql:" + "//47.101.202.78:3306/AppSystem?characterEncoding=utf-8", "root", "sunweimin88");
            Statement stmt = (Statement) conn.createStatement(); //创建Statement对象

            String sql = "select * from stu where sname ='"+user+"';";


            ResultSet rs = stmt.executeQuery(sql);//创建数据对
            while (rs.next()) {

                if(pwd.equals(rs.getString(2))){
                    uid = rs.getInt("sid");

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




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
