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

    @WebRoute(path = "/test")
    public String test3() {
        return "abcd";
    }

    @WebRoute(path = "/null")
    public String test4() {
        return null;
    }

    @WebRoute(path = "/void")
    public void test5() {

    }
}
