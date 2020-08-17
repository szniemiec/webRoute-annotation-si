public class Main {

    public static void main(String[] args) throws Exception {
        ServerApp server = new ServerApp();
        server.registerRoutes(Routes.class);
        server.run();
    }
}
