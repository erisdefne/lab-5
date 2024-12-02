package use_case.login;

import entity.CurrentUser;
import interface_adapter.login.LoginPresenter2;

public class LoginInteractor2 {

    private static String accessToken = null;
    private final LoginPresenter2 loginPresenter2;
    private final CurrentUser currentUser;

    public LoginInteractor2(LoginPresenter2 loginPresenter2, CurrentUser currentUser) {
        this.loginPresenter2 = loginPresenter2;
        this.currentUser = currentUser;
    }

    public void execute(LoginInputData2 loginInputData2) {
        if (accessToken == null) {
            try {
                LoginClass loginClass = new LoginClass();
                while (accessToken == null || accessToken.isEmpty()) {
                    Thread.sleep(500); // Poll every 500 milliseconds
                    accessToken = loginClass.getAccess_token(); // Get the token
                }

                // Update CurrentUser with the access token
                currentUser.setAccessToken(accessToken);

                // Notify the presenter that login was successful
                loginPresenter2.prepareSuccessView();
            } catch (Exception ex) {
                throw new RuntimeException("Error during login: " + ex.getMessage(), ex);
            }
        }
    }
}