package com.example.dishrecommender.widge;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.viewpager2.widget.ViewPager2;

// Display Views or Fragments in a swipeable format

public class ViewPager2Container extends RelativeLayout {
    private ViewPager2 mViewPager2;
    private Boolean disallowParentInterceptDownEvent = true;
    private int startX = 0;
    private int startY = 0;

    public ViewPager2Container(Context context) {
        super(context);
    }

    public ViewPager2Container(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPager2Container(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ViewPager2Container(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView instanceof ViewPager2) {
                mViewPager2 = (ViewPager2) childView;
                break;
            }
        }
        if (mViewPager2 == null) {
            throw new IllegalStateException("The root child of ViewPager2Container must contains a ViewPager2");
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Boolean doNotNeedIntercept = ((!mViewPager2.isUserInputEnabled() || (mViewPager2.getAdapter() != null && mViewPager2.getAdapter().getItemCount() <= 1)));
        if (doNotNeedIntercept) {
            return super.onInterceptTouchEvent(ev);
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            startX = (int) ev.getX();
            startY = (int) ev.getY();
            getParent().requestDisallowInterceptTouchEvent(disallowParentInterceptDownEvent);
        } else if ((ev.getAction() == MotionEvent.ACTION_MOVE)) {
            int endX = (int) ev.getX();
            int endY = (int) ev.getY();
            int disX = (int) Math.abs(endX - startX);
            int disY = (int) Math.abs(endY - startY);
            if (mViewPager2.getOrientation() == ViewPager2.ORIENTATION_VERTICAL) {
                onVerticalActionMove(endY, disX, disY);
            } else if (mViewPager2.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                onHorizontalActionMove(endX, disX, disY);
            }
        } else {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void onHorizontalActionMove(int endX, int disX, int disY) {
        if (mViewPager2.getAdapter() == null) {
            return;
        }
        if (disX > disY) {
            int currentItem = mViewPager2.getCurrentItem();
            int itemCount = mViewPager2.getAdapter().getItemCount();
            if (currentItem == 0 && endX - startX > 0) {
                getParent().requestDisallowInterceptTouchEvent(false);
            } else {
                if (currentItem == itemCount - 1) {
                    if (endX < startX) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
            }
        } else if (disY > disX) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
    }

    private void onVerticalActionMove(int endY, int disX, int disY) {
        if (mViewPager2.getAdapter() == null) {
            return;
        }
        if (disY > disX) {
            int currentItem = mViewPager2.getCurrentItem();
            int itemCount = mViewPager2.getAdapter().getItemCount();
            if (currentItem == 0 && endY - startY > 0) {
                getParent().requestDisallowInterceptTouchEvent(false);
            } else {
                if (currentItem == itemCount - 1) {
                    if (endY < startY) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
            }
        } else if (disX > disY) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
    }

    public void disallowParentInterceptDownEvent(Boolean disallowParentInterceptDownEvent) {
        this.disallowParentInterceptDownEvent = disallowParentInterceptDownEvent;
    }
}
