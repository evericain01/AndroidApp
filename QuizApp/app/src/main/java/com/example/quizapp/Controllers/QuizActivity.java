package com.example.quizapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to quit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent quitQuiz = new Intent(QuizActivity.this, HomePageActivity.class);
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
}