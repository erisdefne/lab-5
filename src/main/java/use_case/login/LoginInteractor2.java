package use_case.login;

import interface_adapter.login.LoginPresenter2;

public class LoginInteractor2 {

    private static String accessToken = null;
    private final LoginPresenter2 loginPresenter2;

    public LoginInteractor2(LoginPresenter2 loginPresenter2) {
        this.loginPresenter2 = loginPresenter2;
    }

    public void execute(LoginInputData2 loginInputData2) {
        if (accessToken == null) {
            try {
                LoginClass loginClass = new LoginClass();
                while (accessToken == null || accessToken.isEmpty()) {
                    Thread.sleep(500); // Poll every 500 milliseconds
                    accessToken = loginClass.getAccess_token();
                }

                loginPresenter2.prepareSuccessView();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}