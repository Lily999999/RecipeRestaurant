package com.example.dishrecommender.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dishrecommender.R;
import com.example.dishrecommender.logic.net.RestaurantInterface;
import com.example.dishrecommender.logic.net.RetrofitServiceManager;
import com.example.dishrecommender.ui.activity.LoginActivity;


public class MyFragment extends Fragment {

    private Button mBtnLoginOut;

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnLoginOut = view.findViewById(R.id.my_btn_login_out);
        RestaurantInterface mRestaurantService = RetrofitServiceManager.getInstance().create(RestaurantInterface.class);
        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(requireActivity(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });
    }
}