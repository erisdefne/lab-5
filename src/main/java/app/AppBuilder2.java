package app;

import javax.swing.*;
import java.awt.*;

import entity.CurrentUser;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController2;
import interface_adapter.login.LoginPresenter2;
import interface_adapter.login.LoginViewModel2;
import interface_adapter.top_artists.TopArtistsController;
import interface_adapter.top_artists.TopArtistsPresenter;
import use_case.login.LoginInteractor2;
import use_case.top_artists.TopArtistsInteractor;
import view.LoginView2;
import view.LoggedInView;

public class AppBuilder2 {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private LoginViewModel2 loginViewModel2;
    private LoginView2 loginView2;
    private LoggedInView loggedInView;
    private final CurrentUser currentUser = new CurrentUser();

    private TopArtistsController topArtistsController;
    private TopArtistsPresenter topArtistsPresenter;

    public AppBuilder2() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder2 addLoginView() {
        loginViewModel2 = new LoginViewModel2();
        loginView2 = new LoginView2(loginViewModel2);
        cardPanel.add(loginView2, loginView2.getViewName());
        return this;
    }

    public AppBuilder2 addLoggedInView() {
        loggedInView = new LoggedInView();
        loggedInView.setTopArtistsController(topArtistsController); // Wire the controller
        loggedInView.setTopArtistsPresenter(topArtistsPresenter);   // Wire the presenter
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    public AppBuilder2 addLoginUseCase() {
        LoginPresenter2 loginPresenter2 = new LoginPresenter2(viewManagerModel);
        LoginInteractor2 loginInteractor2 = new LoginInteractor2(loginPresenter2, currentUser);
        LoginController2 loginController2 = new LoginController2(loginInteractor2);
        loginView2.setLoginController(loginController2);

        viewManagerModel.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName())) {
                cardLayout.show(cardPanel, evt.getNewValue().toString());
            }
        });
        return this;
    }

    public AppBuilder2 addTopArtistsUseCase(String accessToken) {
        topArtistsPresenter = new TopArtistsPresenter();
        TopArtistsInteractor interactor = new TopArtistsInteractor(topArtistsPresenter, accessToken);
        topArtistsController = new TopArtistsController(interactor);
        return this;
    }

    public JFrame build() {
        JFrame application = new JFrame("Login Application");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);
        cardLayout.show(cardPanel, loginView2.getViewName());
        application.setSize(300, 200);
        return application;
    }
}
