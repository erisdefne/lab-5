
package interface_adapter.login;

import use_case.login.LoginInputData2;
import use_case.login.LoginInteractor2;



public class LoginController2 {

    private final LoginInteractor2 loginInteractor2;

    public LoginController2(LoginInteractor2 loginInteractor2) {
        this.loginInteractor2 = loginInteractor2;
    }

    public void execute() {
        LoginInputData2 inputData = new LoginInputData2();
        loginInteractor2.execute(inputData);
    }
}