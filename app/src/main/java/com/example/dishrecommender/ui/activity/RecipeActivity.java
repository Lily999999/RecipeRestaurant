package com.example.dishrecommender.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishrecommender.R;
import com.example.dishrecommender.logic.model.BaseBean;
import com.example.dishrecommender.logic.model.RecipeBean;
import com.example.dishrecommender.logic.net.RestaurantInterface;
import com.example.dishrecommender.logic.net.RetrofitServiceManager;
import com.example.dishrecommender.ui.adapter.RecipeAdapter;
import com.example.dishrecommender.ui.base.BaseActivity;
import com.example.dishrecommender.utils.RecipeDiffCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

// RecipeActivity uses RecyclerView and GridLayout

// Click the star to save it to user collection, click the recipe to jump to RecipeDetailActivity

// Uses Retrofit and RxJava to get recipes from the server, and Retrofit to save recipes to the collection
// https://github.com/ReactiveX/RxJava

public class RecipeActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter = new RecipeAdapter(new RecipeDiffCallback());
    private ArrayList<RecipeBean.DataDTO> mList = new ArrayList<>();
    private Map<String, String> queryMap;
    private ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        mIvBack = findViewById(R.id.recipe_iv_back);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recipe_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setLayoutAnimation(new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.recycler_view_fade_in)));
        recyclerView.setAdapter(recipeAdapter);
        RestaurantInterface mRestaurantService = RetrofitServiceManager.getInstance().create(RestaurantInterface.class);

        queryMap = (Map<String, String>) getIntent().getSerializableExtra("recipe_query");
        mRestaurantService.getRecipe(50, 1, queryMap).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<RecipeBean>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                Log.e("TAG", "onSubscribe: subscribe");
            }

            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RecipeBean recipeBean) {
                Log.e("TAG", "onSubscribe: success");
                if (recipeBean.data == null) {
                    Toast.makeText(RecipeActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                } else {
                    mList.addAll(recipeBean.data);
                    recipeAdapter.submitList(mList);
                    recipeAdapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, ImageView imageView) {
                            Intent intent = new Intent(RecipeActivity.this, RecipeDetailActivity.class);
                            intent.putExtra("recipe_detail", (Serializable) recipeAdapter.getCurrentList().get(position));
                            Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(RecipeActivity.this, imageView, "shareElement").toBundle();
                            startActivity(intent, bundle);
                        }
                    });
                    recipeAdapter.setOnItemClickListener2(new RecipeAdapter.OnItemClickListener2() {
                        @Override
                        public void onItemClick(View view, int position) {
                            mRestaurantService.addCollectionRecipe("2",  recipeBean.data.get(position).recipeId.toString()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<BaseBean>() {

                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onSuccess(@NonNull BaseBean baseBean) {
                                    Toast.makeText(RecipeActivity.this, "Add Successful", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Log.e("TAG", "onSubscribe: error");
                Toast.makeText(RecipeActivity.this, "Net error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }
}