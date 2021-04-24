package com.example.quizapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.quizapp.R;

public class ResultActivity extends AppCompatActivity {

    TextView experienceGained;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        experienceGained = findViewById(R.id.experienceGainedText);

//        startAnimation();
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
    public int getUserScore() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Integer score = 0;
        if (bundle != null) {
            score = (Integer) bundle.get("score");
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
        Integer amount = 0;
        if (bundle != null) {
            amount = (Integer) bundle.get("amount");
        }

        return amount;
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
}