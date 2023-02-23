package com.example.dishrecommender.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dishrecommender.R;
import com.example.dishrecommender.logic.model.RecipeBean;

import java.util.Objects;


public class RecipeAdapter extends ListAdapter<RecipeBean.DataDTO, RecipeAdapter.ViewHolder> {

    private RecipeAdapter.OnItemClickListener mOnItemClickListener;
    private RecipeAdapter.OnItemClickListener2 mOnItemClickListener2;

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ImageView imageView);
    }

    public interface OnItemClickListener2 {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener2(OnItemClickListener2 mOnItemClickListener2) {
        this.mOnItemClickListener2 = mOnItemClickListener2;
    }

    public void setOnItemClickListener(RecipeAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public RecipeAdapter(@NonNull DiffUtil.ItemCallback<RecipeBean.DataDTO> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_rv, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeBean.DataDTO data = getItem(position);
        if (mOnItemClickListener != null) {
            holder.mIvCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, holder.getAbsoluteAdapterPosition(), holder.mIvCover);
                }
            });
            holder.mTvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, holder.getAbsoluteAdapterPosition(), holder.mIvCover);
                }
            });
            holder.mTvCategories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, holder.getAbsoluteAdapterPosition(), holder.mIvCover);
                }
            });
        }
        if (Objects.equals(data.name, "")) {
            holder.mTvName.setText("no name");

        } else {
            holder.mTvName.setText(data.name);
        }
        holder.mTvCategories.setText(data.category);
        if (!data.images.isEmpty()) {
            Glide.with(holder.mIvCover).load(data.images.get(0)).centerCrop().error(R.drawable.ic_twitter).into(holder.mIvCover);
        }
        if (mOnItemClickListener != null) {
            holder.mIvUnStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.mIvUnStar.setVisibility(View.INVISIBLE);
                    holder.mIvStar.setVisibility(View.VISIBLE);
                     mOnItemClickListener2.onItemClick(view, holder.getAbsoluteAdapterPosition());
                }
            });
            holder.mIvStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.mIvStar.setVisibility(View.INVISIBLE);
                    holder.mIvUnStar.setVisibility(View.VISIBLE);
                    // mOnItemClickListener2.onItemClick(view, holder.getAbsoluteAdapterPosition());
                }
            });
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvName;
        TextView mTvCategories;
        ImageView mIvCover;
        ImageView mIvStar;
        ImageView mIvUnStar;
        CardView mCardView;

        public ViewHolder(View view) {
            super(view);
            mTvName = view.findViewById(R.id.recipe_tv_name);
            mTvCategories = view.findViewById(R.id.recipe_tv_categories);
            mIvCover = view.findViewById(R.id.recipe_iv_url);
            mIvStar = view.findViewById(R.id.recipe_star);
            mIvUnStar = view.findViewById(R.id.recipe_unstar);
            mCardView = view.findViewById(R.id.recipe_cardView);
        }
    }
}
