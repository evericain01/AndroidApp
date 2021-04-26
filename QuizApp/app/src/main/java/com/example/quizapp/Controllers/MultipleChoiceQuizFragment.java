package com.example.quizapp.Controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.quizapp.Models.Experience;
import com.example.quizapp.Models.QuestionHandler;
import com.example.quizapp.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import java.util.Random;

public class MultipleChoiceQuizFragment extends Fragment {
    int counter = 1;
    int totalExperienceGained = 0;
    int score, questionAmount;
    boolean flag = true;

    RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    RadioGroup radioGroup;
    Button nextButton;
    TextView categoryTitle, difficultyTitle, questionBox, counterText, quitQuiz;
    MediaPlayer mediaPlayer;
    SwitchCompat switchCompat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_multiple_choice, container, false);
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

        switchCompat = getActivity().findViewById(R.id.musicSwitch);
        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchCompat.isChecked()) {
                    startMusic();
                }else {
                    pauseMusic();
                }
            }
        });

        Random rand = new Random();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    QuestionHandler quiz = new QuestionHandler(amount, category, difficulty, type);

                    quiz.setAmount(amount + 1);
                    quiz.setType(type);
                    quiz.setDifficulty(difficulty);
                    quiz.setCategory(category);
                    quiz.generateQuestions();

                    while (counter < quiz.getAmount()) {
                        if (flag) {
                            forLoopHelper(quiz, rand.nextInt(3));
                        }
                    }

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

    public void forLoopHelper(QuestionHandler handler, int selection) {

        questionBox.setText(handler.getQuestions().get(counter));
        radioGroup = getActivity().findViewById(R.id.radioGroup);
        radioButton1 = getActivity().findViewById(R.id.button1);
        radioButton2 = getActivity().findViewById(R.id.button2);
        radioButton3 = getActivity().findViewById(R.id.button3);
        radioButton4 = getActivity().findViewById(R.id.button4);
        nextButton = getActivity().findViewById(R.id.nextQuestionButton);
        flag = false;

        switch (selection) {
            case 0:
                radioButton1.setText(handler.getAnswers().get(counter));
                try {
                    radioButton2.setText(handler.getIncorrectAnswers().get(counter).getString(0));
                    radioButton3.setText(handler.getIncorrectAnswers().get(counter).getString(1));
                    radioButton4.setText(handler.getIncorrectAnswers().get(counter).getString(2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                radioButton2.setText(handler.getAnswers().get(counter));
                try {
                    radioButton1.setText(handler.getIncorrectAnswers().get(counter).getString(0));
                    radioButton3.setText(handler.getIncorrectAnswers().get(counter).getString(1));
                    radioButton4.setText(handler.getIncorrectAnswers().get(counter).getString(2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                radioButton3.setText(handler.getAnswers().get(counter));
                try {
                    radioButton2.setText(handler.getIncorrectAnswers().get(counter).getString(0));
                    radioButton1.setText(handler.getIncorrectAnswers().get(counter).getString(1));
                    radioButton4.setText(handler.getIncorrectAnswers().get(counter).getString(2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                radioButton4.setText(handler.getAnswers().get(counter));
                try {
                    radioButton2.setText(handler.getIncorrectAnswers().get(counter).getString(0));
                    radioButton3.setText(handler.getIncorrectAnswers().get(counter).getString(1));
                    radioButton1.setText(handler.getIncorrectAnswers().get(counter).getString(2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
        }

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter < handler.getAmount()) {
                            if (radioButton1.getText() == handler.getAnswers().get(counter)) {
                                totalExperienceGained += Experience.calculateExperience(handler.getDifficulty());
                                correctAnswerSnackBar();
                                score++;
                            } else {
                                incorrectAnswerSnackBar();
                            }
                            gotoResultActivityIfLast(handler);
                            radioGroup.clearCheck();
                            counter++;
                            flag = true;
                        }
                        counterText.setText(counter + "/" + questionAmount);
                        System.out.println("Counter: " + counter);
                        System.out.println("Score: " + score);
                    }
                });
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter < handler.getAmount()) {
                            if (radioButton2.getText() == handler.getAnswers().get(counter)) {
                                totalExperienceGained += Experience.calculateExperience(handler.getDifficulty());
                                correctAnswerSnackBar();
                                score++;
                            } else {
                                incorrectAnswerSnackBar();
                            }
                            gotoResultActivityIfLast(handler);
                            radioGroup.clearCheck();
                            counter++;
                            flag = true;
                        }
                        counterText.setText(counter + "/" + questionAmount);
                        System.out.println("Counter: " + counter);
                        System.out.println("Score: " + score);
                    }
                });
            }
        });

        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter < handler.getAmount()) {
                            if (radioButton3.getText() == handler.getAnswers().get(counter)) {
                                totalExperienceGained += Experience.calculateExperience(handler.getDifficulty());
                                correctAnswerSnackBar();
                                score++;
                            }else {
                                incorrectAnswerSnackBar();
                            }
                            gotoResultActivityIfLast(handler);
                            radioGroup.clearCheck();
                            counter++;
                            flag = true;
                        }
                        counterText.setText(counter + "/" + questionAmount);
                        System.out.println("Counter: " + counter);
                        System.out.println("Score: " + score);
                    }
                });
            }
        });

        radioButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter < handler.getAmount()) {
                            if (radioButton4.getText() == handler.getAnswers().get(counter)) {
                                totalExperienceGained += Experience.calculateExperience(handler.getDifficulty());
                                correctAnswerSnackBar();
                                score++;
                            } else {
                                incorrectAnswerSnackBar();
                            }
                            gotoResultActivityIfLast(handler);
                            radioGroup.clearCheck();
                            counter++;
                            flag = true;
                        }
                        counterText.setText(counter + "/" + questionAmount);
                        System.out.println("Counter: " + counter);
                        System.out.println("Score: " + score);
                    }
                });
            }
        });

    }

    /**
     * Sends the user to the result screen after completing the quiz.
     *
     * @param handler The question.
     */
    public void gotoResultActivityIfLast(QuestionHandler handler) {
        if (counter + 1 == handler.getAmount()) {
            counter--;
            Intent resultActivity = new Intent(getActivity(), ResultActivity.class);
            resultActivity.putExtra("USER_ID", getCurrentUserId());
            resultActivity.putExtra("category", getCategoryString(handler.getCategory()));
            resultActivity.putExtra("score", score);
            resultActivity.putExtra("amount", handler.getAmount() - 1);
            resultActivity.putExtra("experienceGained", totalExperienceGained);
            startActivity(resultActivity);
        }

    }

    /**
     * Sets a snackBar for correct answers.
     */
    public void correctAnswerSnackBar() {
        ConstraintLayout constraintLayout = getActivity().findViewById(R.id.constraintMultipleChoice);
        final Snackbar snack = Snackbar.make(constraintLayout, "CORRECT!", Snackbar.LENGTH_SHORT);
        View snackView = snack.getView();
        snack.setTextColor(Color.BLACK);
        snack.setDuration(1500);
        snackView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
        snack.show();

    }

    /**
     * Sets a snackBar for incorrect answers.
     */
    public void incorrectAnswerSnackBar() {
        ConstraintLayout constraintLayout = getActivity().findViewById(R.id.constraintMultipleChoice);
        final Snackbar snack = Snackbar.make(constraintLayout, "INCORRECT.", Snackbar.LENGTH_SHORT);
        View snackView = snack.getView();
        snack.setDuration(1500);
        snackView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
        snack.show();
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

    /**
     * Starts the music.
     */
    public void startMusic() {
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.treasure_grenada_no_copyright);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
    }

    /**
     * Pauses the music.
     */
    public void pauseMusic() {
        mediaPlayer.pause();
    }

    /**
     * Converting category number to its string.
     *
     * @param categoryNumber Category number.
     * @return Category string.
     */
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

    /**
     * Capitalizes difficulty name.
     *
     * @param difficultyText Difficulty text.
     * @return Capitalizes difficulty name.
     */
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