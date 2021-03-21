package com.bytedance.practice5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.practice5.model.Message;
import com.bytedance.practice5.model.MessageListResponse;
import com.bytedance.practice5.socket.SocketActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.bytedance.practice5.Constants.token;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "chapter5";
    private FeedAdapter adapter = new FeedAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UploadActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_mine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Constants.STUDENT_ID);
            }
        });

        findViewById(R.id.btn_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(null);
            }
        });
        findViewById(R.id.btn_socket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SocketActivity.class);
                startActivity(intent);
            }
        });



    }


    private void getData(String studentId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MessageListResponse messageListResponse = getMessageData(studentId, token);
                List<Message> messageList = messageListResponse.feeds;
                Log.e("GetData", "Got the correct data!.");
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (studentId == null) {
                            adapter.setData(messageList);
                        }
                        else {
                            adapter.setData(null);
                            for (int i = 0; i < messageList.size(); ++i) {
                                if (messageList.get(i).getId().equals(studentId)) {
                                    List<Message> dataList = (List<Message>) messageList.get(i);
                                    adapter.setData(dataList);
                                }
                            }
                        }
                    }
                });
            };

        }).start();
    }


    public MessageListResponse getMessageData(String studentId, String token) {
        String urlStr =
                String.format("https://api-sjtu-camp-2021.bytedance.com/homework/invoke/messages");
        MessageListResponse result = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000);
            connection.setRequestMethod("GET");
            // connection.setRequestProperty("accept", token);

            if (connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

                result = new Gson().fromJson(reader, MessageListResponse.class);

                reader.close();
                in.close();
            }
            else {
                // 错误处理
            }
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Looper.prepare();
            Toast.makeText(this, "网络异常" + e.toString(), Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
        return result;
    }
}