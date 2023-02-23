package com.example.dishrecommender.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dishrecommender.R;
import com.example.dishrecommender.logic.model.CollectionRecipeBean;
import com.example.dishrecommender.logic.model.RecipeBean;


public class CollectionRecipeAdapter extends ListAdapter<CollectionRecipeBean.DataDTO, CollectionRecipeAdapter.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position,View view2);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public CollectionRecipeAdapter(@NonNull DiffUtil.ItemCallback<CollectionRecipeBean.DataDTO> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection_rv_recipe, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeBean.DataDTO data = getItem(position).collection_data;
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, holder.getAbsoluteAdapterPosition(),holder.mIvCover);
                }
            });
        }
        holder.mTvName.setText(data.name);
        holder.mTvCategory.setText(data.category);
        holder.mTvDescription.setText(data.description);
        if (!data.images.isEmpty()) {
            Glide.with(holder.mIvCover).load(data.images.get(0)).centerCrop().error(R.drawable.ic_twitter).into(holder.mIvCover);
        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvName;
        TextView mTvDescription;
        TextView mTvCategory;
        public ImageView mIvCover;

        public ViewHolder(View view) {
            super(view);
            mTvName = view.findViewById(R.id.collection_tv_recipe_name);
            mTvDescription = view.findViewById(R.id.collection_tv_recipe_description);
            mTvCategory = view.findViewById(R.id.collection_tv_recipe_categories);
            mIvCover = view.findViewById(R.id.collection_iv_recipe_url);
        }
    }
}