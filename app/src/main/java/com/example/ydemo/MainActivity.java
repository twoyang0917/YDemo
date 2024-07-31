package com.example.ydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login_btn;
    private Button reset_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_button);
        reset_btn = findViewById(R.id.reset_button);

        login_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("Login", "登录");
                String usernameStr = String.valueOf(username.getText());
                String passwordStr = Utils.md5(String.valueOf(password.getText()));
                String sign = Utils.md5(usernameStr + passwordStr + "magic");
                Log.i("Login", "username: " + usernameStr + '\t' + "password: " + passwordStr);

                // okhttp3
                /*new Thread() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(Common.interceptor).build();
                        FormBody formBody = new FormBody.Builder()
                                .add("username", usernameStr)
                                .add("password", passwordStr)
                                .add("sign", sign)
                                .build();
                        String login_url = "http://10.0.53.6:5000/login";
                        Request request = new Request.Builder().url(login_url)
                                .post(formBody)
                                .build();
                        okhttp3.Call call = client.newCall(request);
                        try {
                            Response response = call.execute();
                            ResponseBody responseBody = response.body();
                            String data = responseBody.string();
                            Log.i("Login", data);
                            // showToast(data);
                            LoginResponse loginResponse = new Gson().fromJson(data, LoginResponse.class);
                            if (loginResponse.code == 0) {
                                showToast("登录成功");
                                // 将token保存到SharedPreferences
                                SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("token", loginResponse.token);
                                editor.commit();

//                                xml文件保存的位置
//                                flame:/data/data/com.example.ydemo/shared_prefs # cat user_info.xml
//                                <?xml version='1.0' encoding='utf-8' standalone='yes' ?>
//                                <map>
//                                    <string name="token">2080c669-f7fb-4a6a-9a6e-3825c29b8f2f</string>
//                                </map>

                                // 跳转至首页
                                Intent intent = new Intent(MainActivity.this, IndexActivity.class);
                                startActivity(intent);
                            } else {
                                showToast("登录失败");
                            }

                        } catch (IOException e) {
                            Log.e("Login", "网络请求失败");
                        }
                    }
                }.start();*/

                // retrofit
                new Thread() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(Common.interceptor).build();
                        retrofit2.Call<ResponseBody> call = new Retrofit.Builder()
                                .baseUrl("http://10.0.53.6:5000/")
                                .client(client)
                                .build()
                                .create(HttpReq.class)
                                .login(usernameStr, passwordStr, sign);
                        try {
                            ResponseBody responseBody = call.execute().body();
                            String data = responseBody.string();
                            Log.i("Login", data);
                            // showToast(data);
                            LoginResponse loginResponse = new Gson().fromJson(data, LoginResponse.class);
                            if (loginResponse.code == 0) {
                                showToast("登录成功");
                                Intent intent = new Intent(MainActivity.this, IndexActivity.class);
                                startActivity(intent);
                            }
                        } catch (IOException e) {
                            Log.e("Login", "网络请求失败");
                        }
                    }
                }.start();
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.i("Login", "重置");
                username.setText("");
                password.setText("");
            }
        });
    }

    private void showToast(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

