package com.example.dishrecommender.widge;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

// effects for ViewPager2
public class RotationTransformer implements ViewPager2.PageTransformer {
    private final float MAX_ROTATION = 90f;
    private final float MIN_SCALE = 0.9f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        page.setTranslationX(-position * page.getWidth() * 0.8f);
        page.setPivotY(page.getHeight() / 2f);

        if (position < -1) { // [-Infinity, -1)
            page.setRotationY(-MAX_ROTATION);
            page.setPivotX(0f);
        } else if (position <= 1) { // [-1,1]
            if (position < 0) { // [-1, 0]
                page.setRotationY(position * position * MAX_ROTATION);
                page.setPivotX(0f);
                float scale = scale = MIN_SCALE + 4f * (1f - MIN_SCALE) * (position + 0.5f) * (position + 0.5f);
                page.setScaleX(scale);
                page.setScaleY(scale);
            } else { // [1, 0]
                page.setRotationY(-position * position * MAX_ROTATION);
                page.setPivotX(page.getWidth());
                float scale = MIN_SCALE + 4f * (1f - MIN_SCALE) * (position - 0.5f) * (position - 0.5f);
                page.setScaleX(scale);
                page.setScaleY(scale);
            }
        } else { // (1, +Infinity]
            page.setRotationY(MAX_ROTATION);
            page.setPivotX(page.getWidth());
        }
    }
}
