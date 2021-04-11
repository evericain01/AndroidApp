package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.progressindicator.LinearProgressIndicator;

public class HomePageActivity extends AppCompatActivity {

    DatabaseHelper db;
    TextView welcomeTitle;
    TextView levelText;
    TextView expNeededText;
    Button optionsButton;
    Button viewQueue;
    LinearProgressIndicator progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        db = new DatabaseHelper(this);

        welcomeTitle = findViewById(R.id.homePageTitleText);
        levelText = findViewById(R.id.levelText);
        expNeededText = findViewById(R.id.expNeededText);
        optionsButton = findViewById(R.id.playButton);
        viewQueue = findViewById(R.id.viewQueueButton);
        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String currentUserID = "";
        if (bundle != null) {
            currentUserID = (String) bundle.get("USER_ID");
        }

        // setting First and Last name of current user
        welcomeTitle.setText("Welcome, " + db.getFirstAndLastName(currentUserID));

        db.setExperiencePoints(currentUserID, "2400");


        // gets the total experience points of the current user (max 100)
        int exp = Integer.parseInt(db.getExperiencePoints(currentUserID));
        System.out.println(exp);

        // Calculating level based on experience
        int level = Experience.calculateLevel((double) exp);
        System.out.println(level);

        // Displaying level text as a string
        levelText.setText("LEVEL: " + String.valueOf(level));

        double expNeeded = Experience.nextLevelXpNeeded((double) exp);
        System.out.println(expNeeded);

        expNeededText.setText(String.valueOf(expNeeded) + " :EXP NEEDED");

         int barProgression = Experience.progressionRate((double) exp);
        System.out.println("Bar Progression: " + barProgression);

        // sets the value of the progress bar (progress bar can only take a max of 100)
        progressBar.setProgressCompat(barProgression, true);

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToOptions = new Intent(HomePageActivity.this, OptionsActivity.class);
                startActivity(goToOptions);
            }
        });

        viewQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToQueue = new Intent(HomePageActivity.this, QueueActivity.class);
                startActivity(goToQueue);
            }
        });
    }
}