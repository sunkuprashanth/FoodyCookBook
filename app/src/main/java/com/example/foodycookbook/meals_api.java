package com.example.foodycookbook;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface meals_api {

    @GET("/api/json/v1/1/search.php")
    Call<Meals> get_meals(@Query("s") String search_param);


    @GET("/api/json/v1/1/lookup.php")
    Call<Meals> get_meal_id(@Query("i") String search_param);

}
