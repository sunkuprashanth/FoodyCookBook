package com.example.foodycookbook;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = SavedMeals.class, version = 1)
public abstract class SavedMealsDB extends RoomDatabase {

    public abstract SavedMealsDao savedMealsDao();
}
