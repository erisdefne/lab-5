package app;

import javax.swing.*;
import java.awt.*;
import entity.CurrentUser;
import interface_adapter.TempoAnalyser.TempoAnalysisController;
import interface_adapter.TempoAnalyser.TempoAnalysisPresenter;
import use_case.TempoAnalyser.TempoAnalyserFetcher;
import use_case.TempoAnalyser.TempoAnalysisInteractor;
import view.TempoAnalysisView;

public class AppBuilder2 {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private LoginView2 loginView2;
    private LoggedInView loggedInView;
    private final CurrentUser currentUser = new CurrentUser();

    public AppBuilder2() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder2 addLoginView() {
        loginView2 = new LoginView2();
        cardPanel.add(loginView2, loginView2.getViewName());
        return this;
    }

    public AppBuilder2 addLoggedInView() {
        loggedInView = new LoggedInView();
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    public AppBuilder2 addTempoAnalyserUseCase() {
        // Initialize the fetcher
        final TempoAnalyserFetcher fetcher = new TempoAnalyserFetcher();

        // Initialize the presenter
        final TempoAnalysisPresenter presenter = new TempoAnalysisPresenter();

        // Initialize the interactor
        final TempoAnalysisInteractor interactor = new TempoAnalysisInteractor(fetcher, presenter);

        // Initialize the controller
        final TempoAnalysisController controller = new TempoAnalysisController(interactor);

        // Create the TempoAnalysisView
        final TempoAnalysisView tempoAnalysisView = new TempoAnalysisView(controller, presenter);

        // Add the TempoAnalysisView to the application card panel
        cardPanel.add(tempoAnalysisView, "tempoAnalysisView");

        // Set up the action listener in LoggedInView
        loggedInView.setTempoAnalyserActionListener(e -> {
            cardLayout.show(cardPanel, "tempoAnalysisView");
        });

        // Set up the "Go Back" button in TempoAnalysisView
        tempoAnalysisView.setGoBackButtonListener(e -> {
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
