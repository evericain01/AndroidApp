package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class OptionsActivity extends AppCompatActivity {
    Integer categoryNum, totalQuestions;
    String challenge;
    Button startQuiz;
    Button addToQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        startQuiz = findViewById(R.id.startQuizButtn);

        Spinner category = findViewById(R.id.chooseCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        Spinner difficulty = findViewById(R.id.chooseDifficulty);
        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(this, R.array.difficulties, android.R.layout.simple_spinner_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficulty.setAdapter(difficultyAdapter);

        Spinner type = findViewById(R.id.chooseType);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(typeAdapter);

        Spinner total = findViewById(R.id.chooseAmount);
        ArrayAdapter<CharSequence> totalAdapter = ArrayAdapter.createFromResource(this, R.array.amount, android.R.layout.simple_spinner_item);
        totalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        total.setAdapter(totalAdapter);

        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int chosenCategory = category.getSelectedItemPosition() + 9;
                String chosenDifficulty = difficulty.getSelectedItem().toString().toLowerCase();
                String chosenType = type.getSelectedItem().toString();
                if (chosenType.equals("True or False")) {
                    chosenType = "boolean";
                }
                else {
                    chosenType = "multiple";
                }
                int amountOfQuestions = Integer.parseInt(total.getSelectedItem().toString());

                QuestionHandler generator = new QuestionHandler(amountOfQuestions, chosenCategory, chosenDifficulty, chosenType);
                generator.generateQuestions();
            }
        });


    }
}