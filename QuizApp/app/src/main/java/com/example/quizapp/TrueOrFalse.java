package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrueOrFalse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrueOrFalse extends Fragment {
    Button tButton, fButton;
    int counter;
    int score;
    TextView questionBox;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrueOrFalse() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrueOrFalse.
     */
    // TODO: Rename and change types and number of parameters
    public static TrueOrFalse newInstance(String param1, String param2) {
        TrueOrFalse fragment = new TrueOrFalse();
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

//        System.out.println(difficulty + "  " + amount + "  "  + category + "  "  + type);

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
                    forLoopHelper(quiz);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

//        tButton = getActivity().findViewById(R.id.trueButton);
//        tButton.setOnClickListener();

        questionBox = getActivity().findViewById(R.id.questionBox);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_true_or_false, container, false);
    }

    public void forLoopHelper(QuestionHandler handler) {
        System.out.println(handler.getQuestions().get(counter));
        questionBox.setText(handler.getQuestions().get(counter));

//        for (int i = 0; i < handler.getAmount(); i++ ){
//            System.out.println("Question: " + handler.getQuestions().get(i));
//            System.out.println("Difficulty: " + handler.getDifficultyArr().get(i));
//            System.out.println("Choices: ");
//            System.out.println(handler.getAnswers().get(i));
//            System.out.println(handler.getAnswers().get(i));
//            for (int j = 0; j < handler.getIncorrectAnswers().get(i).length(); j++) {
//                try {
//                    System.out.println(handler.getIncorrectAnswers().get(i).getString(j));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println("");
//        }
    }

    public void changeQuestion() {

    }

}