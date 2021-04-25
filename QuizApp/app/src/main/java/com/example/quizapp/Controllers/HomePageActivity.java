package com.example.quizapp.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizapp.Models.DatabaseHelper;
import com.example.quizapp.Models.Experience;
import com.example.quizapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DatabaseHelper db;
    TextView nameTitle, levelText, expNeededText, menuFullName, menuLevel;
    Button optionsButton, viewQueue;
    Toolbar toolbar;
    LinearProgressIndicator progressBar;
    DrawerLayout drawer;
    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(this);
        nameTitle = findViewById(R.id.homePageTitleText);
        optionsButton = findViewById(R.id.playButton);
        viewQueue = findViewById(R.id.viewQueueButton);

        nameTitle.setText(db.getFirstAndLastName(getCurrentUserId()));

        experienceHelper();
        initializeDrawerMenu();

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

    public void experienceHelper() {
        levelText = findViewById(R.id.levelText);
        expNeededText = findViewById(R.id.expNeededText);
        progressBar = findViewById(R.id.progressBar);

        // Gets the total experience points of the current user
        int exp = Integer.parseInt(db.getExperiencePoints(getCurrentUserId()));
        System.out.println(exp);

        // Calculating level based on experience
        level = Experience.calculateLevel((double) exp);
        System.out.println("Level: " + level);

        // Displaying level text as a string
        levelText.setText("LEVEL: " + String.valueOf(level));

        // Gets the experience needed to level up
        double expNeeded = Experience.nextLevelXpNeeded(exp);
        System.out.println("Exp needed:" + expNeeded);
        expNeededText.setText(String.valueOf((int) expNeeded) + " EXP NEED FOR LEVEL UP");

        // Getting progressionRate through level.
        int barProgression = Experience.progressionRate((double) exp);
        System.out.println("Bar Progression: " + barProgression);

        // sets the value of the progress bar (progress bar can only take a max of 100)
        progressBar.setProgress(barProgression, true);
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
     * When pressing the back button it'll prompted a message saying if the user wants to quit the app.
     */
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to quit the app?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Initializing drawer menu.
     */
    public void initializeDrawerMenu() {
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        menuFullName = headerView.findViewById(R.id.menuFullNameText);
        menuFullName.setText(db.getFirstAndLastName(getCurrentUserId()));
        menuLevel = headerView.findViewById(R.id.menuLevelText);
        menuLevel.setText("Level " + String.valueOf(level));
        System.out.println("Level: " + level);
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
            case R.id.nav_logout:
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                HomePageActivity.super.onBackPressed();
                                Intent logout = new Intent(HomePageActivity.this, LoginActivity.class);
                                startActivity(logout);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            default:
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            case R.id.modifyProfile:
                Intent modifyProfile = new Intent(HomePageActivity.this, ModifyProfileActivity.class);
                modifyProfile.putExtra("USER_ID", getCurrentUserId());
                startActivity(modifyProfile);
                return true;
            case R.id.logoutOption:
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                HomePageActivity.super.onBackPressed();
                                Intent logout = new Intent(HomePageActivity.this, LoginActivity.class);
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