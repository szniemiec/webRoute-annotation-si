import annotations.WebRoute;


public class Routes {

    @WebRoute(path = "/home")
    public String test1() {
        return "Message ONE";
    }

    @WebRoute(path = "/users")
    public String test2() {
        return "Message TWO";
    }
}
