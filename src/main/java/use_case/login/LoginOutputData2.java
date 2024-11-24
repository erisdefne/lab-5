package use_case.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData2 {

    private final String username;
    private final boolean useCaseFailed;

    public LoginOutputData2(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

}
