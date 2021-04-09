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
    EditText firstNameInput;
    EditText lastNameInput;
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

                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                String currentUserID = "";
                if (bundle != null) {
                    currentUserID = (String) bundle.get("USER_ID");
                }

                boolean result = db.addProfile(currentUserID, firstName, lastName);

                if (result) {
                    Toast.makeText(ProfileCreationActivity.this, "Profile Created.", Toast.LENGTH_SHORT).show();
                    Intent categoryIntent = new Intent(ProfileCreationActivity.this, HomePageActivity.class);
                    startActivity(categoryIntent);
                } else {
                    Toast.makeText(ProfileCreationActivity.this, "Profile was not Created.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // for testing purposes:

//        viewUSERS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Cursor result = db.viewAllUsers();
//                if(result.getCount() == 0) {
//                    showMessage("error", "Nothing found");
//                    return;
//                }
//
//                StringBuffer buffer = new StringBuffer();
//
//                while(result.moveToNext()) {
//                    buffer.append("ID " + result.getString(0) + "\n");
//                    buffer.append("user " + result.getString(1) + "\n");
//                    buffer.append("pass " + result.getString(2) + "\n \n");
//
//                }
//                showMessage("Data", buffer.toString());
//            }
//        });
//

        // for testing purposes:

//        viewPROFILE.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Cursor result = db.viewAllProfiles();
//                if(result.getCount() == 0) {
//                    showMessage("error", "Nothing found");
//                    return;
//                }
//
//                StringBuffer buffer = new StringBuffer();
//
//                while(result.moveToNext()) {
//                    buffer.append("ProfileID " + result.getString(0) + "\n");
//                    buffer.append("UserID " + result.getString(1) + "\n");
//                    buffer.append("Firstname " + result.getString(2) + "\n");
//                    buffer.append("LastName " + result.getString(3) + "\n");
//                    buffer.append("EXP " + result.getString(4) + "\n \n");
//
//                }
//                showMessage("Data", buffer.toString());
//            }
//        });
//
//
//    }
//
//
        // for testing purposes:

//    private void showMessage(String title, String message) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.show();
//    }
    }
}