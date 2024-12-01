package view;

import interface_adapter.genre_distribution.GenreDistributionViewModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

public class GenreDistributionView extends JPanel {
    private final JTextArea errorText;
    private final JButton goBackButton;

    public GenreDistributionView() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Genre Distribution (Last 6 Months)", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        errorText = new JTextArea();
        errorText.setEditable(false);
        errorText.setForeground(Color.RED);
        add(new JScrollPane(errorText), BorderLayout.CENTER);

        // Go Back button
        goBackButton = new JButton("Go Back");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(goBackButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void updateView(GenreDistributionViewModel viewModel) {
        if (viewModel.getError() != null) {
            errorText.setText("Error: " + viewModel.getError());
            return;
        }

        Map<String, Integer> genreData = viewModel.getGenreData();
        if (genreData == null || genreData.isEmpty()) {
            errorText.setText("No genres found.");
            return;
        }

        // Generate a pie chart for genre distribution
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Integer> entry : genreData.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Genre Distribution",
                dataset,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        removeAll();
        add(chartPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }


    /**
     * Sets an ActionListener for the Go Back button.
     *
     * @param listener The ActionListener to be called when the button is clicked.
     */
    public void setGoBackButtonListener(ActionListener listener) {
        goBackButton.addActionListener(listener);
    }
}