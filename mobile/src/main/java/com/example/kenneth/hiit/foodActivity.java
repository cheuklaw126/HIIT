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

        foodsList.add(new ModelFood(R.drawable.signin, "Karachi Savour", "haleem", "4"));
        foodsList.add(new ModelFood(R.drawable.signin, "haleem", "Chaman Savour", "6"));
        foodsList.add(new ModelFood(R.drawable.signin, "chaat", "Karachi Foods", "3"));
        foodsList.add(new ModelFood(R.drawable.signin, "Kare", "Karachi Foods", "7"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;
        recyclerView.setLayoutManager(rvLiLayoutManager);

        FoodAdapter adapter = new FoodAdapter(this,foodsList);

        recyclerView.setAdapter(adapter);
    }
}
