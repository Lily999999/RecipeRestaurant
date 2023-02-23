package com.example.dishrecommender.ui.base;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;


public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        cancelStatusBar();
    }

    private void cancelStatusBar() {
        Window window = this.getWindow();
        View decorView = window.getDecorView();


        WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(window, decorView);
        windowInsetsController.setAppearanceLightStatusBars(true);
        window.setStatusBarColor(Color.TRANSPARENT);
    }
}
