package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // Open Trivia DB
    // https://opentdb.com/api.php?amount=10&category=9&difficulty=medium
    // amount tag is the amount of questions
    // category tag is only given when a category is selected otherwise its all categories
    // difficulty is how hard the question is
    // https://opentdb.com/api_category.php list of all categories in the db

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}