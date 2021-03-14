package com.ss.android.ugc.demo;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ss.android.ugc.demo.widget.Clock;

public class ClockHandlerThread extends HandlerThread implements Handler.Callback {
    public static final int MSG_REFLASH_UI = 0;

    public Handler mHandler;
    public Clock clockview;

    public ClockHandlerThread(String name, Clock clockview) {
        super((name));
        this.clockview = clockview;
    }

    @Override protected void onLooperPrepared() {
        super.onLooperPrepared();
        mHandler = new Handler(getLooper(), this);
        mHandler.sendEmptyMessage(MSG_REFLASH_UI);
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        if (message.what == MSG_REFLASH_UI) {
            clockview.invalidate();
            Log.e("clock", "Reflash the UI");
            mHandler.sendEmptyMessageDelayed(MSG_REFLASH_UI, 1000);
        }
        return true;
    }
}