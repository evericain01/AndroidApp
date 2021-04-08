package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    QuestionHandler temp = new QuestionHandler(5);

                    for (int i = 0; i < temp.getAmount(); i++ ){
                        System.out.println("Question: " + temp.getQuestions().get(i));
                        System.out.println("Choices: ");
                        System.out.println(temp.getAnswers().get(i));
                        for (int j = 0; j < temp.getIncorrectAnswers().get(i).length(); j++) {
                            try {
                                System.out.println(temp.getIncorrectAnswers().get(i).getString(j));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}