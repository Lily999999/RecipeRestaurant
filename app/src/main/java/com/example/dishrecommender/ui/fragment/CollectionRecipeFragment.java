package com.example.dishrecommender.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dishrecommender.R;
import com.example.dishrecommender.logic.model.BaseBean;
import com.example.dishrecommender.logic.model.CollectionRecipeBean;
import com.example.dishrecommender.logic.net.RestaurantInterface;
import com.example.dishrecommender.logic.net.RetrofitServiceManager;
import com.example.dishrecommender.ui.activity.RecipeDetailActivity;
import com.example.dishrecommender.ui.adapter.CollectionRecipeAdapter;
import com.example.dishrecommender.utils.CollectionRecipeCallback;
import com.google.android.material.transition.platform.MaterialElevationScale;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class CollectionRecipeFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private SwipeRecyclerView recyclerView;
    private CollectionRecipeAdapter adapter = new CollectionRecipeAdapter(new CollectionRecipeCallback());
    private List<CollectionRecipeBean.DataDTO> list = new ArrayList<>();
    private int page = 1;


    private String mParam1;
    private String mParam2;

    public CollectionRecipeFragment() {
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
        return inflater.inflate(R.layout.fragment_collection_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postponeEnterTransition();
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                startPostponedEnterTransition();
                return true;
            }
        });
        setExitTransition(new MaterialElevationScale(false));
        setReenterTransition(new MaterialElevationScale(true));
        RestaurantInterface mRestaurantService = RetrofitServiceManager.getInstance().create(RestaurantInterface.class);
        recyclerView = view.findViewById(R.id.collection_rv_recipe);
        refreshLayout = view.findViewById(R.id.collection_srl_recipe);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCollect(mRestaurantService);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemViewSwipeEnabled(true);
        OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
                return true;
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
                int position = srcHolder.getLayoutPosition();
                mRestaurantService.deleteCollection(list.get(position).id.toString()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<BaseBean>() {

                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull BaseBean baseBean) {
                        Toast.makeText(requireActivity(), baseBean.msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        recyclerView.loadMoreError(0, "Net Error");
                        Toast.makeText(requireActivity(), "Net error", Toast.LENGTH_SHORT).show();
                    }
                });
                list.remove(position);
                //adapter.submitList(list);
                adapter.notifyItemRemoved(position);
            }
        };
        recyclerView.setOnItemMoveListener(mItemMoveListener);

        getCollect(mRestaurantService);

    }

    private void getCollect(RestaurantInterface mRestaurantService) {
        mRestaurantService.getCollection("2", "20", "1").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<CollectionRecipeBean>() {

            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CollectionRecipeBean collectionRecipeBean) {
                refreshLayout.setRefreshing(false);
                if (collectionRecipeBean.data.isEmpty()) {
                    Toast.makeText(requireActivity(), "Net error", Toast.LENGTH_SHORT).show();
                } else {
                    list.clear();
                    list.addAll(collectionRecipeBean.data);
                    adapter.setOnItemClickListener(new CollectionRecipeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, View transView) {
                            Intent intent = new Intent(requireActivity(), RecipeDetailActivity.class);
                            intent.putExtra("transitionName", transView.getTransitionName());
                            intent.putExtra("recipe_detail", (Serializable) collectionRecipeBean.data.get(position).collection_data);
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(requireActivity(), transView, transView.getTransitionName());
                            startActivity(intent, options.toBundle());
                        }
                    });
                    adapter.submitList(list);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Toast.makeText(requireActivity(), "Net error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}