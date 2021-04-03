package com.module.video;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.module.router.provider.INewsProvider;

public class VideoMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_main);
    }
}