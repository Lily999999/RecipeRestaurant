package com.example.dishrecommender.logic.net;

import com.example.dishrecommender.logic.model.BaseBean;
import com.example.dishrecommender.logic.model.CollectionRecipeBean;
import com.example.dishrecommender.logic.model.RecipeBean;
import com.example.dishrecommender.logic.model.RestaurantBean;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RestaurantInterface {

    @FormUrlEncoded
    @POST("api/user/login")
    Single<BaseBean> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/user/register")
    Single<BaseBean> register(@Field("username") String username, @Field("password") String password);

    @GET("api/restaurant")
    Single<RestaurantBean> getRestaurant(@Query("latitude") String latitude, @Query("longitude") String longitude, @Query("term") String term);

    // (QueryMap: dietary cook_time perp_time total_time taste ingredients)
    @GET("api/recipe")
    Single<RecipeBean> getRecipe(@Query("limit") int limit, @Query("page") int page, @QueryMap Map<String, String> param);

    @FormUrlEncoded
    @POST("api/user/collection")
    Single<BaseBean> addCollectionRecipe(@Field("collect_type") String id, @Field("recipe_id")String recipe_id);

    @FormUrlEncoded
    @POST("api/user/collection")
    Single<BaseBean> addCollectionRestaurant(@Field("collect_type") String id, @Field("restaurant_id") String restaurant_id);

    @DELETE("api/user/collection")
    Single<BaseBean> deleteCollection(@Query("id") String id);

    // (collect_type 1 is restaurant 2 is recipe)
    @GET("api/user/collection")
    Single<CollectionRecipeBean> getCollection(@Query("collect_type") String collect_type, @Query("limit") String limit, @Query("page") String page);

}
