package com.example.quizapp.Controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizapp.Models.QuestionHandler;
import com.example.quizapp.R;

import org.json.JSONException;

import java.util.Random;

public class TrueOrFalseQuizFragment extends Fragment {
    Button tButton, fButton, nextButton;
    int counter = 1;
    int score;
    int questionAmount;
    TextView categoryTitle, difficultyTitle, questionBox, counterText, quitQuiz;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public TrueOrFalseQuizFragment() {
    }

    public static TrueOrFalseQuizFragment newInstance(String param1, String param2) {
        TrueOrFalseQuizFragment fragment = new TrueOrFalseQuizFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();

        String difficulty = (String) bundle.get("difficulty");
        int amount = (int) bundle.get("amount");
        int category = (int) bundle.get("category");
        String type = (String) bundle.get("type");

        categoryTitle = getActivity().findViewById(R.id.categoryTitle);
        difficultyTitle = getActivity().findViewById(R.id.difficultyTitle);
        questionBox = getActivity().findViewById(R.id.questionBox);
        counterText = getActivity().findViewById(R.id.counterText);

        Random rand = new Random();
        int selection = rand.nextInt(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    QuestionHandler quiz = new QuestionHandler(amount, category, difficulty, type);

                    quiz.setAmount(amount);
                    quiz.setType(type);
                    quiz.setDifficulty(difficulty);
                    quiz.setCategory(category);
                    quiz.generateQuestions();

                    while (counter < quiz.getAmount() - 1) {
                        forLoopHelper(quiz, selection);
                    }

                    Intent intent = new Intent();
                    intent.putExtra("score", score);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        super.onActivityCreated(savedInstanceState);

        categoryTitle.setText(getCategoryString(category));
        difficultyTitle.setText(getDifficultyString(difficulty));
        counterText.setText("1/"+ String.valueOf(amount));
        questionAmount = amount;

        quitQuiz = getActivity().findViewById(R.id.quitQuizTextView);

        quitQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Are you sure you want to quit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent quitQuiz = new Intent(getActivity(), HomePageActivity.class);
                                quitQuiz.putExtra("USER_ID", getCurrentUserId());
                                startActivity(quitQuiz);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_true_or_false, container, false);
    }

    public void forLoopHelper(QuestionHandler handler, int selection) {
        questionBox.setText(handler.getQuestions().get(counter));
        tButton = getActivity().findViewById(R.id.trueButton);
        fButton = getActivity().findViewById(R.id.falseButton);

//        nextButton = getActivity().findViewById(R.id.nextQuestionButton);

        switch (selection) {
            case 0:
                tButton.setText(handler.getAnswers().get(counter));
                try {
                    fButton.setText(handler.getIncorrectAnswers().get(counter).getString(0));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                fButton.setText(handler.getAnswers().get(counter));
                try {
                    fButton.setText(handler.getIncorrectAnswers().get(counter).getString(0));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
        }

        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter < handler.getAmount()) {
                    if (tButton.getText() == handler.getAnswers().get(counter)) {
                        score++;
                    }
                    counter++;
                }
                counterText.setText(String.valueOf(counter) + "/" + String.valueOf(questionAmount));
                System.out.println("Counter: " + counter);
                System.out.println("Score: " + score);
            }
        });
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter < handler.getAmount()) {
                    if (fButton.getText() == handler.getAnswers().get(counter)) {
                        score++;
                    }
                    counter++;
                }
                counterText.setText(String.valueOf(counter) + "/" + String.valueOf(questionAmount));
                System.out.println("Counter: " + counter);
                System.out.println("Score: " + score);
            }
        });

    }

    /**
     * Gets the current user ID.
     *
     * @return The user ID as a String.
     */
    public String getCurrentUserId() {
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        return (String) bundle.get("USER_ID");
    }

    private String getCategoryString(int categoryNumber) {
        String category = "";
        switch (categoryNumber) {
            case 9:
                category = "General Knowledge";
                break;
            case 10:
                category = "Books";
                break;
            case 11:
                category = "Films";
                break;
            case 12:
                category = "Music";
                break;
            case 13:
                category = "Musical and Theatres";
                break;
            case 14:
                category = "Television";
                break;
            case 15:
                category = "Video Games";
                break;
            case 16:
                category = "Board Games";
                break;
            case 17:
                category = "Science and Nature";
                break;
            case 18:
                category = "Computers";
                break;
            case 19:
                category = "Math";
                break;
            case 20:
                category = "Mythology";
                break;
            case 21:
                category = "Sports";
                break;
            case 22:
                category = "Geography";
                break;
            case 23:
                category = "Politics";
                break;
            case 24:
                category = "Art";
                break;
            case 25:
                category = "Celebrities";
                break;
            case 27:
                category = "Animals";
                break;
            case 28:
                category = "Vehicles";
                break;
            case 29:
                category = "Comics";
                break;
            case 30:
                category = "Gadgets";
                break;
            case 31:
                category = "Japanese Anime and Manga";
                break;
            case 32:
                category = "Cartoons and Animations";
                break;
            default:
        }

        return category;
    }

    private String getDifficultyString(String difficultyText) {
        String difficulty = "";
        switch (difficultyText) {
            case "easy":
                difficulty = ("(EASY)");
                break;
            case "medium":
                difficulty = ("(MEDIUM)");
                break;
            case "hard":
                difficulty = ("(HARD)");
                break;
            default:
        }

        return difficulty;
    }
}