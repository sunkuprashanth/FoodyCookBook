package com.example.foodycookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    LinearLayout meals_ly;
    int meals_count = 10;
    ImageView saved, search_btn;
    EditText search_bar;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.null_case);
        meals_ly = findViewById(R.id.meals_ly);
        saved = findViewById(R.id.saved);
        search_btn = findViewById(R.id.search_btn);
        search_bar = findViewById(R.id.search_bar);

        tv.setVisibility(View.GONE);

        GlobalData.savedMeals = new SavedMeals();
        GlobalData.savedMealsDB = Room.databaseBuilder(this, SavedMealsDB.class, "saved_meals").allowMainThreadQueries().build();

        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goto_saved = new Intent(MainActivity.this, SavedMealsActivity.class);
                startActivity(goto_saved);
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search_str = search_bar.getText().toString();
                get_all_meals(search_str);
            }
        });

        get_all_meals("");
    }

    public void get_all_meals(String search) {

        tv.setVisibility(View.GONE);
        Call<Meals> get_meals = GlobalData.meals_api.get_meals(search);
        get_meals.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {
                //Log.d(TAG, "onResponse: " + response.body().meals.size());
                GlobalData.meals_all = response.body().meals;
                addMeals();
            }

            @Override
            public void onFailure(Call<Meals> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void addMeals() {

        meals_ly.removeAllViews();
        if(GlobalData.meals_all == null) {
            tv.setVisibility(View.VISIBLE);
            return;
        }
        meals_count = GlobalData.meals_all.size();
        for (int i = 0; i < meals_count; i++) {

            View v = getLayoutInflater().inflate(R.layout.meal_temp, null, false);

            TextView meal_name = v.findViewById(R.id.meal_name);
            TextView meal_desc = v.findViewById(R.id.meal_desc);
            final TextView meal_id = v.findViewById(R.id.meal_id);
            ImageView meal_pic = v.findViewById(R.id.meal_pic);
            CardView meal_card = v.findViewById(R.id.meal_card);

            meal_id.setText("" + i);
            meal_name.setText(GlobalData.meals_all.get(i).strMeal);
            meal_desc.setText(GlobalData.meals_all.get(i).strInstructions);
            Picasso.get().load(Uri.parse(GlobalData.meals_all.get(i).strMealThumb)).into(meal_pic);

            meal_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goto_full = new Intent(MainActivity.this, FullMealActivity.class);
                    goto_full.putExtra("id", meal_id.getText().toString());
                    startActivity(goto_full);
                }
            });

            meals_ly.addView(v);
        }
    }
}