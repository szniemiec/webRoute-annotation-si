package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class Handler implements HttpHandler {

    Map<String, Method> map;

    public Handler(Map<String, Method> map) {
        this.map = map;
    }

    @Override
    public void handle(final HttpExchange e) throws IOException {
        String response = "This is the response";
        e.sendResponseHeaders(200, response.length());
        OutputStream os = e.getResponseBody();
        os.write(response.getBytes());
        os.close();
        try {
            Class<?> routesClass = Class.forName("Routes");
            Object routes = routesClass.getDeclaredConstructor().newInstance();
            Method m = map.get(e.getRequestURI().toString());
            if (m.invoke(routes).equals("Message ONE")) {
                System.out.println("home message one");
            } else if (m.invoke(routes).equals("Message TWO")) {
                System.out.println("users message two");
            } else {
                System.out.println("other");
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                InstantiationException | InvocationTargetException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
    }
}

// 2
// z exchange wyciągnąć ścieżkę, na którą poszły zapytania
// odniosę się do mapy z konstruktora (map.get(ścieżka) i dostaję metodę obsługującą zapytanie)
// wywołać metodę i zwrócić to co metoda zwraca do użytkownika
