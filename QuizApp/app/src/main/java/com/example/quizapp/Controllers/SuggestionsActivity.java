package com.example.quizapp.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizapp.R;

public class SuggestionsActivity extends AppCompatActivity {
    TextView categoryName;
    Button backToHomePageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        categoryName = findViewById(R.id.categoryNameText);
        categoryName.setPaintFlags(categoryName.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        categoryName.setText(getCategory());

        backToHomePageButton = findViewById(R.id.backToHomePageButton);

        backToHomePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homePage = new Intent(SuggestionsActivity.this, HomePageActivity.class);
                homePage.putExtra("USER_ID", getCurrentUserId());
                startActivity(homePage);
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
     * Gets the category of the quiz.
     *
     * @return The category.
     */
    public String getCategory() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String category = "";
        if (bundle != null) {
            category = (String) bundle.get("category");
        }

        return category;
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
            case R.id.logoutOption:
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SuggestionsActivity.super.onBackPressed();
                                Intent logout = new Intent(SuggestionsActivity.this, LoginActivity.class);
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