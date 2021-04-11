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
    Button viewCategories;
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
        viewCategories = findViewById(R.id.viewCategoriesButton);
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

        db.setExperiencePoints(currentUserID, "50");
//        double level = .50 * Math.sqrt(Integer.parseInt(db.getExperiencePoints(currentUserID)));

//        levelText.setText(String.valueOf(level));

        // gets the total experience points of the current user (max 100)
        expText.setText(db.getExperiencePoints(currentUserID) + ": EXP");

        // sets the value of the progress bar (progress bar can only take a max of 100)
        progressBar.setProgressCompat(10, true);

        viewCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseCategoryIntent = new Intent(HomePageActivity.this, OptionsActivity.class);
                startActivity(chooseCategoryIntent);
            }
        });
    }

//    @Override
//    public void calculateLevel(String userID, String currentExp, String level) {
//        int level = 0;
//        double maxXp = calcXpForLevel(0);
//        do {
//            maxXp += calcXpForLevel(++level);
//        } while (maxXp < xp);
//        return level;
//
//    }
}