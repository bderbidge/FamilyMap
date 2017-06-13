package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Created by brandonderbidge on 5/24/17.
 */

public class DefaultHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String str = httpExchange.getRequestURI().toString();
        String path = "";

        if(str.equals("/")){

             path = "/Users/brandonderbidge/Documents" +
                    "/CS240/FamilyMap/FamilyMapServers/" +
                    "MapServerlib/familymapserver/data/web/index.html";



        }else if(str.equals("/css/main.css")){

             path = "/Users/brandonderbidge/Documents" +
                    "/CS240/FamilyMap/FamilyMapServers/" +
                    "MapServerlib/familymapserver/data/web/" +
                    "/css/main.css";



        }else if(str.equals("/favicon/ico")){

             path = "/Users/brandonderbidge/Documents" +
                    "/CS240/FamilyMap/FamilyMapServers/" +
                    "MapServerlib/familymapserver/data/web/" +
                    "favicon.ico";


        }

        createPage(httpExchange, path);

    }

    public void createPage(HttpExchange httpExchange, String path) throws IOException {


        OutputStream os = httpExchange.getResponseBody();

        FileInputStream fis = new FileInputStream(new File(path));

        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        final byte[] buffer = new byte[0x1000];
        int counter = 0;
        while ((counter = fis.read(buffer)) >= 0){
            os.write(buffer, 0, counter);
        }
        os.flush();;
        os.close();;
        fis.close();
        httpExchange.close();
    }
}
