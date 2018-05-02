package com.example.kenneth.hiit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class foodActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<ModelFood> foodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        recyclerView = findViewById(R.id.rv);

        foodsList = new ArrayList<>();
        foodsList.add(new ModelFood(R.drawable.signin, "Healthy Chicken and Veggies", "chicken breasts, red onion, brocolini", "240Cal"));
        foodsList.add(new ModelFood(R.drawable.signin, "Lemon Roasted Salmon With Sweet Potatoes", "sweet potatoes, brocolini, salmon filets", "282Cal"));
        foodsList.add(new ModelFood(R.drawable.signin, "Paleo Fried Rice", "onion, eggs, green onions", "272Cal"));
        foodsList.add(new ModelFood(R.drawable.signin, "Lemon Rosemary Chicken", "chicken breasts, sweet potatoes, lemon,rosemary", "250Cal"));
        foodsList.add(new ModelFood(R.drawable.signin, "Healthy Chicken and Veggies", "chicken breasts, red onion, brocolini", "240Cal"));

        foodsList.add(new ModelFood(R.drawable.signin, "Lemon Roasted Salmon With Sweet Potatoes", "sweet potatoes, brocolini, salmon filets", "282Cal"));

        foodsList.add(new ModelFood(R.drawable.signin, "Paleo Fried Rice", "onion, eggs, green onions", "272Cal"));

        foodsList.add(new ModelFood(R.drawable.signin, "Lemon Rosemary Chicken", "chicken breasts, sweet potatoes, lemon,rosemary", "250Cal"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;
        recyclerView.setLayoutManager(rvLiLayoutManager);

        FoodAdapter adapter = new FoodAdapter(this,foodsList);

        recyclerView.setAdapter(adapter);
    }
}
