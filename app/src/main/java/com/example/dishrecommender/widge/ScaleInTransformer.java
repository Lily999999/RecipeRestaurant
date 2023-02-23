package com.example.dishrecommender.widge;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

// effects for ViewPager2

public class ScaleInTransformer implements ViewPager2.PageTransformer {
    private final float CENTER = 0.5f;
    private final float MINSCALE = 0.85f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();
        page.setPivotY(pageHeight / 2F);
        page.setPivotX(pageWidth / 2F);
        if (position < -1) { // [-Infinity, -1)
            page.setScaleX(MINSCALE);
            page.setScaleY(MINSCALE);
            page.setPivotX(pageWidth);
        } else if (position <= 1) { // [-1,1]
            if (position < 0) { // [-1, 0]
                float scaleFactor = (1 + position) * (1 - MINSCALE) + MINSCALE;
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setPivotX(pageWidth * (CENTER + CENTER * -position));
            } else { // [1, 0]
                float scaleFactor = (1 - position) * (1 - MINSCALE) + MINSCALE;
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setPivotX(pageWidth * ((1 - position) * CENTER));
            }
        } else { // (1, +Infinity]
            page.setPivotX(0F);
            page.setScaleX(MINSCALE);
            page.setScaleY(MINSCALE);
        }
    }
}
