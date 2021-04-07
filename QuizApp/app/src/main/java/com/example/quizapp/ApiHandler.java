package com.example.quizapp;

public class ApiHandler {

    public String createApiUrl(int amount, String category, String difficulty) {
        String url = "";

        // If a category is not given
        if (category == "") {
            url = "https://opentdb.com/api.php?amount=" + amount + "&difficulty=" + difficulty;
            // If a category is given
        } else {
            url = "https://opentdb.com/api.php?amount=" + amount + "category=" + category + "&difficulty=" + difficulty;
        }

        return url;
    }



}
