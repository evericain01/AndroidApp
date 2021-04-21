package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueueActivity extends AppCompatActivity {
    private DatabaseHelper db;
    RecyclerView recyclerView;
    SwipeAdapter swipeAdapter;
    List<QuestionHandler> quizzes = new ArrayList<>();
    Toolbar toolbar;
    TextView queueNumber;
    TextView ifNoQuizzesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(this);
        queueNumber = findViewById(R.id.queueNumberText);

        String[] arr = getResources().getStringArray(R.array.amount);
        System.out.println(Arrays.toString(arr));

        recyclerView = findViewById(R.id.swipeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        swipeAdapter = new SwipeAdapter(this, quizzes);
        recyclerView.setAdapter(swipeAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    quizzes = db.convertQueueTableToList(getCurrentUserId());

                    runOnUiThread(new Runnable() {
                        public void run() {
                            swipeAdapter.setData(quizzes);
                            if (swipeAdapter.getItemCount() == 0) {
                                ifNoQuizzesText = findViewById(R.id.ifQueueIsEmptyText);
                                ViewGroup.LayoutParams layoutParams = ifNoQuizzesText.getLayoutParams();
                                layoutParams.width = 1000;
                                layoutParams.height = 100;
                                ifNoQuizzesText.setLayoutParams(layoutParams);
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.modifyProfile:
                Intent modifyProfile = new Intent(QueueActivity.this, ModifyProfileActivity.class);
                modifyProfile.putExtra("USER_ID", getCurrentUserId());
                startActivity(modifyProfile);
                return true;
            case R.id.logoutOption:
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                QueueActivity.super.onBackPressed();
                                Intent logout = new Intent(QueueActivity.this, LoginActivity.class);
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