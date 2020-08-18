import annotations.WebRoute;
import com.sun.net.httpserver.HttpServer;
import handlers.Handler;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ServerApp {

    Map<String, Method> map = null;

    public void registerRoutes(Class<?>... classes) {
        map = new HashMap<>();
        Arrays.stream(classes).flatMap(c -> Arrays.stream(c.getMethods()))
                .filter(method -> method.isAnnotationPresent(WebRoute.class))
                .forEach(method -> map.put(method.getAnnotation(WebRoute.class).path(), method));
    }

    public void run() throws Exception {
        if (map == null) {
            throw new Exception("Map is null");
        }
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new Handler(map));
        server.setExecutor(null);
        server.start();
    }
}