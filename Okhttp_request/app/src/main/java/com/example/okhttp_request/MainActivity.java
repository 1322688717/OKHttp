package com.example.okhttp_request;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：阿宅
 * 时间：2021.6.8
 * 内容：okhttp的基本使用 同步get和异步get的基本方法
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //异步get请求
        EnqueueGet();
        //同步get请求
        SyncGet();
    }

    private void SyncGet() {
        String url = "https://api.caiyunapp.com/v2.5/7KlicJFJqOmm3iIn/121.6544,25.1552/realtime.json";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    Log.e("TAG", "同步get请求的返回体：" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void EnqueueGet() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                String url = "https://api.caiyunapp.com/v2.5/7KlicJFJqOmm3iIn/121.6544,25.1552/realtime.json";
                OkHttpClient okHttpClient = new OkHttpClient();
                final Request request = new Request.Builder()
                        .url(url)
                        .get()//默认就是GET请求，可以不写
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("TAG", "onFailure: ");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Log.e("TAG", "异步get请求的返回体: " + response.body().string());
                    }
                });
            }
        }.start();
    }
}