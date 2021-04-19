package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

public class OptionsActivity extends AppCompatActivity {
    DatabaseHelper db;
    Button startQuiz;
    Button addToQueue;
    Button viewQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        db = new DatabaseHelper(this);
        startQuiz = findViewById(R.id.startQuizButton);
        addToQueue = findViewById(R.id.addToQueueButton);
        viewQueue = findViewById(R.id.viewQueueButtonFromOptions);

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
                String finalType = chosenType.equals("True or False") ? "boolean" : "multiple";
//                if (chosenType.equals("True or False")) {
//                    finalType = "boolean";
//                }
//                else {
//                    finalType = "multiple";
//                }
                int amountOfQuestions = Integer.parseInt(total.getSelectedItem().toString());
                //String finalType1 = finalType;
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            QuestionHandler quiz = new QuestionHandler(amountOfQuestions, chosenCategory, chosenDifficulty, finalType);
//
//                            quiz.setAmount(amountOfQuestions);
//                            quiz.setType(finalType);
//                            quiz.setDifficulty(chosenDifficulty);
//                            quiz.setCategory(chosenCategory);
//                            quiz.generateQuestions();
//                            forLoopHelper(quiz);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
                if (chosenType.equals("True or False")) {
                    Intent quiz = new Intent(OptionsActivity.this, TrueOrfalseQuiz.class);

                    quiz.putExtra("amount", amountOfQuestions);
                    quiz.putExtra("category", chosenCategory);
                    quiz.putExtra("difficulty", chosenDifficulty);
                    quiz.putExtra("type", finalType);

                    startActivity(quiz);
                }
                else {
                    Intent quiz = new Intent(OptionsActivity.this, MultipleChoiceQuiz.class);

                    quiz.putExtra("amount", amountOfQuestions);
                    quiz.putExtra("category", chosenCategory);
                    quiz.putExtra("difficulty", chosenDifficulty);
                    quiz.putExtra("type", finalType);

                    startActivity(quiz);
                }

            }
        });

        addToQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                // Getting current user id
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                String currentUserID = "";
                if (bundle != null) {
                    currentUserID = (String) bundle.get("USER_ID");
                }

                // Storing this quiz into the current user's QUEUE LIST.
                db.addQuizToQueue(currentUserID, amountOfQuestions, chosenCategory, chosenDifficulty, chosenType);
                Toast.makeText(OptionsActivity.this, "Quiz Added To Queue.", Toast.LENGTH_SHORT).show();
            }
        });

        viewQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                String currentUserID = "";
                if (bundle != null) {
                    currentUserID = (String) bundle.get("USER_ID");
                }
                Intent goToViewQueue = new Intent(OptionsActivity.this, QueueActivity.class);
                goToViewQueue.putExtra("USER_ID", currentUserID);
                startActivity(goToViewQueue);
            }
        });

    }
//    public void forLoopHelper(QuestionHandler handler) {
//        for (int i = 0; i < handler.getAmount(); i++ ){
//            System.out.println("Question: " + handler.getQuestions().get(i));
//            System.out.println("Difficulty: " + handler.getDifficultyArr().get(i));
//            System.out.println("Choices: ");
//            System.out.println(handler.getAnswers().get(i));
//            for (int j = 0; j < handler.getIncorrectAnswers().get(i).length(); j++) {
//                try {
//                    System.out.println(handler.getIncorrectAnswers().get(i).getString(j));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println("");
//        }
//    }
}