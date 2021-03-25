package com.bytedance.practice5.socket;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;

public class ClientSocketThread extends Thread {
    public ClientSocketThread(SocketActivity.SocketCallback callback) {
        this.callback = callback;
    }

    private SocketActivity.SocketCallback callback;
    private boolean stopFlag = false;
    private volatile String message = "";

    //head请求内容
    private static String content = "HEAD /xxjj/index.html HTTP/1.1\r\nHost:www.sjtu.edu.cn\r\n\r\n";

    private synchronized void clearMsg() {
        this.message = "";
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("www.sjtu.edu.cn", 80);
            BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());
            BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
            message = content;
            byte[] data = new byte[1024*5];
            while (!stopFlag && socket.isConnected()) {
                if (!message.isEmpty()) {
                    Log.d("socket", "客户端发送"+message);
                    os.write(message.getBytes());
                    os.flush();
                    clearMsg();
                    int recivelen = is.read(data);
                    String receive = new String(data, 0, recivelen);
                    callback.onResponse(receive);
                }
            }
            Log.e("socket", "客户端断开");
            os.flush();
            os.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}