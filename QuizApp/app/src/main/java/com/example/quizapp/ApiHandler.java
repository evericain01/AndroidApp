package com.example.quizapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class ApiHandler {

    private String token = null;

    public ApiHandler() {
        if (token == null) {
            token = generateToken();
        }
    }

    /**
     *
     * @param amount The number of questions to be given.
     * @param category The category to be given. ( can an empty string )
     * @param difficulty How hard the questions will be. ( Easy, Medium, and Hard )
     *
     * @return The generated the questions.
     */
    public String generateQuestions(int amount, int category, String difficulty) {
        String url = "";
        String questions = "";

        // If a category is not given
        if (category == -1 && difficulty == "") {
            url = "https://opentdb.com/api.php?amount=" + amount + "&token=" + token;
            // If a category is given
        } else if (category != -1 && difficulty == "") {
            url = "https://opentdb.com/api.php?amount=" + amount + "category=" + category + "&token=" + token;
        } else if (category == -1 && difficulty != "") {
            url = "https://opentdb.com/api.php?amount=" + amount + "&difficulty=" + difficulty + "&token=" + token;
        } else {
            url = "https://opentdb.com/api.php?amount=" + amount + "category=" + category + "&difficulty=" + difficulty + "&token=" + token;
        }

        URL url2;

        try {
            String jsonQuestions = null;
            System.out.println(url);
            url2 = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) url2.openConnection();
            // Setting properties for the request.
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("charset", "utf-8");
            // Create the connection.
            con.connect();
            // If the response is successful.
            if (con.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                // Gets the JSON data as a InputStream and converts it into a string.
                InputStream inStream = con.getInputStream();
                jsonQuestions = streamToString(inStream);
                // Closes the connection.
                con.disconnect();
                return jsonQuestions;
            } else {
                System.out.println("failed to get questions");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "false";
    }

    /**
     * Generates and returns a token for the session.
     *
     * @return The session token.
     */
    private String generateToken() {
        String token = "";

        String https_url = "https://opentdb.com/api_token.php?command=request";

        URL url;

        try {
            String json = null;

            url = new URL(https_url);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            // Setting properties for the request.
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("charset", "utf-8");
            // Create the connection.
            con.connect();
            // If the response is successful.
            if (con.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                // Gets the JSON data as a InputStream and converts it into a string.
                InputStream inStream = con.getInputStream();
                json = streamToString(inStream);
                // Closes the connection.
                con.disconnect();
                // Returns the carved out token from the JSON data.
                return getTokenString(json);
            } else {
                System.out.println("failed to get token");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "false";
    }

    /**
     * Carves out the token data from the JSON data.
     *
     * @param jsonData JSON data from the API token call.
     * @return The token for the session.
     */
    private String getTokenString(String jsonData) {
        return jsonData.substring(jsonData.lastIndexOf(',') + 10, jsonData.length() - 2);
    }

    /**
     * Converts a inputStream into a string.
     *
     * @param inputStream InputStream to be converted to string.
     * @return String.
     */
    private String streamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
    }
}
