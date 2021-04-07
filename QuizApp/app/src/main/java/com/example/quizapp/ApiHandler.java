package com.example.quizapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class ApiHandler {

    /**
     *
     * @param amount The number of questions to be given.
     * @param category The category to be given. (can an empty string)
     * @param difficulty How hard the questions will be. ( Easy, Medium and Hard)
     * @param token Token for the session.
     * @return The generated URL for the question set.
     */
    public String createApiUrl(int amount, String category, String difficulty, String token) {
        String url = "";

        // If a category is not given
        if (category == "") {
            url = "https://opentdb.com/api.php?amount=" + amount + "&difficulty=" + difficulty + "&token=" + token;
            // If a category is given
        } else {
            url = "https://opentdb.com/api.php?amount=" + amount + "category=" + category + "&difficulty=" + difficulty + "&token=" + token;
        }

        return url;
    }

    /**
     * Generates and returns a token for the session.
     *
     * @return The session token.
     */
    public static String generateToken() {
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
    private static String getTokenString(String jsonData) {
        return jsonData.substring(jsonData.lastIndexOf(',') + 10, jsonData.length() - 2);
    }

    /**
     * Converts a inputStream into a string.
     *
     * @param inputStream InputStream to be converted to string.
     * @return String.
     */
    private static String streamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
    }
}
