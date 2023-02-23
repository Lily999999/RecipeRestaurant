package com.example.dishrecommender.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;


public class BaseFragmentAdapter<T extends Fragment> extends FragmentStateAdapter {
    private List<T> mFragmentList;

    public BaseFragmentAdapter(FragmentActivity fragmentActivity, List<T> fragmentList) {
        super(fragmentActivity);
        mFragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return (Fragment) mFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }
}
