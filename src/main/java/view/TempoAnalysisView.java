package view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import interface_adapter.TempoAnalyser.TempoAnalysisController;
import interface_adapter.TempoAnalyser.TempoAnalysisPresenter;

/**
 * The TempoAnalysisView class represents the GUI for analyzing tempos of tracks.
 */
public class TempoAnalysisView extends JPanel {
    private final TempoAnalysisController controller;
    private final TempoAnalysisPresenter presenter;

    public TempoAnalysisView(TempoAnalysisController controller, TempoAnalysisPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;

        setLayout(new BorderLayout());
        final JButton analyzeButton = new JButton("Analyze Tempos");
        analyzeButton.addActionListener(event -> displayTempoAnalysis());
        add(analyzeButton, BorderLayout.SOUTH);
    }

    /**
     * Displays the tempo analysis results.
     */
    private void displayTempoAnalysis() {
        final List<String> trackIds = List.of("3n3Ppam7vgaVa1iaRUc9Lp", "1p80LdxRV74UKvL8gnD7ky");
        final var tempoData = controller.handleAnalyzeTempoRequest(trackIds);
        presenter.present(tempoData);
        JOptionPane.showMessageDialog(this, presenter.getFormattedOutput());
    }

}
