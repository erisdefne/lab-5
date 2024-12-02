package view;

import interface_adapter.tempo_analyser.TempoAnalyserController;
import interface_adapter.tempo_analyser.TempoAnalyserPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TempoAnalyserView extends JPanel {
    private TempoAnalyserController controller;
    private TempoAnalyserPresenter presenter;
    private final JButton goBackButton;

    public TempoAnalyserView() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Tempo Analyser");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JButton analyseButton = new JButton("Analyse Tempo");
        analyseButton.addActionListener(e -> {
            if (controller != null) {
                controller.analyseTempo("medium_term", 10);
                displayResults();
            }
            else {
                JOptionPane.showMessageDialog(this, "Controller is not set.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        goBackButton = new JButton("Go Back");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(analyseButton);
        buttonPanel.add(goBackButton);

        add(title, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    public void setController(TempoAnalyserController controller) {
        this.controller = controller;
    }

    public void setPresenter(TempoAnalyserPresenter presenter) {
        this.presenter = presenter;
    }

    public void setGoBackButtonListener(ActionListener listener) {
        goBackButton.addActionListener(listener);
    }

    private void displayResults() {
        if (presenter != null) {
            String errorMessage = presenter.getErrorMessage();
            if (errorMessage != null) {
                JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                StringBuilder message = new StringBuilder("Tempo Analysis Results:\n");
                presenter.getTempoAnalysis().forEach((category, count) ->
                        message.append(category).append(": ").append(count).append("\n"));
                JOptionPane.showMessageDialog(this, message.toString(), "Results", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
