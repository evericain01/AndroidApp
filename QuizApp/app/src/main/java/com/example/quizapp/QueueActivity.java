package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QueueActivity extends AppCompatActivity {
    DatabaseHelper db;
    RecyclerView recyclerView;
    SwipeAdapter swipeAdapter;
    List<QuestionHandler> quizzes = new ArrayList<>();

    TextView queueNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        db = new DatabaseHelper(this);

        queueNumber = findViewById(R.id.queueNumberText);

        recyclerView = findViewById(R.id.swipeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        swipeAdapter = new SwipeAdapter(this, quizzes);
        recyclerView.setAdapter(swipeAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = getIntent();
                    Bundle bundle = intent.getExtras();
                    String currentUserID = "";
                    if (bundle != null) {
                        currentUserID = (String) bundle.get("USER_ID");
                    }
                    quizzes = db.convertQueueTableToList(currentUserID);
//
//                    int count = 0;
//                    for (int i = 0; i < quizzes.size(); i++) {
//                        count++;
//                        queueNumber.setText(String.valueOf(count));
//                    }

//                    queueNumber.setText(""+ quizzes.size());

//                    queueNumber.setText(swipeAdapter.getItemCount());

                    System.out.println(quizzes.size());

                    runOnUiThread(new Runnable() {
                        public void run() {
                            swipeAdapter.setData(quizzes);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}