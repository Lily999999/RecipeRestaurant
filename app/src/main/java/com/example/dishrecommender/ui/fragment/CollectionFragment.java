package com.example.dishrecommender.ui.fragment;


import static java.util.Arrays.asList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dishrecommender.R;
import com.example.dishrecommender.ui.adapter.BaseFragmentAdapter;
import com.example.dishrecommender.widge.RotationTransformer;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

// CollectionFragment uses RecyclerView to show the collection

public class CollectionFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ViewPager2 mViewPager2;
    private TabLayout mTabLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CollectionFragment() {
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout = view.findViewById(R.id.collection_tl);
        mViewPager2 = view.findViewById(R.id.collection_vp);
        initViewPager2();
        initTabLayout();

    }



    private void initViewPager2() {
        mViewPager2.setAdapter(new BaseFragmentAdapter(getActivity(), asList(new CollectionRestaurantFragment(), new CollectionRecipeFragment())));
        mViewPager2.setPageTransformer(new RotationTransformer());
        mViewPager2.setOffscreenPageLimit(1);
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
    }

    private void initTabLayout() {
        String tabs[] = {"Restaurant", "Recipe"};
        TabLayoutMediator mediator = new TabLayoutMediator(mTabLayout, mViewPager2, (tab, position) -> {
            tab.setText(tabs[position]);
        });
        mediator.attach();
    }

}