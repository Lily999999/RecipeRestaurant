package com.example.dishrecommender.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dishrecommender.R;
import com.example.dishrecommender.logic.model.RecipeBean;
import com.example.dishrecommender.ui.base.BaseActivity;
import com.google.android.material.chip.ChipGroup;

public class RecipeDetailActivity extends BaseActivity {
    private RecipeBean.DataDTO data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        postponeEnterTransition();
        startPostponedEnterTransition();
        data = (RecipeBean.DataDTO) getIntent().getSerializableExtra("recipe_detail");
        ImageView mIvBack = findViewById(R.id.recipe_iv_detail_back);
        ImageView mIvCover = findViewById(R.id.recipe_iv_detail_url);
        mIvCover.setTransitionName(getIntent().getStringExtra("recipe_cover"));
        TextView mTvName = findViewById(R.id.recipe_tv_detail_name);
        TextView mTvCategory = findViewById(R.id.recipe_tv_detail_categories);
        TextView mTvIntroduction = findViewById(R.id.recipe_tv_detail_introduction);
        ChipGroup mChipGroupKeywords = findViewById(R.id.recipe_chip_group_keywords);
        ChipGroup mChipGroupIngredient = findViewById(R.id.recipe_chip_group_ingredients);
        TextView mTvCookTime = findViewById(R.id.recipe_tv_detail_cooktime);
        TextView mTvPreptime = findViewById(R.id.recipe_tv_detail_preptime);
        TextView mTvCalories = findViewById(R.id.recipe_tv_detail_calories);
        TextView mTvFat = findViewById(R.id.recipe_tv_detail_fat);
        TextView mTvSaturatedFat = findViewById(R.id.recipe_tv_detail_saturatedfat);
        TextView mTvSodium = findViewById(R.id.recipe_tv_detail_sodium);
        TextView mTvCarbohydrate = findViewById(R.id.recipe_tv_detail_carbohydrate);
        TextView mTvSugar = findViewById(R.id.recipe_tv_detail_sugar);
        TextView mTvProtein = findViewById(R.id.recipe_tv_detail_Protein);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (!data.images.isEmpty()) {
            Glide.with(mIvCover).load(data.images.get(0)).centerCrop().error(R.drawable.ic_twitter).into(mIvCover);
        }
        int ct = data.cookTime/60;
        int pt = data.perpTime/60;
        mTvName.setText(data.name);
        mTvCategory.setText(data.category);
        mTvIntroduction.setText(data.instruction.toString());
        mTvCookTime.setText("CookTime (in minutes):" + ct);
        mTvPreptime.setText("PrepTime (in minutes):" + pt);
        mTvCalories.setText("Calories:" + data.calories);
        mTvFat.setText("Fat:" + data.fat);
        mTvSaturatedFat.setText("SaturatedFat:" + data.saturatedFat);
        mTvSodium.setText("Sodium" + data.sodium);
        mTvCarbohydrate.setText("Carbohydrate:" + data.carbohydrate);
        mTvSugar.setText("Sugar:" + data.sugar);
        mTvProtein.setText("Protein:" + data.protein);
        for (String ingredient : data.ingredients) {
            TextView child = (TextView) getLayoutInflater().inflate(R.layout.item_chip, null);
            child.setText(ingredient);
            mChipGroupIngredient.addView(child);
        }
        for (String keyword : data.keywords) {
            TextView child = (TextView) getLayoutInflater().inflate(R.layout.item_chip, null);
            child.setText(keyword);
            mChipGroupKeywords.addView(child);
        }
    }
}