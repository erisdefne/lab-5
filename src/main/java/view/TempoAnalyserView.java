package view;

import interface_adapter.tempo_analyser.TempoAnalyserController;
import interface_adapter.tempo_analyser.TempoAnalyserPresenter;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class TempoAnalyserView extends JPanel {

    private TempoAnalyserController controller;
    private TempoAnalyserPresenter presenter;

    public TempoAnalyserView() {
        setLayout(new BorderLayout());

        final JLabel title = new JLabel("Tempo Analyser");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        final JButton analyseButton = new JButton("Analyse Tempo");
        analyseButton.addActionListener(e -> {
            if (controller != null) {
                controller.analyseTempo(Arrays.asList(80.0, 95.0, 130.0));
                displayResults();
            }
        });

        add(title, BorderLayout.NORTH);
        add(analyseButton, BorderLayout.CENTER);
    }

    private void displayResults() {
        if (presenter != null) {
            final String message;
            if (presenter.getErrorMessage() != null) {
                message = presenter.getErrorMessage();
            }
            else {
                message = presenter.getTempoAnalysis().toString();
            }
            JOptionPane.showMessageDialog(this, message, "Tempo Analysis", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void setController(TempoAnalyserController controller) {
        this.controller = controller;
    }

    public void setPresenter(TempoAnalyserPresenter presenter) {
        this.presenter = presenter;
    }
}
