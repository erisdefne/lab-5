package interface_adapter.login;

/**
 * The state for the Login View Model.
 */
public class LoginState2 {
    private String access_token = null;
    private String loginError;

    public String getToken() {
        return access_token;
    }

    public String getLoginError() {
        return loginError;
    }

    public void setToken(String access_token) {
        this.access_token = access_token;
    }

    public void setLoginError(String usernameError) {
        this.loginError = usernameError;
    }

}
