package use_case.login;

import interface_adapter.login.LoginPresenter2;

import java.awt.*;
import java.io.IOException;
import java.net.URI;


public class LoginInteractor2 {

    private static String accessToken = null;
    private final LoginPresenter2 loginPresenter2;

    public LoginInteractor2(LoginPresenter2 loginPresenter2) {
        this.loginPresenter2 = loginPresenter2;
    }

    public void execute(LoginInputData2 loginInputData2) {
        if (accessToken == null) {
            try {
                String loginLink = LoginClass.getLoginLink();
                Desktop.getDesktop().browse(URI.create(loginLink));
                accessToken = LoginClass.getAccess_token();
                loginPresenter2.prepareSuccessView();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}