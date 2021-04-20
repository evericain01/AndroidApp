package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DatabaseHelper db;

    TextView welcomeTitle;
    TextView levelText;
    TextView expNeededText;
    Button optionsButton;
    Button viewQueue;
    LinearProgressIndicator progressBar;

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        db = new DatabaseHelper(this);
        welcomeTitle = findViewById(R.id.homePageTitleText);
        levelText = findViewById(R.id.levelText);
        expNeededText = findViewById(R.id.expNeededText);
        optionsButton = findViewById(R.id.playButton);
        viewQueue = findViewById(R.id.viewQueueButton);
        progressBar = findViewById(R.id.progressBar);

        // setting First and Last name of current user
        welcomeTitle.setText("Welcome, \n" + db.getFirstAndLastName(getCurrentUserId()));

        db.setExperiencePoints(getCurrentUserId(), "2400");

        // gets the total experience points of the current user (max 100)
        int exp = Integer.parseInt(db.getExperiencePoints(getCurrentUserId()));
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
                goToOptions.putExtra("USER_ID", getCurrentUserId());
                startActivity(goToOptions);
            }
        });

        viewQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToQueue = new Intent(HomePageActivity.this, QueueActivity.class);
                goToQueue.putExtra("USER_ID", getCurrentUserId());
                startActivity(goToQueue);

            }
        });
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
     * When pressing the back button as the drawer menu is toggled.
     * It will simply close the drawer menu instead of quitting the entire activity.
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Navigates to Start Quiz, View Queue or View Quiz Options activities depending on which menu item has been click.
     * @param item The clicked menu item.
     * @return True.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_start_quiz:
                Intent goToOptions = new Intent(HomePageActivity.this, OptionsActivity.class);
                goToOptions.putExtra("USER_ID", getCurrentUserId());
                startActivity(goToOptions);
                break;
            case R.id.nav_queue:
                Intent goToQueue = new Intent(HomePageActivity.this, QueueActivity.class);
                goToQueue.putExtra("USER_ID", getCurrentUserId());
                startActivity(goToQueue);
                break;
            case R.id.nav_view_all_quiz_options:
                Intent viewAllQuizOptions = new Intent(HomePageActivity.this, ViewAllOptionsActivity.class);
                startActivity(viewAllQuizOptions);
                break;
                default:
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}