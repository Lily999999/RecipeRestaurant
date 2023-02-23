package com.example.dishrecommender.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.dishrecommender.logic.model.CollectionRecipeBean;
import com.example.dishrecommender.logic.model.CollectionRecipeBean;
import com.example.dishrecommender.logic.model.CollectionRecipeBean;

import java.util.Objects;


public class CollectionRecipeCallback extends DiffUtil.ItemCallback<CollectionRecipeBean.DataDTO> {
    @Override
    public boolean areItemsTheSame(@NonNull CollectionRecipeBean.DataDTO oldItem, @NonNull CollectionRecipeBean.DataDTO newItem) {
        if (oldItem.hashCode() != newItem.hashCode()) {
            return false;
        } else {
            return Objects.equals(oldItem.id, newItem.id);
        }
    }

    @Override
    public boolean areContentsTheSame(@NonNull CollectionRecipeBean.DataDTO oldItem, @NonNull CollectionRecipeBean.DataDTO newItem) {
        return false;
    }

    @Nullable
    @Override
    public Object getChangePayload(@NonNull CollectionRecipeBean.DataDTO oldItem, @NonNull CollectionRecipeBean.DataDTO newItem) {
        return "";
    }
}