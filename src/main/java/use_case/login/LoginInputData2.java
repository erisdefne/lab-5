package use_case.login;

public class LoginInputData2 {
    private final String username;
    private final String password;

    public LoginInputData2(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
