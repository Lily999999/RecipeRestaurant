package com.example.dishrecommender.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dishrecommender.R;
import com.example.dishrecommender.ui.activity.RecipeActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// RecipeFragment uses Spinner for drop down boxes

// After the user enters four inputs and click the tick button, the input would be saved in SharedPreferences to provide info for RestaurantFragment
// and the screen would jump to RecipeActivity to show the recommended recipes

public class RecipeFragment extends Fragment {


    private EditText mEtTaste;
    private Spinner mSpinnerDietary;
    private ImageView mIvOk;
    private EditText mEtCookTime;
    private EditText mEtIngredient;
    private Map<String, String> queryMap = new HashMap<>();

    public RecipeFragment() {
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
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    // Set views for the inputs
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSpinnerDietary = view.findViewById(R.id.explore_spinner_dietary);
        mEtTaste = view.findViewById(R.id.explore_et_taste);
        mEtCookTime = view.findViewById(R.id.explore_et_cook_time);
        mEtIngredient = view.findViewById(R.id.explore_et_ingredient);
        mIvOk = view.findViewById(R.id.recipe_iv_ok);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Spinner for the taste preferences
        mSpinnerDietary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos != 0) {
                    String[] dietarys = getResources().getStringArray(R.array.dietaryRequirements);
                    editor.putString("dietary", dietarys[pos]);
                    queryMap.put("dietary", dietarys[pos]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        // The tick button to show the recommended recipes
        mIvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mEtTaste.getText())) {
                    queryMap.put("taste", mEtTaste.getText().toString());
                }
                if (!TextUtils.isEmpty(mEtCookTime.getText())) {
                    queryMap.put("cook_time", mEtCookTime.getText().toString());
                }
                if (!TextUtils.isEmpty(mEtIngredient.getText())) {
                    queryMap.put("ingredients", mEtIngredient.getText().toString());
                }
                editor.commit();
                Intent intent = new Intent(getActivity(), RecipeActivity.class);
                intent.putExtra("recipe_query", (Serializable) queryMap);
                startActivity(intent);
            }
        });
    }
}