package com.example.blurbitmap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private Bitmap blurBitmap;
    private ImageView content;
    UpdateUIHandler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        content = (ImageView) findViewById(R.id.content);
        handler = new UpdateUIHandler(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("aa", "开始时间" + System.currentTimeMillis());
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
                blurBitmap = BitmapUtil.fastblur(MyActivity.this, bitmap, 50);
                Log.e("aa", "结束时间" + System.currentTimeMillis());
                handler.sendEmptyMessage(1);
            }
        }).start();
    }


    private static class UpdateUIHandler extends Handler {
        WeakReference<MyActivity> acvt;

        public UpdateUIHandler(MyActivity myActivity) {
            acvt = new WeakReference<MyActivity>(myActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MyActivity myActivity = acvt.get();
            if (null == myActivity) {
                return;
            }
            if (msg.what == 1) {
                myActivity.setContent();
            }

        }
    }


    private void setContent() {
        content.setImageBitmap(blurBitmap);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);


    }


}
