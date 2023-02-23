package com.example.dishrecommender;

import static java.util.Arrays.asList;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dishrecommender.ui.adapter.BaseFragmentAdapter;
import com.example.dishrecommender.ui.base.BaseActivity;
import com.example.dishrecommender.ui.fragment.CollectionRecipeFragment;
import com.example.dishrecommender.ui.fragment.MyFragment;
import com.example.dishrecommender.ui.fragment.RecipeFragment;
import com.example.dishrecommender.ui.fragment.RestaurantFragment;
import com.example.dishrecommender.widge.ScaleInTransformer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.permissionx.guolindev.PermissionX;

// MainActivity uses 4 Fragments just like the slide bar (which uses BottomNavigationView)

// Use PermissionX to handle permission
// https://github.com/guolindev/PermissionX

public class MainActivity extends BaseActivity {

    private ViewPager2 viewPager2;
    private Toolbar toolbar;
    private ImageView searchIv;
    private TextView mTvTitle;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.main_vp);
        searchIv = findViewById(R.id.main_iv_search);
        mTvTitle = findViewById(R.id.toolbar_title);
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottom_navigation_view);
        toolbar = findViewById(R.id.toolbar);
        viewPager2.setPageTransformer(new ScaleInTransformer());
        viewPager2.setAdapter(new BaseFragmentAdapter(this, asList(new RecipeFragment(), new RestaurantFragment(), new CollectionRecipeFragment(), new MyFragment())));
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_menu1) {
                viewPager2.setCurrentItem(0);
            } else if (item.getItemId() == R.id.navigation_menu2) {
                viewPager2.setCurrentItem(1);
            } else if (item.getItemId() == R.id.navigation_menu3) {
                viewPager2.setCurrentItem(2);
            } else if (item.getItemId() == R.id.navigation_menu4) {
                viewPager2.setCurrentItem(3);
            }
            return true;
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                if (position == 0) {
                    mTvTitle.setText("Recipe");
                } else if (position == 1) {
                    mTvTitle.setText("Restaurant");
                } else if (position == 2) {
                    mTvTitle.setText("Collection");
                } else if (position == 3) {
                    mTvTitle.setText("My");
                }
            }
        });
        getAddress();
    }

    private Long mExitTime = 0L;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                Toast.makeText(this, "Please click again to exit", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("MissingPermission")
    private void getAddress() {

        PermissionX.init(this)
                .permissions(Manifest.permission.ACCESS_FINE_LOCATION).request((allGranted, grantedList, deniedList) -> {
                    if (!allGranted) {
                        finish();
                        Toast.makeText(this, "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show();
                    }
                });
    }

}