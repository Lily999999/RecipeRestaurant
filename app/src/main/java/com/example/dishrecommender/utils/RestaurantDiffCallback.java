package com.example.dishrecommender.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.dishrecommender.logic.model.RestaurantBean;

import java.util.Objects;


public class RestaurantDiffCallback extends DiffUtil.ItemCallback<RestaurantBean.DataDTO> {
    @Override
    public boolean areItemsTheSame(@NonNull RestaurantBean.DataDTO oldItem, @NonNull RestaurantBean.DataDTO newItem) {
        if (oldItem.hashCode() != newItem.hashCode()) {
            return false;
        } else {
            return Objects.equals(oldItem.id, newItem.id);
        }
    }

    @Override
    public boolean areContentsTheSame(@NonNull RestaurantBean.DataDTO oldItem, @NonNull RestaurantBean.DataDTO newItem) {
        return false;
    }

    @Nullable
    @Override
    public Object getChangePayload(@NonNull RestaurantBean.DataDTO oldItem, @NonNull RestaurantBean.DataDTO newItem) {
        return "";
    }
}
