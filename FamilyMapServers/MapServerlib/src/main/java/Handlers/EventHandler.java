package Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import DataAccess.DatabaseException;
import Request.GeneralIDRequest;
import Response.EventResponse;
import Response.EventsResponse;
import Response.GeneralPurposeMessage;
import Response.PeopleResponse;
import Response.PersonResponse;
import Services.EventService;
import Services.PersonService;

/**
 * Created by brandonderbidge on 5/31/17.
 */

public class EventHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {


        boolean success = false;

        try {
            Gson gson = new Gson();
            try {


                if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                    // Get the HTTP request headers
                    Headers reqHeaders = exchange.getRequestHeaders();
                    // Check to see if an "Authorization" header is present
                    if (reqHeaders.containsKey("Authorization")) {

                        String authToken = reqHeaders.getFirst("Authorization");

                        // Start sending the HTTP response to the client, starting with
                        // the status code and any defined headers.
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);


                        // From now on, the right way of moving from bytes to utf-8 characters:

                        String path = exchange.getRequestURI().getPath();

                        String eventID = path.substring(path.lastIndexOf('/') + 1);


                        GeneralPurposeMessage purposeMessage = new GeneralPurposeMessage();


                        GeneralIDRequest request = new GeneralIDRequest();
                        EventService eventService = new EventService();

                        request.setAuthToken(authToken);
                        request.setRequestID(eventID);

                        EventResponse response;
                        EventsResponse eventsResponse;

                        if (path.equals("/event/")) {


                            eventsResponse = eventService.GetEvents(request);

                            if (eventsResponse.getevents().size() < 1) {

                                purposeMessage.setMessage("Failed to get all Events");

                                String json = gson.toJson(purposeMessage);
                                OutputStream respBody = exchange.getResponseBody();
                                writeString(json, respBody);

                                respBody.close();


                                success = false;

                            } else {
                                // Close the output stream.  This is how Java knows we are done
                                // sending data and the response is complete/

                                String json = gson.toJson(eventsResponse);
                                OutputStream respBody = exchange.getResponseBody();
                                writeString(json, respBody);

                                respBody.close();


                                success = true;
                            }
                        } else {

                            response = eventService.Get(request);


                            if (response != null) {
                                String json = gson.toJson(response);
                                OutputStream respBody = exchange.getResponseBody();
                                writeString(json, respBody);

                                respBody.close();


                                success = true;

                            } else {

                                purposeMessage.setMessage("The Event ID was not associated with the user");

                                String json = gson.toJson(purposeMessage);
                                OutputStream respBody = exchange.getResponseBody();
                                writeString(json, respBody);

                                respBody.close();


                                success = false;

                            }


                        }

                    }
                }
                if (!success) {
                    // The HTTP request was invalid somehow, so we return a "bad request"
                    // status code to the client.
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    // Since the client request was invalid, they will not receive the
                    // list of games, so we close the response body output stream,
                    // indicating that the response is complete.
                    exchange.getResponseBody().close();
                }
            }catch (DatabaseException e){

                GeneralPurposeMessage purposeMessage = new GeneralPurposeMessage();
                purposeMessage.setMessage(e.getMessage());

                String json = gson.toJson(purposeMessage);
                OutputStream respBody = exchange.getResponseBody();
                writeString(json, respBody);

                respBody.close();


            }
        }
        catch (IOException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            // Since the server is unable to complete the request, the client will
            // not receive the list of games, so we close the response body output stream,
            // indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }
    }

    /*
        The writeString method shows how to write a String to an OutputStream.
    */
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}