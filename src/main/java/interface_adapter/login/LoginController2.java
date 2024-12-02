
package interface_adapter.login;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData2;

public class LoginController2 {
    private final LoginInputBoundary loginInputBoundary;

    public LoginController2(LoginInputBoundary loginInputBoundary) {
        this.loginInputBoundary = loginInputBoundary;
    }

    public void execute(String username, String password) {
        LoginInputData2 inputData = new LoginInputData2(username, password);
        loginInputBoundary.login(inputData);
    }
}
