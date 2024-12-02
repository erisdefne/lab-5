package app;

import javax.swing.*;
import java.awt.*;

import interface_adapter.ViewManagerModel;
import interface_adapter.tempo_analyser.TempoAnalyserController;
import interface_adapter.tempo_analyser.TempoAnalyserPresenter;
import use_case.TempoAnalyser.TempoAnalyserInteractor;
import view.TempoAnalyserView;

public class AppBuilder2 {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();

    private TempoAnalyserPresenter tempoAnalyserPresenter;
    private TempoAnalyserController tempoAnalyserController;
    private TempoAnalyserView tempoAnalyserView;

    public AppBuilder2() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder2 addTempoAnalyserUseCase() {
        tempoAnalyserPresenter = new TempoAnalyserPresenter();
        final TempoAnalyserInteractor interactor = new TempoAnalyserInteractor(tempoAnalyserPresenter);
        tempoAnalyserController = new TempoAnalyserController(interactor);

        tempoAnalyserView = new TempoAnalyserView();
        tempoAnalyserView.setController(tempoAnalyserController);
        tempoAnalyserView.setPresenter(tempoAnalyserPresenter);

        cardPanel.add(tempoAnalyserView, "TempoAnalyserView");
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Tempo Analyser Application");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(cardPanel);
        cardLayout.show(cardPanel, "TempoAnalyserView");
        application.setSize(400, 300);
        return application;
    }
}
