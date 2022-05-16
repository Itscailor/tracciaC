package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;

public class MyHandler implements HttpHandler {

    public void handle(HttpExchange t) throws IOException {
        InputStream is = t.getRequestBody();

        URI uri = t.getRequestURI();
        String query = uri.getQuery();
        if (query != null) {
            int total = adder(query, t);
            printResponse(query + total, 200, t);
        } else {
            printResponse("N/A", 400, t);
        }
    }

    public int adder(String query, HttpExchange t) throws IOException {
        String[] param;
        param = query.split("&");
        ArrayList<String> elementi = new ArrayList<>();
        int total = 0;
        for (int i = 0; i < param.length; i++) {
            String[] value = param[i].split("=");
            if (value[0] == "num" + String.valueOf(i + 1)) {
                elementi.add(value[1]);
            } else {
                printResponse("errore", 400, t);
                System.out.println("num" + String.valueOf(i + 1));
                System.out.println(value);
                elementi = null;
                break;
            }
        }
        if (elementi != null)
            for (int i = 0; i < elementi.size(); i++) {
                total = total + Integer.parseInt(elementi.get(i));
            }
        return total;
    }

    public void printResponse(String text, int errorCode, HttpExchange t) throws IOException {
        String response = printHTML(text);
        t.sendResponseHeaders(errorCode, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public String printHTML(String text) {
        String response = "<!doctype html>\n" +
                "<html lang=en>\n" +
                "<head>\n" +
                "<meta charset=utf-8>\n" +
                "<title>MyJava Sample</title>\n" +
                "</head>\n" +
                "<body>\n" +
                text +
                "</body>\n" +
                "</html>\n";
        return response;
    }

    private String read(InputStream is) {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(is));

        System.out.println("\n");
        String received = null;
        while (true) {
            String s = null;
            try {
                if ((s = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(s);
            received += s;
        }
        return received;
    }
}