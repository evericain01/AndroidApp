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
     * Will generate an un-parsed JSON list of questions to be used.
     *
     * @param amount The number of questions to be given.
     * @param category The category to be given. ( can an empty string )
     * @param difficulty How hard the questions will be. ( Easy, Medium, and Hard )
     *
     * @return A generated list of questions in un-parsed JSON.
     */
    public String generateQuestions(int amount, int category, String difficulty) {
        String url = "";

        // If a category is not given
        if (category == -1 && difficulty.equals("")) {
            url = "https://opentdb.com/api.php?amount=" + amount + "&token=" + token;
            // If a category is given
        } else if (category != -1 && difficulty.equals("")) {
            url = "https://opentdb.com/api.php?amount=" + amount + "category=" + category + "&token=" + token;
            // If neither a category or difficulty is given.
        } else if (category == -1 && !difficulty.equals("")) {
            url = "https://opentdb.com/api.php?amount=" + amount + "&difficulty=" + difficulty + "&token=" + token;
            // If all the params are given.
        } else {
            url = "https://opentdb.com/api.php?amount=" + amount + "category=" + category + "&difficulty=" + difficulty + "&token=" + token;
        }

        return getJSONString(url);
    }

    /**
     * Generates and returns a token for the session.
     *
     * @return The session token.
     */
    private String generateToken() {
        String token = "";

        String url = "https://opentdb.com/api_token.php?command=request";
        return getTokenString(getJSONString(url));
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
     * Gets a JSON request from a given URL.
     *
     * @param urlString URL to request from.
     * @return Un-parsed JSON data.
     */
    private String getJSONString(String urlString) {
        URL url;
        try {
            // String to store JSON data down the line
            String json = null;

            url = new URL(urlString);
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
                return json;
            } else {
               return "Failed to make the connection";
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Failed to make a connection";
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
