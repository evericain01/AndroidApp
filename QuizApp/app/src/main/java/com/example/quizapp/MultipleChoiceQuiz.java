package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class MultipleChoiceQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice_quiz);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        ArrayList<String> questions = (ArrayList<String>) bundle.get("questions");
        ArrayList<String> answers = (ArrayList<String>) bundle.get("answes");

        System.out.println(questions);

    }
}