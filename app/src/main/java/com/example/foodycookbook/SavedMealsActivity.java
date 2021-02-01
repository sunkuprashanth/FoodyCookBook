package com.example.foodycookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.room.Room;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavedMealsActivity extends AppCompatActivity {

    private static final String TAG = "SavedMealsActivity";
    int meals_count;
    LinearLayout meals_ly;
    Cursor cursor;
    Meal_conts meals_one = new Meal_conts();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_meals);

        meals_ly = findViewById(R.id.meals_ly);

        cursor = GlobalData.savedMealsDB.savedMealsDao().get_save_meals();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "onCreate: " + cursor.getString(0));
            get_meal(cursor.getString(0));
            cursor.moveToNext();
        }
        meals_count = cursor.getCount();

    }

    public void get_meal(String id) {

        Call<Meals> mealsCall = GlobalData.meals_api.get_meal_id(id);
        mealsCall.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {
                Log.d(TAG, "onResponse: " + response);
                meals_one = response.body().meals.get(0);
                addMeals();
            }

            @Override
            public void onFailure(Call<Meals> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    public void addMeals() {

        for (int i = 0; i < 1; i++) {

            View v = getLayoutInflater().inflate(R.layout.meal_temp, null, false);

            TextView meal_name = v.findViewById(R.id.meal_name);
            TextView meal_desc = v.findViewById(R.id.meal_desc);
            final TextView meal_id = v.findViewById(R.id.meal_id);
            ImageView meal_pic = v.findViewById(R.id.meal_pic);
            CardView meal_card = v.findViewById(R.id.meal_card);

            meal_id.setText("" + i);
            meal_name.setText(meals_one.strMeal);
            meal_desc.setText(meals_one.strInstructions);
            Picasso.get().load(Uri.parse(meals_one.strMealThumb)).into(meal_pic);

            meal_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goto_full = new Intent(SavedMealsActivity.this, FullMealActivity.class);
                    goto_full.putExtra("id", meal_id.getText().toString());
                    //startActivity(goto_full);
                }
            });

            meals_ly.addView(v);
        }
    }
}