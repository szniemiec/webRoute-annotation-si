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
        String response = "";
        try {
            Class<?> routesClass = Class.forName("Routes");
            Object routes = routesClass.getDeclaredConstructor().newInstance();
            Method m = map.get(e.getRequestURI().toString());
            if (m.invoke(routes) == null) {
                response = "null";
            } else if (m.invoke(routes).equals("Message ONE")) {
                response = "Message ONE";
            } else if (m.invoke(routes).equals("Message TWO")) {
                response = "Message TWO";
            } else if (m.invoke(routes) instanceof String) {
                response = e.getRequestURI().toString();
            }
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException |
                IllegalAccessException | ClassNotFoundException | NullPointerException exception) {
            response = exception.getClass().getName() + " exception";
            exception.printStackTrace();
        }
        e.sendResponseHeaders(200, response.length());
        OutputStream os = e.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}