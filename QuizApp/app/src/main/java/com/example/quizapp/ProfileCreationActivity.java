package com.example.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileCreationActivity extends AppCompatActivity {
    EditText firstNameInput, lastNameInput;
    Button createProfileButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);

        db = new DatabaseHelper(this);
        firstNameInput = findViewById(R.id.firstNameEditText);
        lastNameInput = findViewById(R.id.lastNameEditText);
        createProfileButton = findViewById(R.id.createProfileButton);

        createProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameInput.getText().toString();
                String lastName = lastNameInput.getText().toString();

                boolean result = db.addProfile(getCurrentUserId(), firstName, lastName);

                if (result) {
                    Toast.makeText(ProfileCreationActivity.this, "Profile Created.", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(ProfileCreationActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                } else {
                    Toast.makeText(ProfileCreationActivity.this, "Profile was not Created.", Toast.LENGTH_SHORT).show();
                }
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
}