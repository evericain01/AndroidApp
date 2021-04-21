package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

public class TrueOrFalseQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_orfalse_quiz);
        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");

        FragmentManager fm = getSupportFragmentManager();
        TrueOrFalse trueOrfalseQuiz = (TrueOrFalse) fm.findFragmentById(R.id.fragment2);
//        trueOrfalseQuiz.changeData(msg);

    }
}