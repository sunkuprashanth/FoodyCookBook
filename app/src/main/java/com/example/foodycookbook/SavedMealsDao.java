package com.example.foodycookbook;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SavedMealsDao {


    @Insert
    void insert_meal_id(SavedMeals s);

    @Query("Select * from SavedMeals")
    Cursor get_save_meals();
}
