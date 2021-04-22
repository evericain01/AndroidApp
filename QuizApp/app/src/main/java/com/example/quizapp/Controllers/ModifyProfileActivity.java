package com.example.quizapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizapp.Models.DatabaseHelper;
import com.example.quizapp.R;

public class ModifyProfileActivity extends AppCompatActivity {
    EditText newFirstName, newLastName, oldPassword, newPassword, retypeNewPassword;
    Button confirmChanges;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);

        db = new DatabaseHelper(this);
        newFirstName = findViewById(R.id.newFirstNameEditText);
        newLastName = findViewById(R.id.newLastNameEditText);
        oldPassword = findViewById(R.id.oldPasswordEditText);
        newPassword = findViewById(R.id.newPasswordEditText);
        retypeNewPassword = findViewById(R.id.retypePasswordEditText);
        confirmChanges = findViewById(R.id.confirmChangesButton);

        confirmChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameInput = newFirstName.getText().toString();
                String lastNameInput = newLastName.getText().toString();
                String oldPasswordInput = oldPassword.getText().toString();
                String newPasswordInput = newPassword.getText().toString();
                String retypeNewPasswordInput = retypeNewPassword.getText().toString();

                if (!firstNameInput.equals("") && !lastNameInput.equals("")) {
                    db.updateFirstName(getCurrentUserId(), firstNameInput);
                    db.updateLastName(getCurrentUserId(), lastNameInput);
                    Toast.makeText(ModifyProfileActivity.this, "First/Last Name Successfully Changed.", Toast.LENGTH_LONG).show();
                    sendToHomePageIntent();
                } else if (!firstNameInput.equals("")) {
                    db.updateFirstName(getCurrentUserId(), firstNameInput);
                    Toast.makeText(ModifyProfileActivity.this, "First Name Successfully Changed.", Toast.LENGTH_LONG).show();
                    sendToHomePageIntent();
                } else if (!lastNameInput.equals("")) {
                    db.updateLastName(getCurrentUserId(), lastNameInput);
                    Toast.makeText(ModifyProfileActivity.this, "Last Name Successfully Changed.", Toast.LENGTH_LONG).show();
                    sendToHomePageIntent();
                } else {
                    boolean result = db.checkOldPassword(oldPasswordInput);
                    if (result) {
                        if (newPasswordInput.equals(retypeNewPasswordInput)) {
                            db.updatePassword(getCurrentUserId(), newPasswordInput);
                            Toast.makeText(ModifyProfileActivity.this, "Passwords Successfully Changed.", Toast.LENGTH_LONG).show();
                            sendToHomePageIntent();
                        } else {
                            Toast.makeText(ModifyProfileActivity.this, "Passwords do not match.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ModifyProfileActivity.this, "Old password is not correct.", Toast.LENGTH_LONG).show();
                    }
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

    /**
     * Send the user to the HomePage activity.
     */
    public void sendToHomePageIntent() {
        Intent homeIntent = new Intent(ModifyProfileActivity.this, HomePageActivity.class);
        homeIntent.putExtra("USER_ID", getCurrentUserId());
        startActivity(homeIntent);
    }

}