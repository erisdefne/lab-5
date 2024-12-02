package interface_adapter.login;

import interface_adapter.ViewManagerModel;

public class LoginPresenter2 {

    private final ViewManagerModel viewManagerModel;

    public LoginPresenter2(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    public void prepareSuccessView() {
        viewManagerModel.setState("logged in");
        viewManagerModel.firePropertyChanged();
    }
}