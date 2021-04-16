package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QueueActivity extends AppCompatActivity {
    DatabaseHelper db;
    RecyclerView recyclerView;
    SwipeAdapter swipeAdapter;
    List<QuestionHandler> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        db = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.swipeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        swipeAdapter = new SwipeAdapter(this, questions);
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
                    List<QuestionHandler> currentUserQueueList = db.convertQueueTableToList(currentUserID);
                    System.out.println("YOOOOOOOOOOOOOOOOOOOOOOOOOO IM HHERE");
                    for(int i = 0; i < currentUserQueueList.size(); i++) {
                        System.out.println(currentUserQueueList.get(i).getAmount());
                        System.out.println(currentUserQueueList.get(i).getCategory());
                        System.out.println(currentUserQueueList.get(i).getDifficulty());
                        System.out.println(currentUserQueueList.get(i).getType());
                    }
                    swipeAdapter.setData(currentUserQueueList);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


//        System.out.println(currentUserQueueList);
        System.out.println("---------------------------------------------------------------------------------------------------------------------");

    }
}