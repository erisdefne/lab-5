package app;

import javax.swing.*;
import java.awt.*;

import entity.CurrentUser;
import interface_adapter.ViewManagerModel;
import interface_adapter.genre_distribution.GenreDistributionController;
import interface_adapter.genre_distribution.GenreDistributionPresenter;
import interface_adapter.genre_distribution.GenreDistributionViewModel;
import interface_adapter.login.LoginController2;
import interface_adapter.login.LoginPresenter2;
import interface_adapter.login.LoginViewModel2;
import interface_adapter.top_songs.TopSongsController;
import interface_adapter.top_songs.TopSongsPresenter;
import interface_adapter.top_songs.TopSongsViewModel;
import use_case.genre_distribution.GenreDistributionInteractor;
import use_case.login.LoginInteractor2;
import use_case.topsongs.TopSongsInteractor;
import view.GenreDistributionView;
import view.LoginView2;
import view.LoggedInView;
import view.TopSongsView;

public class AppBuilder2 {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private LoginViewModel2 loginViewModel2;
    private LoginView2 loginView2;
    private LoggedInView loggedInView;
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
        loggedInView = new LoggedInView();
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

    public AppBuilder2 addTopSongsUseCase() {
        TopSongsViewModel topSongsViewModel = new TopSongsViewModel();
        TopSongsPresenter topSongsPresenter = new TopSongsPresenter(topSongsViewModel);
        TopSongsInteractor topSongsInteractor = new TopSongsInteractor(topSongsPresenter);
        TopSongsController topSongsController = new TopSongsController(topSongsInteractor);

        TopSongsView topSongsView = new TopSongsView();

        // Set up the action listener for the "TopSongs" button in LoggedInView
        loggedInView.setTopSongsActionListener(e -> {
            // Fetch the top songs
            topSongsController.execute(currentUser);
            // Update the view with the fetched data
            topSongsView.updateView(topSongsViewModel);
            // Display the TopSongsView
            cardPanel.add(topSongsView, "topSongs");
            cardLayout.show(cardPanel, "topSongs");
        });

        // Set up the "Go Back" button in TopSongsView
        topSongsView.setGoBackButtonListener(e -> {
            // Show the LoggedInView when "Go Back" is clicked
            cardLayout.show(cardPanel, loggedInView.getViewName());
        });

        return this;
    }

    public AppBuilder2 addGenreDistributionUseCase() {
        GenreDistributionViewModel viewModel = new GenreDistributionViewModel();
        GenreDistributionPresenter presenter = new GenreDistributionPresenter(viewModel);
        GenreDistributionInteractor interactor = new GenreDistributionInteractor(presenter);
        GenreDistributionController controller = new GenreDistributionController(interactor);
        GenreDistributionView view = new GenreDistributionView();

        // Set up the action listener for "View Genre Distribution"
        loggedInView.setGenreDistributionActionListener(e -> {
            controller.fetchAndPresentGenreDistribution(currentUser);
            view.updateView(viewModel);
            cardPanel.add(view, "genreDistribution");
            cardLayout.show(cardPanel, "genreDistribution");
        });

        // Set up the "Go Back" button in TopSongsView
        view.setGoBackButtonListener(e -> {
            // Show the LoggedInView when "Go Back" is clicked
            cardLayout.show(cardPanel, loggedInView.getViewName());
        });

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