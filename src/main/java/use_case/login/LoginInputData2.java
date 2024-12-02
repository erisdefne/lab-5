package use_case.login;

/**
 * The Input Data for the Login Use Case.
 */
public class LoginInputData2 {

    private final String access_token;

    public LoginInputData2() {
        this.access_token = null;
    }

    String getToken() {
        return access_token;
    }

}