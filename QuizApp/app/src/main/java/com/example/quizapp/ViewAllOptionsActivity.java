package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ViewAllOptionsActivity extends AppCompatActivity {
    Button viewCategories;
    Button viewDifficulties;
    Button viewTypes;
    Button viewAmounts;

    ImageView categoryPic;
    ImageView difficultyPic;
    ImageView typePic;
    ImageView amountPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_options);

        viewCategories = findViewById(R.id.viewAllCategoriesButton);
        viewDifficulties = findViewById(R.id.viewAllDifficulties);
        viewTypes = findViewById(R.id.viewAllTypes);
        viewAmounts = findViewById(R.id.viewAllAmounts);

        categoryPic = findViewById(R.id.categoryImage);
        categoryPic.setImageResource(R.drawable.categories_ico);
        difficultyPic = findViewById(R.id.difficultyImage);
        difficultyPic.setImageResource(R.drawable.difficulty_ico);
        typePic = findViewById(R.id.typeImage);
        typePic.setImageResource(R.drawable.type_ico);
        amountPic = findViewById(R.id.amountImage);
        amountPic.setImageResource(R.drawable.number_ico);

        viewCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategories(ViewAllOptionsActivity.this);
            }
        });

        viewDifficulties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDifficulties(ViewAllOptionsActivity.this);
            }
        });

        viewTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getType(ViewAllOptionsActivity.this);
            }
        });

        viewAmounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAmounts(ViewAllOptionsActivity.this);
            }
        });

    }

    public void getCategories(Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Categories:");
        dialog.setMessage("General Knowledge\n" + "Books\n" + "Films\n" + "Music\n" + "Musical and Theatres\n"
                        + "Television\n" + "Video Games\n" + "Board Games\n" + "Science and Nature\n" + "Computers\n"
                        + "Math\n" + "Mythology\n" + "Sports\n" + "Geography\n" + "History\n" + "Politics\n" + "Art\n"
                        + "Celebrities\n" + "Animals\n" + "Vehicles\n" + "Comics\n" + "Gadgets\n" + "Japanese Anime and Manga\n"
                        + "Cartoons and Animations");
        dialog.setCancelable(true);
        dialog.show();
    }

    public void getDifficulties(Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Difficulties:");
        dialog.setMessage("Easy\n" + "Medium\n" + "Hard");
        dialog.setCancelable(true);
        dialog.show();
    }

    public void getType(Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Types:");
        dialog.setMessage("True and False\n" + "Multiple Choice");
        dialog.setCancelable(true);
        dialog.show();
    }

    public void getAmounts(Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Amount of Questions:");
        dialog.setMessage("10\n" + "20\n" + "30\n" + "40\n" + "50");
        dialog.setCancelable(true);
        dialog.show();
    }

}