package com.example.dishrecommender.logic.model;

import com.example.dishrecommender.logic.net.RestaurantInterface;
import com.example.dishrecommender.logic.net.RetrofitServiceManager;

import io.reactivex.rxjava3.core.Single;


public class RestaurantRepository {
    public final RestaurantInterface mRestaurantService = RetrofitServiceManager.getInstance().create(RestaurantInterface.class);

    private RestaurantRepository() {
    }

    public static RestaurantRepository getSingleton() {
        return Inner.instance;
    }

    private static class Inner {
        private static final RestaurantRepository instance = new RestaurantRepository();
    }
    public Single<BaseBean> login(String username,String password) {
        return mRestaurantService.login(username,password);
    }
    public Single<RestaurantBean> getRestaurant(String latitude,String longitude,String term){
        return mRestaurantService.getRestaurant(latitude, longitude, term);
    }
}
