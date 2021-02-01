package com.example.foodycookbook;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GlobalData {

    public static SavedMeals savedMeals;
    public static SavedMealsDB savedMealsDB;

    public static List<Meal_conts> meals_all = new ArrayList<Meal_conts>();

    public static Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.themealdb.com/")
            .addConverterFactory(GsonConverterFactory.create()).build();

    public static meals_api meals_api = retrofit.create(meals_api.class);

}
