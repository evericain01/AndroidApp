package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity {
    EditText usernameInput;
    EditText passwordInput;
    Button loginButton;
    TextView registerHereText;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    QuestionHandler temp = new QuestionHandler(20, 8, "", "boolean");
//                    temp.generateQuestions();
//                    forLoopHelper(temp);

                    temp.setType("boolean");
                    temp.setDifficulty("easy");
                    temp.generateQuestions();
                    forLoopHelper(temp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();



        db = new DatabaseHelper(this);
        usernameInput = findViewById(R.id.usernameEditText);
        passwordInput = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerHereText = findViewById(R.id.registerTextView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                boolean result = db.checkLogin(username, password);
                if(result)
                {
                    Intent categoryIntent = new Intent(LoginActivity.this, HomePageActivity.class);
                    // getting current user id from user table
                    String currentUserID = db.getCurrentUserID();
                    categoryIntent.putExtra("USER_ID", currentUserID);
                    Toast.makeText(LoginActivity.this,currentUserID,Toast.LENGTH_SHORT).show();
                    startActivity(categoryIntent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Invalid Username or Password.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerHereText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registeringIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(registeringIntent);
            }
        });
    }

    public void forLoopHelper(QuestionHandler handler) {
        for (int i = 0; i < handler.getAmount(); i++ ){
            System.out.println("Question: " + handler.getQuestions().get(i));
            System.out.println("Difficulty: " + handler.getDifficultyArr().get(i));
            System.out.println("Choices: ");
            System.out.println(handler.getAnswers().get(i));
            for (int j = 0; j < handler.getIncorrectAnswers().get(i).length(); j++) {
                try {
                    System.out.println(handler.getIncorrectAnswers().get(i).getString(j));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("");
        }
    }

}