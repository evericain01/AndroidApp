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
    TextView expText;
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
        expText = findViewById(R.id.expText);
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

        // gets the total experience points of the current user (max 100)
        expText.setText(db.getExperiencePoints(currentUserID) + ": EXP");

        // sets the value of the progress bar (progress bar can only take a max of 100)
        progressBar.setProgressCompat(10, true);

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