package com.example.quizapp;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;

import java.util.ArrayList;

public class OptionsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DatabaseHelper db;
    Button startQuiz;
    Button addToQueue;
    Button viewQueue;
    TextView menuFullName;
    TextView menuLevel;
    Toolbar toolbar;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeDrawerMenu();

        db = new DatabaseHelper(this);
        startQuiz = findViewById(R.id.startQuizButton);
        addToQueue = findViewById(R.id.addToQueueButton);
        viewQueue = findViewById(R.id.viewQueueButtonFromOptions);

        menuFullName.setText(db.getFirstAndLastName(getCurrentUserId()));

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

                if (chosenType.equals("True or False")) {
                    Intent quiz = new Intent(OptionsActivity.this, TrueOrFalseQuiz.class);

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

                // Storing this quiz into the current user's QUEUE LIST.
                db.addQuizToQueue(getCurrentUserId(), amountOfQuestions, chosenCategory, chosenDifficulty, chosenType);
                Toast.makeText(OptionsActivity.this, "Quiz Added To Queue.", Toast.LENGTH_SHORT).show();
            }
        });

        viewQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToViewQueue = new Intent(OptionsActivity.this, QueueActivity.class);
                goToViewQueue.putExtra("USER_ID", getCurrentUserId());
                startActivity(goToViewQueue);
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
     * Initializing drawer menu.
     */
    public void initializeDrawerMenu() {
        drawer = findViewById(R.id.drawer_layout_options);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        menuFullName = headerView.findViewById(R.id.menuFullNameText);
        menuLevel = headerView.findViewById(R.id.menuLevelText);
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
                Intent goToOptions = new Intent(OptionsActivity.this, OptionsActivity.class);
                goToOptions.putExtra("USER_ID", getCurrentUserId());
                startActivity(goToOptions);
                break;
            case R.id.nav_queue:
                Intent goToQueue = new Intent(OptionsActivity.this, QueueActivity.class);
                goToQueue.putExtra("USER_ID", getCurrentUserId());
                startActivity(goToQueue);
                break;
            case R.id.nav_view_all_quiz_options:
                Intent viewAllQuizOptions = new Intent(OptionsActivity.this, ViewAllOptionsActivity.class);
                startActivity(viewAllQuizOptions);
                break;
            case R.id.nav_logout:
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                OptionsActivity.super.onBackPressed();
                                Intent logout = new Intent(OptionsActivity.this, LoginActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.modifyProfile:
                Intent modifyProfile = new Intent(OptionsActivity.this, ModifyProfileActivity.class);
                modifyProfile.putExtra("USER_ID", getCurrentUserId());
                startActivity(modifyProfile);
                return true;
            case R.id.logoutOption:
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                OptionsActivity.super.onBackPressed();
                                Intent logout = new Intent(OptionsActivity.this, LoginActivity.class);
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