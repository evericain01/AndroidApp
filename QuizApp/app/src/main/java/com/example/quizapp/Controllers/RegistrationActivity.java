package com.example.quizapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.Models.DatabaseHelper;
import com.example.quizapp.R;

public class RegistrationActivity extends AppCompatActivity {
    EditText usernameInput, passwordInput, confirmPasswordInput;
    Button registerButton;
    TextView loginHereText;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        db = new DatabaseHelper(this);
        usernameInput = findViewById(R.id.usernameEditText);
        passwordInput = findViewById(R.id.passwordEditText);
        confirmPasswordInput = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);
        loginHereText = findViewById(R.id.loginTextView);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                String confirmPassword = confirmPasswordInput.getText().toString();

                if (username.equals("") || password.equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Please make sure all fields are filled.", Toast.LENGTH_SHORT).show();
                } else {
                    if (validatePassword(password)) {
                        if (password.equals(confirmPassword)) {
                            boolean columnNum = db.addUser(username, password);

                            if (columnNum) {
                                Toast.makeText(RegistrationActivity.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                                Intent profileCreation = new Intent(RegistrationActivity.this, ProfileCreationActivity.class);
                                // getting current user id from user table
                                String currentUserID = db.getCurrentUserID(username);
                                profileCreation.putExtra("USER_ID", currentUserID);

                                startActivity(profileCreation);
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Something went wrong with the registration.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        loginHereText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToLogin = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(backToLogin);
            }
        });
    }

    /**
     * Checks for various validations of the password.
     *
     * @param pass The password.
     */
    public boolean validatePassword(String pass) {
        if (pass.length() < 7){
            Toast.makeText(RegistrationActivity.this, "Password is too short (must be 7-15)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (pass.length() > 15){
            Toast.makeText(RegistrationActivity.this, "Password is too long (must be 7-15)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!pass.matches(".*\\d.*")){
            Toast.makeText(RegistrationActivity.this, "No digits found in password.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!pass.matches(".*[a-z].*")) {
            Toast.makeText(RegistrationActivity.this, "No lowercase letters found in password.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}