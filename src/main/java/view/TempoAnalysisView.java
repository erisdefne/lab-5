package view;

import interface_adapter.TempoAnalyser.TempoAnalysisController;
import interface_adapter.TempoAnalyser.TempoAnalysisPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TempoAnalysisView extends JPanel {
    private final TempoAnalysisController controller;
    private final TempoAnalysisPresenter presenter;
    private final JButton goBackButton = new JButton("Go Back");
    private final JButton analyseButton = new JButton("Analyse Tempos");

    public TempoAnalysisView(TempoAnalysisController controller, TempoAnalysisPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;

        setLayout(new BorderLayout());
        add(analyseButton, BorderLayout.CENTER);
        add(goBackButton, BorderLayout.SOUTH);

        analyseButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Analysis complete: " + presenter.getResults());
        });
    }

    public void setGoBackButtonListener(ActionListener listener) {
        goBackButton.addActionListener(listener);
    }
}
