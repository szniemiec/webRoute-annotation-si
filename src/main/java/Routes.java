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

    @WebRoute(path = "/lol")
    public String test3() {
        return "lfsfd";
    }

    @WebRoute(path = "/nool")
    public String test4() {
        throw new NullPointerException();
    }
}
