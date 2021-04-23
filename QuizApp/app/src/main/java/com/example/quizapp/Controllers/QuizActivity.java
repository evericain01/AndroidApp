package com.example.quizapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.quizapp.R;

public class QuizActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (getType().equals("multiple")) {
            MultipleChoiceQuizFragment multipleChoiceQuizFragment = new MultipleChoiceQuizFragment();
            fragmentTransaction.add(R.id.fragment_container, multipleChoiceQuizFragment);
        } else {
            TrueOrFalseQuizFragment trueOrFalseQuizFragment = new TrueOrFalseQuizFragment();
            fragmentTransaction.add(R.id.fragment_container, trueOrFalseQuizFragment);
        }
        fragmentTransaction.commit();

    }

    public String getType() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String type = "";
        if (bundle != null) {
            type = (String) bundle.get("type");
        }

        return type;
    }
}