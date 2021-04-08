package com.example.quizapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public JSONArray generateQuestionsArray(int amount, int category, String difficulty) {
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

        return parseJSONArray(getJSONString(url), "results");
    }

    private String parseJSON(String jsonString) {
        try {
            JSONObject obj = new JSONObject(jsonString);
            return obj.getString("token");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Failed to parse json";
    }


    /**
     * Generates and returns a token for the session.
     *
     * @return The session token.
     */
    private String generateToken() {
        String token = "";

        String url = "https://opentdb.com/api_token.php?command=request";
        return parseJSONString(getJSONString(url), "token");
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

        return "Something failed in the try catch";
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

    /**
     * Takes in a un-parsed JSON String, with a code to parse the string into the code desired.
     *
     * @param jsonString Un-parsed JSON String.
     * @param code String to be obtained from the JSON string.
     *
     * @return Parsed JSON String.
     */
    private String parseJSONString(String jsonString, String code) {
        try {
            JSONObject obj = new JSONObject(jsonString);
            return obj.getString(code);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Failed to parse json";
    }

    /**
     * Takes in a un-parsed JSON Array, with a code to parse the array into the code desired.
     *
     * @param jsonArray Un-parsed JSON Array.
     * @param code Array to be obtained from the JSON Array.
     *
     * @return Parsed JSON Array.
     */
    private JSONArray parseJSONArray(String jsonArray, String code) {
        try {
            JSONObject obj = new JSONObject(jsonArray);
            // Returns a JSON array.
            return obj.getJSONArray(code);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
