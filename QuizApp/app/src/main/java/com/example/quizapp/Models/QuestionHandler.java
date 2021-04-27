package com.example.quizapp.Models;

import android.text.Html;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class QuestionHandler extends ApiHandler {

    private JSONArray originalJsonArr = null;
    private int amount;
    private int category;
    private String difficulty;
    private String type;
    private int id;

    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private ArrayList<String> difficultyArr;
    private ArrayList<JSONArray> incorrectAnswers;

    /**
     * Generates questions in the private Data-Members that can be obtained from the getter methods
     * for questions, answers, incorrectAnswers and difficultyArr.
     *
     * @param amount     number of questions with a max of 50
     * @param category   category numbers from 9-27
     * @param difficulty easy, medium, hard
     * @param type       boolean or multiple
     */
    public QuestionHandler(int amount, int category, String difficulty, String type) {
        if (amount >= 50) {
            this.amount = 50;
        } else {
            this.amount = amount;
        }

        if (category < 9 || category > 32) {
            this.category = -1;
        } else {
            this.category = category;
        }

        switch(difficulty) {
            case "easy":
                this.difficulty = "easy";
                break;
            case "medium":
                this.difficulty = "medium";
                break;
            case "hard":
                this.difficulty = "hard";
                break;
            default:
                this.difficulty = "";
                break;
        }

        switch (type) {
            case "boolean":
                this.type = "boolean";
                break;
            case "multiple":
                this.type = "multiple";
                break;
            default:
                this.type = "";
                break;
        }

        generateQuestions();
    }


    /**
     *  Generates questions in the private Data-Members (including an id of the quiz) that can be
     *  obtained from the getter methods for questions, answers, incorrectAnswers and difficultyArr.
     *
     * @param id a unique id
     * @param amount number of questions with a max of 50
     * @param category category numbers from 9-27
     * @param difficulty easy, medium, hard
     * @param type boolean or multiple
     */
    public QuestionHandler(int id, int amount, int category, String difficulty, String type) {
        this.id = id;

        if (amount >= 50) {
            this.amount = 50;
        } else {
            this.amount = amount;
        }

        if (category < 9 || category > 32) {
            this.category = -1;
        } else {
            this.category = category;
        }

        switch(difficulty) {
            case "easy":
                this.difficulty = "easy";
                break;
            case "medium":
                this.difficulty = "medium";
                break;
            case "hard":
                this.difficulty = "hard";
                break;
            default:
                this.difficulty = "";
                break;
        }

        switch (type) {
            case "boolean":
                this.type = "boolean";
                break;
            case "multiple":
                this.type = "multiple";
                break;
            default:
                this.type = "";
                break;
        }

        generateQuestions();
    }


    /**
     * Generates an array of questions and saves them to the private Data-Members saving the Questions,
     * answers, incorrect answers and the difficulty of those questions.
     *
     * @return True if it successfully generated questions, false if it didn't generate any questions.
     */
    public boolean generateQuestions() {
        boolean flag = true;
        int responseCounter = 0;
        originalJsonArr = generateQuestionsArray(amount, category, difficulty, type);

        while (getResponseCode().equals("4")) {
            originalJsonArr = generateQuestionsArray(amount, category, difficulty, type);
            flag = false;
            responseCounter++;

            if (responseCounter > 2) {
                if (amount == 50) {
                    this.amount = amount - 10;
                } else if (amount == 40) {
                    this.amount = amount - 10;
                } else if (amount == 30) {
                    this.amount = amount - 10;
                } else if (amount == 20) {
                    this.amount = amount - 10;
                } else {
                    this.amount = amount - 1;
                }

                originalJsonArr = generateQuestionsArray(amount, category, difficulty, type);

            }
        }

        try {
            questions = htmlDecodeHelper(getQuestionsArray(originalJsonArr));
            answers = htmlDecodeHelper(getCorrectAnswers(originalJsonArr));
            incorrectAnswers = htmlDecodeHelperJSON(getIncorrectAnswers(originalJsonArr));
            difficultyArr = getDifficultyArray(originalJsonArr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return flag;
    }

    private ArrayList<JSONArray> htmlDecodeHelperJSON(ArrayList<JSONArray> jsonArrayArrayList) throws JSONException {
        ArrayList<JSONArray> outArrayList = new ArrayList<>();
        JSONArray array;

        for (int i = 0; i < jsonArrayArrayList.size(); i++) {
            array = new JSONArray();
            for (int j = 0; j < jsonArrayArrayList.get(i).length(); j++) {
                array.put(Html.fromHtml(jsonArrayArrayList.get(i).getString(j), Html.FROM_HTML_MODE_LEGACY).toString());
            }
            outArrayList.add(array);
        }

        return outArrayList;
    }

    private ArrayList<String> htmlDecodeHelper(ArrayList<String> stringArrayList) {
        ArrayList<String> outArrayList = new ArrayList<>();

        for (int i = 0; i < stringArrayList.size(); i++) {
            outArrayList.add(Html.fromHtml(stringArrayList.get(i), Html.FROM_HTML_MODE_LEGACY).toString());
        }

        return outArrayList;
    }

    /**
     * Gets a array of questions from a given JSONArray.
     *
     * @param jsonArray JSONArray with the questions embedded.
     * @return ArrayList<String> of all the questions.
     * @throws JSONException If JSON fails to parse
     */
    private ArrayList<String> getQuestionsArray(JSONArray jsonArray) throws JSONException {
        ArrayList<String> questionArr = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            questionArr.add(jsonArray.getJSONObject(i).getString("question"));
        }

        return questionArr;
    }

    private ArrayList<String> getDifficultyArray(JSONArray jsonArray) throws JSONException {
        ArrayList<String> difficultyArr = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            difficultyArr.add(jsonArray.getJSONObject(i).getString("difficulty"));
        }

        return difficultyArr;
    }

    /**
     * Gets a array of incorrect answers from a given JSONArray.
     *
     * @param jsonArray JSONArray with the questions embedded.
     * @return ArrayList<String> of all the questions.
     * @throws JSONException If JSON fails to parse
     */
    private ArrayList<JSONArray> getIncorrectAnswers(JSONArray jsonArray) throws JSONException {
        ArrayList<JSONArray> incorrectArr = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            incorrectArr.add(jsonArray.getJSONObject(i).getJSONArray("incorrect_answers"));
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
    private ArrayList<String> getCorrectAnswers(JSONArray jsonArray) throws JSONException {
        ArrayList<String> correctArr = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            correctArr.add(jsonArray.getJSONObject(i).getString("correct_answer"));
        }

        return correctArr;
    }

    public ArrayList<String> getQuestions() { return questions; }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public ArrayList<JSONArray> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public ArrayList<String> getDifficultyArr() {
        return difficultyArr;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (amount >= 50) {
            amount = 50;
        } else {
            this.amount = amount;
        }
    }

    public int getCategory() { return category; }

    public void setCategory(int category) {
        if (category < 9 || category > 32) {
            this.category = -1;
        } else {
            this.category = category;
        }
    }

    public String getDifficulty() { return difficulty; }

    public void setDifficulty(String difficulty) {
        if (difficulty.equals("easy") && !difficulty.equals("medium") && !difficulty.equals("hard")) {
            this.difficulty = "easy";
        } else if (!difficulty.equals("easy") && difficulty.equals("medium") && !difficulty.equals("hard")){
            this.difficulty = "medium";
        } else if (!difficulty.equals("easy") && !difficulty.equals("medium") && difficulty.equals("hard")) {
            this.difficulty = "hard";
        } else {
            this.difficulty = "";
        }
    }

    public String getType() { return type; }

    public void setType(String type) {
        if (type.equals("boolean") && !type.equals("multiple")) {
            this.type = "boolean";
        } else if (!type.equals("boolean") && type.equals("multiple")){
            this.type = "multiple";
        } else {
            this.type = "";
        }
    }
}