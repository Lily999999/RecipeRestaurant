package com.example.dishrecommender.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.dishrecommender.logic.model.RecipeBean;

import java.util.Objects;


public class RecipeDiffCallback extends DiffUtil.ItemCallback<RecipeBean.DataDTO> {
    @Override
    public boolean areItemsTheSame(@NonNull RecipeBean.DataDTO oldItem, @NonNull RecipeBean.DataDTO newItem) {
        if (oldItem.hashCode() != newItem.hashCode()) {
            return false;
        } else {
            return Objects.equals(oldItem.id, newItem.id);
        }
    }

    @Override
    public boolean areContentsTheSame(@NonNull RecipeBean.DataDTO oldItem, @NonNull RecipeBean.DataDTO newItem) {
        return false;
    }

    @Nullable
    @Override
    public Object getChangePayload(@NonNull RecipeBean.DataDTO oldItem, @NonNull RecipeBean.DataDTO newItem) {
        return "";
    }
}