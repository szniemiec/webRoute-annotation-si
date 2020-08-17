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
//        for (Class<?> c : classes) {
////            if (c.isAnnotationPresent(WebRoute.class)) {
//            Method[] methods = c.getDeclaredMethods();
//            for (Method m : methods) {
//                if (m.isAnnotationPresent(WebRoute.class)) {
//                    WebRoute webRoute = m.getAnnotation(WebRoute.class);
//                    String path = webRoute.path();
//                    map.put(path, m);
//                }
//            }
////            }
//        }

    //czy klasa powinna mieć adnotację Webroute

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

// 1
// registerRoutes - z każdej z klas chcę wyciągnąć metody (refleksja)
// - odfiltrować te, które nie mają @WebRoute
// - zainicjalizować mapę (klucz: ścieżka w webroute (path), wartość: sama metoda)

//
//        2 sposb metody referencyjne
//
//        Method[] methods = c.getMethods();
//        for (Method m : methods) {
//        if (m.isAnnotationPresent(WebRoute.class)) {
//        WebRoute webRoute = m.getAnnotation(WebRoute.class);
//        String path = webRoute.path();
//        map.put(path, m);
//        }
//        }
//        }
//
//
//        classes.filter(c->c.isAnnotationPresent(WebRoute.class))).foreach(this::doSomething)
