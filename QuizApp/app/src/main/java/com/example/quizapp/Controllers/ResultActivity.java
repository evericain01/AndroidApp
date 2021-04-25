package com.example.quizapp.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizapp.R;

public class ResultActivity extends AppCompatActivity {
    TextView experienceGained, levelStage, percentageScore, fractionScore;
    Button homePageButton, doAnotherQuizButton;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        addNotification();

    }


    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(ResultActivity.this, "My Notification");
        builder.setContentTitle("This is my first example notification");
        builder.setContentText("Hey, URGENT Message");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);

        Intent notificationIntent = new Intent(this, NotificationView.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());

    }

    /**
     * Prompts the Exp gained animation.
     */
    private void startAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        experienceGained.startAnimation(animation);
    }

    /**
     * When pressing the back button it'll prompted a message saying if the user wants to go the home
     * page activity.
     */
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
     * Gets the percentage value of the user's score.
     * @return The percentage score.
     */
    private double convertFractionToPercentage() {
        double percentageScore;
        double score = getUserScore();
        double amount = getUserAmountOfQuestions();

        percentageScore = score / amount * 100;

        return percentageScore;
    }

    /**
     * Getting the appropriate level stage.
     * @return level stage.
     */
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
     * Gets the user's score.
     *
     * @return Gets the user's score.
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
     * Gets the amount of questions of the quiz.
     *
     * @return The amount of questions.
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
     * Gets the experience points gained from the quiz.
     *
     * @return The gained experience points.
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

    /**
     * Initializing the options menu.
     *
     * @param menu The desired menu format.
     * @return true;
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    /**
     * Navigates to Modify Profile or Logout depending on which option menu item has been click.
     *
     * @param item The item in the options menu.
     * @return True.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutOption:
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ResultActivity.super.onBackPressed();
                                Intent logout = new Intent(ResultActivity.this, LoginActivity.class);
                                startActivity(logout);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}