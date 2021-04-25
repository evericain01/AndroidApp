package com.example.quizapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizapp.R;

public class ResultActivity extends AppCompatActivity {
    TextView experienceGained, levelStage, percentageScore, fractionScore;
    Button homePageButton, doAnotherQuizButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        experienceGained = findViewById(R.id.experienceGainedText);
        levelStage = findViewById(R.id.levelStageTitle);
        percentageScore = findViewById(R.id.percentageScoreText);
        fractionScore = findViewById(R.id.fractionScoreText);

        experienceGained.setText("EXP: +" + String.valueOf(getExperienceGained()));
        levelStage.setText(determineLevelStage());
        percentageScore.setText(String.valueOf((int) convertFractionToPercentage()) + "%");
        fractionScore.setText("(" + getUserScore() +"/" + getUserAmountOfQuestions() + ")");

        startAnimation();

        homePageButton = findViewById(R.id.backToHomePageButton);
        doAnotherQuizButton = findViewById(R.id.doAnotherQuizButton);

        homePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHomePage = new Intent(ResultActivity.this, HomePageActivity.class);
                goToHomePage.putExtra("USER_ID", getCurrentUserId());
                startActivity(goToHomePage);
            }
        });

        doAnotherQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToOptionsPage = new Intent(ResultActivity.this, OptionsActivity.class);
                goToOptionsPage.putExtra("USER_ID", getCurrentUserId());
                startActivity(goToOptionsPage);
            }
        });

    }


    private double convertFractionToPercentage() {
        double percentageScore;

        double score = getUserScore();
        double amount = getUserAmountOfQuestions();

        percentageScore = score / amount * 100;

        return percentageScore;
    }


    private String determineLevelStage() {
        String levelStage;
        double score = convertFractionToPercentage();
        if (score <= 33) {
            levelStage = "Beginner";
        } else if (score > 33 && score <= 66) {
            levelStage = "Intermediate";
        } else {
            levelStage = "Advanced";
        }

        return levelStage;
    }

    private void startAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        experienceGained.startAnimation(animation);
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Go back to Home Page?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent quitQuiz = new Intent(ResultActivity.this, HomePageActivity.class);
                        quitQuiz.putExtra("USER_ID", getCurrentUserId());
                        startActivity(quitQuiz);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Gets the current user ID.
     *
     * @return The user ID as a String.
     */
    public String getCurrentUserId() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String currentUserID = "";
        if (bundle != null) {
            currentUserID = (String) bundle.get("USER_ID");
        }

        return currentUserID;
    }


    /**
     * Gets the current user ID.
     *
     * @return The user ID as a String.
     */
    public int getUserScore() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int score = 0;
        if (bundle != null) {
            score = (int) bundle.get("score");
        }

        return score;
    }

    /**
     * Gets the current user ID.
     *
     * @return The user ID as a String.
     */
    public int getUserAmountOfQuestions() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int amount = 0;
        if (bundle != null) {
            amount = (int) bundle.get("amount");
        }

        return amount;
    }

    /**
     * Gets the current user ID.
     *
     * @return The user ID as a String.
     */
    public int getExperienceGained() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Integer experienceGained = 0;
        if (bundle != null) {
            experienceGained = (Integer) bundle.get("experienceGained");
        }

        return experienceGained;
    }
}