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
        Arrays.stream(classes).forEach(c -> {
            Method[] methods = c.getMethods();
            for (Method m : methods) {
                if (m.isAnnotationPresent(WebRoute.class)) {
                    WebRoute webRoute = m.getAnnotation(WebRoute.class);
                    String path = webRoute.path();
                    map.put(path, m);
                }
            }
        });
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