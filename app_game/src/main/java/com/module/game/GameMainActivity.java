package com.module.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GameMainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("signature");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);
    }
}