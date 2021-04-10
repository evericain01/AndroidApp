package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ChooseCategoryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Integer categoryNum, totalQuestions;
    String challenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        Spinner category = findViewById(R.id.chooseCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        category.setOnItemSelectedListener(this);

        Spinner difficulty = findViewById(R.id.chooseDifficulty);
        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(this, R.array.difficulties, android.R.layout.simple_spinner_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficulty.setAdapter(difficultyAdapter);
        difficulty.setOnItemSelectedListener(this);

//        Spinner type = findViewById(R.id.chooseType);
//        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
//        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        type.setAdapter(typeAdapter);
//        type.setOnItemSelectedListener(this);

        Spinner total = findViewById(R.id.chooseAmount);
        ArrayAdapter<CharSequence> totalAdapter = ArrayAdapter.createFromResource(this, R.array.amount, android.R.layout.simple_spinner_item);
        totalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        total.setAdapter(totalAdapter);
        total.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categoryNum = i + 9;
        challenge = adapterView.getItemAtPosition(i).toString().toLowerCase();
        String temp = adapterView.getItemAtPosition(i).toString();
//        totalQuestions = Integer.parseInt(temp);
        Toast.makeText(ChooseCategoryActivity.this, temp, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}