package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomePageActivity extends AppCompatActivity {

    DatabaseHelper db;
    TextView welcomeTitle;
    Button viewCategories;
    Button viewQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        db = new DatabaseHelper(this);

        welcomeTitle = findViewById(R.id.homePageTitleText);
        viewCategories = findViewById(R.id.viewCategoriesButton);
        viewQueue = findViewById(R.id.viewQueueButton);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String currentUserID = "";
        if (bundle != null) {
            currentUserID = (String) bundle.get("USER_ID");
        }

//        String currentUserID = db.getCurrentUserID();
//        welcomeTitle.setText("Welcome, " + db.getFistAndLastName(currentUserID));
//        Toast.makeText(HomePageActivity.this, currentUserID, Toast.LENGTH_SHORT).show();
    }
}