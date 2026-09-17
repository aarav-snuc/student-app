package com.lab;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/** Minimal Student Management web application. */
public class StudentApp {

    static final StudentStore STORE = new StudentStore();

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", StudentApp::home);
        server.createContext("/students", StudentApp::students);
        server.createContext("/add", StudentApp::add);
        server.start();
        System.out.println("Student Management System running on port " + port);
    }

    /** Landing page listing the available operations. */
    static void home(HttpExchange ex) throws IOException {
        send(ex, 200, """
                Student Management System
                -------------------------
                GET  /students         view all students
                GET  /add?name=Asha    add a student
                """);
    }

    /** View all students, or add one via POST. */
    static void students(HttpExchange ex) throws IOException {
        if ("POST".equals(ex.getRequestMethod())) {
            String name = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            STORE.add(name);
            send(ex, 201, "Added: " + name.trim() + "\n");
            return;
        }
        StringBuilder out = new StringBuilder("Students (" + STORE.count() + "):\n");
        for (String s : STORE.list()) {
            out.append(" - ").append(s).append("\n");
        }
        send(ex, 200, out.toString());
    }

    /** Add a student from the browser: /add?name=Asha */
    static void add(HttpExchange ex) throws IOException {
        String query = ex.getRequestURI().getQuery();
        if (query == null || !query.startsWith("name=")) {
            send(ex, 400, "Usage: /add?name=Asha\n");
            return;
        }
        String name = URLDecoder.decode(query.substring(5), StandardCharsets.UTF_8);
        STORE.add(name);
        send(ex, 200, "Added: " + name + "\nTotal students: " + STORE.count() + "\n");
    }

    static void send(HttpExchange ex, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().add("Content-Type", "text/plain; charset=utf-8");
        ex.sendResponseHeaders(status, bytes.length);
        ex.getResponseBody().write(bytes);
        ex.close();
    }
}
