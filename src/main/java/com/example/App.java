package com.example;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.OutputStream;

public class App {
    public static void main (String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/add", exchange -> {
            int a = Integer.parseInt(exchange.getRequestURI().getQuery().split("&")[0].split("=")[1]);
            int b = Integer.parseInt(exchange.getRequestURI().getQuery().split("&")[1].split("=")[1]);
            String response = "Result: " + (a + b);
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            SYstem.out.println("TEST");
        });

        server.createContext("/health", exchange -> {
            String response = "OK";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        server.start();
        System.out.println("Server started on port 8080");
    }
}