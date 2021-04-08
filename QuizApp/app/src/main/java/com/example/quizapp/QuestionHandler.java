package com.example.quizapp;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class QuestionHandler extends ApiHandler {

    private JSONArray originalJsonArr = null;
    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private ArrayList<String> incorrectAnswers;

    /**
     *
     * @param amount number of questions with a max of 50
     */
    public QuestionHandler(int amount) {
        if (amount >= 50) {
            amount = 50;
        }

        originalJsonArr = generateQuestionsArray(amount, -1, "", "");

        try {
            questions = getQuestions(originalJsonArr);
            answers = getCorrectAnswers(originalJsonArr);
            incorrectAnswers = getIncorrectAnswers(originalJsonArr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param amount number of questions with a max of 50
     * @param category category numbers from 9-27
     */
    public QuestionHandler(int amount, int category) {
        if (amount >= 50) {
            amount = 50;
        }

        originalJsonArr = generateQuestionsArray(amount, category, "", "");

        try {
            questions = getQuestions(originalJsonArr);
            answers = getCorrectAnswers(originalJsonArr);
            incorrectAnswers = getIncorrectAnswers(originalJsonArr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param amount number of questions with a max of 50
     * @param category category numbers from 9-27
     * @param difficulty easy, medium, hard
     */
    public QuestionHandler(int amount, int category, String difficulty) {
        if (amount >= 50) {
            amount = 50;
        }

        originalJsonArr = generateQuestionsArray(amount, category, difficulty, "");

        try {
            questions = getQuestions(originalJsonArr);
            answers = getCorrectAnswers(originalJsonArr);
            incorrectAnswers = getIncorrectAnswers(originalJsonArr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param amount number of questions with a max of 50
     * @param category category numbers from 9-27
     * @param difficulty easy, medium, hard
     * @param type boolean or multiple
     */
    public QuestionHandler(int amount, int category, String difficulty, String type) {
        if (amount >= 50) {
            amount = 50;
        }

        originalJsonArr = generateQuestionsArray(amount, category, difficulty, type);

        try {
            questions = getQuestions(originalJsonArr);
            answers = getCorrectAnswers(originalJsonArr);
            incorrectAnswers = getIncorrectAnswers(originalJsonArr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a array of questions from a given JSONArray.
     *
     * @param jsonArray JSONArray with the questions embedded.
     * @return ArrayList<String> of all the questions.
     * @throws JSONException If JSON fails to parse
     */
    protected ArrayList<String> getQuestions(JSONArray jsonArray) throws JSONException {
        ArrayList<String> questionArr = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            questionArr.add(jsonArray.getJSONObject(i).getString("question"));
        }

        return questionArr;
    }

    /**
     * Gets a array of incorrect answers from a given JSONArray.
     *
     * @param jsonArray JSONArray with the questions embedded.
     * @return ArrayList<String> of all the questions.
     * @throws JSONException If JSON fails to parse
     */
    protected ArrayList<String> getIncorrectAnswers(JSONArray jsonArray) throws JSONException {
        ArrayList<String> incorrectArr = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            incorrectArr.add(jsonArray.getJSONObject(i).getString("incorrect_answers"));
        }

        return incorrectArr;
    }

    /**
     * Gets a array of correct answers from a given JSONArray.
     *
     * @param jsonArray JSONArray with the questions embedded.
     * @return ArrayList<String> of all the questions.
     * @throws JSONException If JSON fails to parse
     */
    protected ArrayList<String> getCorrectAnswers(JSONArray jsonArray) throws JSONException {
        ArrayList<String> correctArr = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            correctArr.add(jsonArray.getJSONObject(i).getString("correct_answer"));
        }

        return correctArr;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public ArrayList<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }
}
