package app;

import javax.swing.*;
import java.awt.*;

import entity.CurrentUser;
import interface_adapter.ViewManagerModel;
import interface_adapter.compare_playlists.PlaylistSimilarityController;
import interface_adapter.compare_playlists.PlaylistSimilarityPresenter;
import interface_adapter.compare_playlists.PlaylistSimilarityViewModel;
import interface_adapter.login.LoginController2;
import interface_adapter.login.LoginPresenter2;
import interface_adapter.login.LoginViewModel2;
import use_case.compare_playlists.ComparePlaylistInteractor;
import use_case.compare_playlists.SpotifyPlaylistRepository;
import use_case.login.LoginInteractor2;
import view.LoginView2;
import view.LoggedInView;
import view.SimilarityScorePanel;

public class AppBuilder2 {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private LoginViewModel2 loginViewModel2;
    private LoginView2 loginView2;
    private LoggedInView loggedInView;
    private SimilarityScorePanel similarityScorePanel;
    private PlaylistSimilarityPresenter playlistSimilarityPresenter;
    private PlaylistSimilarityController playlistSimilarityController;
    private final CurrentUser currentUser = new CurrentUser();

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
        loggedInView = new LoggedInView(cardPanel, cardLayout);
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

    public AppBuilder2 addSimilarityScorePanel() {
        similarityScorePanel = new SimilarityScorePanel(cardPanel, cardLayout, loggedInView); // Pass loggedInView
        cardPanel.add(similarityScorePanel, "Similarity Score Panel");
        return this;
    }


    public AppBuilder2 addComparePlaylistsUseCase() {
        PlaylistSimilarityViewModel playlistSimilarityViewModel = new PlaylistSimilarityViewModel();

        // Pass SimilarityScorePanel to the Presenter
        playlistSimilarityPresenter = new PlaylistSimilarityPresenter(playlistSimilarityViewModel, similarityScorePanel);

        ComparePlaylistInteractor comparePlaylistInteractor = new ComparePlaylistInteractor(new SpotifyPlaylistRepository(), playlistSimilarityPresenter);
        playlistSimilarityController = new PlaylistSimilarityController(comparePlaylistInteractor, playlistSimilarityPresenter);

        loggedInView.setPlaylistSimilarityController(playlistSimilarityController);

        return this;
    }

    public JFrame build() {
        JFrame application = new JFrame("Login Application");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);
        cardLayout.show(cardPanel, loginView2.getViewName());


        application.setSize(600, 400);
        return application;
    }
}