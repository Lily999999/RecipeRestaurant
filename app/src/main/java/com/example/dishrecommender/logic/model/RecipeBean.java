package com.example.dishrecommender.logic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class RecipeBean {
    public Integer code;
    public List<DataDTO> data;
    public String msg;
    public Boolean ok;

    public static class DataDTO implements Serializable {
        @SerializedName("Id")
        public String id;
        @SerializedName("RecipeId")
        public Integer recipeId;
        @SerializedName("Images")
        public List<?> images;
        @SerializedName("Name")
        public String name;
        @SerializedName("Category")
        public String category;
        @SerializedName("Dietary")
        public List<String> dietary;
        @SerializedName("Description")
        public String description;
        @SerializedName("Keywords")
        public List<String> keywords;
        @SerializedName("Instruction")
        public List<String> instruction;
        @SerializedName("Ingredients")
        public List<String> ingredients;
        @SerializedName("CookTime")
        public Integer cookTime;
        @SerializedName("PerpTime")
        public Integer perpTime;
        @SerializedName("TotalTime")
        public Integer totalTime;
        @SerializedName("Calories")
        public Double calories;
        @SerializedName("Fat")
        public Double fat;
        @SerializedName("SaturatedFat")
        public Double saturatedFat;
        @SerializedName("Sodium")
        public Double sodium;
        @SerializedName("Carbohydrate")
        public Integer carbohydrate;
        @SerializedName("Fiber")
        public Double fiber;
        @SerializedName("Sugar")
        public Double sugar;
        @SerializedName("Protein")
        public Double protein;
    }
}
