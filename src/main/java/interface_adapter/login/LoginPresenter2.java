package interface_adapter.login;

import interface_adapter.ViewManagerModel;

/**
 * Presenter for the login use case.
 */
public class LoginPresenter2 {

    private final ViewManagerModel viewManagerModel;

    public LoginPresenter2(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Notify the UI of a successful login.
     */
    public void presentSuccess() {
        viewManagerModel.setState("logged in");
    }

    /**
     * Notify the UI of a failed login with an error message.
     *
     * @param errorMessage The error message to display.
     */
    public void presentError(String errorMessage) {
        viewManagerModel.setState("error");
        viewManagerModel.setErrorMessage(errorMessage);
    }
}
