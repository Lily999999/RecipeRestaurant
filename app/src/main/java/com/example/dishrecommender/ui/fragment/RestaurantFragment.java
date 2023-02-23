package com.example.dishrecommender.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishrecommender.R;
import com.example.dishrecommender.logic.model.RestaurantBean;
import com.example.dishrecommender.logic.model.RestaurantRepository;
import com.example.dishrecommender.logic.net.RestaurantInterface;
import com.example.dishrecommender.ui.activity.RestaurantActivity;
import com.example.dishrecommender.ui.adapter.RestaurantAdapter;
import com.example.dishrecommender.utils.RestaurantDiffCallback;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

// RestaurantFragment uses the preferences provided by RecipeFragment, click the WebView to view its web page

// Uses Retrofit and RxJava to get restaurants from the server
public class RestaurantFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private LocationManager locationManager;
    private Location location;

    private ProgressBar progressBar;
    private Button button;
    private RecyclerView recyclerView;
    private List<RestaurantBean.DataDTO> mList = new ArrayList<>();
    private RestaurantAdapter adapter = new RestaurantAdapter(new RestaurantDiffCallback());
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RestaurantFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_restaurant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = view.findViewById(R.id.restaurant_btn_query);
        progressBar = view.findViewById(R.id.restaurant_pb);
        recyclerView = view.findViewById(R.id.restaurant_recycler_view);
        recyclerView.setLayoutAnimation(new LayoutAnimationController(AnimationUtils.loadAnimation(getContext(), R.anim.recycler_view_fade_in)));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        getAddress();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String term = sharedPreferences.getString("taste", "") + " " + sharedPreferences.getString("dietary", "");
                if (location == null) {
                    Toast.makeText(getContext(), "location error", Toast.LENGTH_SHORT).show();
                } else if (term.equals("")) {
                    Toast.makeText(getContext(), "Please set preference in recipe", Toast.LENGTH_SHORT).show();
                } else {
                    RestaurantInterface mRestaurantService = RestaurantRepository.getSingleton().mRestaurantService;
                    progressBar.setVisibility(View.VISIBLE);
                    mRestaurantService.getRestaurant(location.getLatitude() + "", location.getLongitude() + "", term)
                            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<RestaurantBean>() {
                                @Override
                                public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                                }

                                @Override
                                public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RestaurantBean restaurantBean) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    if (restaurantBean.data == null) {
                                        Toast.makeText(getContext(), "no restaurant", Toast.LENGTH_SHORT).show();
                                    } else {
                                        mList.clear();
                                        mList.addAll(restaurantBean.data);
                                        adapter.submitList(mList);
                                        adapter.setOnItemClickListener(new RestaurantAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {
                                                Intent intent = new Intent(getActivity(), RestaurantActivity.class);
                                                intent.putExtra("url", adapter.getCurrentList().get(position).url);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(), "net error", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getAddress() {
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        location = locationManager
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Log.e("TAG", "onViewCreated: " + location);
    }
}