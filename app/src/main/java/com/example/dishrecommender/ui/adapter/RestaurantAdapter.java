package com.example.dishrecommender.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dishrecommender.R;
import com.example.dishrecommender.logic.model.RestaurantBean;

import java.util.Objects;


public class RestaurantAdapter extends ListAdapter<RestaurantBean.DataDTO, RestaurantAdapter.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public RestaurantAdapter(@NonNull DiffUtil.ItemCallback<RestaurantBean.DataDTO> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant_rv, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RestaurantBean.DataDTO data = getItem(position);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, holder.getAbsoluteAdapterPosition());
                }
            });
        }
        holder.mTvName.setText(data.name);
        if (Objects.equals(data.location.address1, "")) {
            holder.mTvAddress.setText(data.location.address2);
        } else {
            holder.mTvAddress.setText(data.location.address1);
        }
        holder.mTvCategories.setText(data.categories.get(0).title);
        if (Objects.equals(data.phone, "")) {
            holder.mTvPhone.setText("no phone");
        } else {
            holder.mTvPhone.setText(data.phone);
        }
        if (Math.round(data.distance) > 1000) {
            holder.mTvDistance.setText(Math.round(data.distance / 1000) + "km");
        } else {
            holder.mTvDistance.setText(Math.round(data.distance) + "m");
        }
        Glide.with(holder.mIvCover).load(data.image_url).centerCrop().error(R.drawable.ic_twitter).into(holder.mIvCover);
        holder.mRatingRating.setRating(data.rating);
        holder.mRatingPrice.setRating(data.price.length());
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvName;
        TextView mTvAddress;
        TextView mTvCategories;
        TextView mTvPhone;
        TextView mTvDistance;
        ImageView mIvCover;
        RatingBar mRatingRating;
        RatingBar mRatingPrice;

        public ViewHolder(View view) {
            super(view);
            mTvName = view.findViewById(R.id.restaurant_tv_name);
            mTvAddress = view.findViewById(R.id.restaurant_tv_address);
            mTvCategories = view.findViewById(R.id.restaurant_tv_categories);
            mTvPhone = view.findViewById(R.id.restaurant_tv_phone);
            mTvDistance = view.findViewById(R.id.restaurant_tv_distance);
            mIvCover = view.findViewById(R.id.restaurant_iv_url);
            mRatingRating = view.findViewById(R.id.restaurant_ratingbar_rating);
            mRatingPrice = view.findViewById(R.id.restaurant_ratingbar_price);
        }
    }
}

