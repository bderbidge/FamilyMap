package com.example.brandonderbidge.familymapserver.httpclient;

import com.example.brandonderbidge.familymapserver.Model;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by brandonderbidge on 6/6/17.
 */

public class Server {



    public String register(URL url) {

        String respData = "";

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");


            Gson gson = new Gson();
            String json = gson.toJson(Model.getRegister());

            connection.connect();

            OutputStream reqBody = connection.getOutputStream();
            writeString(json, reqBody);


            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
                InputStream respBody = connection.getInputStream();
                // Extract JSON data from the HTTP response body
                respData = readString(respBody);
                // Display the JSON data returned from the server
                System.out.println(respData);
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + connection.getResponseMessage());

                respData = "Unable to Register the User";
            }


            // Close the request body output stream, indicating that the
            // request is complete
            reqBody.close();


            if(respData.equals("{\"message\":\"Failed to Register User\"}") ||
                    respData.equals("{\"message\":\"Failed to Register User because the" +
                            " input was invalid.\"}"))
                respData = "Unable to Register the User";

            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // The HTTP response status code indicates success,
                // so print a success message
                System.out.println("Route successfully claimed.");
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + connection.getResponseMessage());

                respData = "Unable to Register the User";
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();

            respData = "Unable to Register the User";
        }

        return respData;
    }

    public String login(URL url) {

        String respData = "";

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");




            Gson gson = new Gson();
            String json = gson.toJson(Model.getLogin());

            connection.connect();

            OutputStream reqBody = connection.getOutputStream();
            writeString(json, reqBody);


            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
                InputStream respBody = connection.getInputStream();
                // Extract JSON data from the HTTP response body
                respData = readString(respBody);
                // Display the JSON data returned from the server
                System.out.println(respData);
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + connection.getResponseMessage());

                respData = "Unable to login the User";
            }


            // Close the request body output stream, indicating that the
            // request is complete
            reqBody.close();


            if(respData.equals("{\"message\":\"Failed to Login User\"}"))
                respData = "Unable to login the User";

            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // The HTTP response status code indicates success,
                // so print a success message
                System.out.println("Route successfully claimed.");
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + connection.getResponseMessage());

                respData = "Unable to login the User";
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();

            respData = "Unable to login the User";
        }

        return respData;
    }

    public String getPerson(URL url) {

        String respData = "";

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setDoOutput(false);

            connection.addRequestProperty("Authorization", Model.getAuthToken());

            connection.connect();


            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
                InputStream respBody = connection.getInputStream();
                // Extract JSON data from the HTTP response body
                respData = readString(respBody);
                // Display the JSON data returned from the server
                System.out.println(respData);
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + connection.getResponseMessage());

                respData = "Unable to get PersonActivity";
            }




            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // The HTTP response status code indicates success,
                // so print a success message
                System.out.println("Route successfully claimed.");
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + connection.getResponseMessage());

                respData = "Unable to get PersonActivity";
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();

            respData = "Unable to get PersonActivity";
        }

        if(respData.equals("{\"message\":\"AuthToken is not valid\"}"))
            respData = "Invalid AuthToken";

        return respData;
    }


    public String getPeople(URL url) {

        String respData = "";

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setDoOutput(false);

            connection.addRequestProperty("Authorization", Model.getAuthToken());

            connection.connect();


            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
                InputStream respBody = connection.getInputStream();
                // Extract JSON data from the HTTP response body
                respData = readString(respBody);
                // Display the JSON data returned from the server
                System.out.println(respData);
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + connection.getResponseMessage());

                respData = "Unable to get PersonActivity";
            }




            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // The HTTP response status code indicates success,
                // so print a success message
                System.out.println("Route successfully claimed.");
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + connection.getResponseMessage());

                respData = "Unable to get PersonActivity";
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();

            respData = "Unable to get PersonActivity";
        }

        if(respData.equals("{\"message\":\"AuthToken is not valid\"}"))
            respData = "Invalid AuthToken";

        return respData;
    }

    public String getEvents(URL url) {

        String respData = "";

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setDoOutput(false);

            connection.addRequestProperty("Authorization", Model.getAuthToken());

            connection.connect();


            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
                InputStream respBody = connection.getInputStream();
                // Extract JSON data from the HTTP response body
                respData = readString(respBody);
                // Display the JSON data returned from the server
                System.out.println(respData);
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + connection.getResponseMessage());

                respData = "Unable to get PersonActivity";
            }




            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // The HTTP response status code indicates success,
                // so print a success message
                System.out.println("Route successfully claimed.");
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + connection.getResponseMessage());

                respData = "Unable to get PersonActivity";
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();

            respData = "Unable to get PersonActivity";
        }

        if(respData.equals("{\"message\":\"AuthToken is not valid\"}"))
            respData = "Invalid AuthToken";

        return respData;
    }


    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }


}
