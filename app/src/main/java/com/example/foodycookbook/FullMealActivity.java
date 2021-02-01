package com.example.foodycookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class FullMealActivity extends AppCompatActivity {

    TextView meal_name, meal_link, meal_desc;
    ImageView meal_pic, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_meal);

        meal_name = findViewById(R.id.meal_name);
        meal_desc = findViewById(R.id.meal_desc);
        meal_pic = findViewById(R.id.meal_pic);
        meal_link = findViewById(R.id.meal_link);
        save = findViewById(R.id.save);

        Intent prev = getIntent();
        final int meal_id = Integer.parseInt(prev.getStringExtra("id"));
        Toast.makeText(FullMealActivity.this, "C " + meal_id, Toast.LENGTH_SHORT).show();

        meal_name.setText(GlobalData.meals_all.get(meal_id).strMeal);
        meal_link.setText(GlobalData.meals_all.get(meal_id).strYoutube);
        meal_desc.setText(GlobalData.meals_all.get(meal_id).strInstructions);
        Picasso.get().load(Uri.parse(GlobalData.meals_all.get(meal_id).strMealThumb)).into(meal_pic);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save.setImageDrawable(getResources().getDrawable(R.drawable.save_after));
                SavedMeals savedMeals = new SavedMeals();
                savedMeals.setMeal_id("" + GlobalData.meals_all.get(meal_id).idMeal);
                GlobalData.savedMealsDB.savedMealsDao().insert_meal_id(savedMeals);
            }
        });
    }
}