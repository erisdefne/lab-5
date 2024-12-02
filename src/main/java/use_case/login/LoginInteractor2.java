package use_case.login;

import entity.CurrentUser;
import interface_adapter.login.LoginPresenter2;

public class LoginInteractor2 implements LoginInputBoundary {

    private final LoginPresenter2 loginPresenter;
    private final CurrentUser currentUser;

    public LoginInteractor2(LoginPresenter2 loginPresenter, CurrentUser currentUser) {
        this.loginPresenter = loginPresenter;
        this.currentUser = currentUser;
    }

    @Override
    public void login(LoginInputData2 inputData) {
        String username = inputData.getUsername();
        String password = inputData.getPassword();

        // Simulate login logic (you can replace this with actual logic)
        if ("testUser".equals(username) && "testPass".equals(password)) {
            currentUser.setAccessToken("mockAccessToken");
            loginPresenter.presentSuccess();
        } else {
            loginPresenter.presentError("Invalid username or password.");
        }
    }
}

