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
    private final JPanel centerPanel; // Panel to hold the dynamic content (chart or error text)

    public GenreDistributionView() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Genre Distribution (Last 6 Months)", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        // Panel for dynamic content
        centerPanel = new JPanel(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);

        // Error text
        errorText = new JTextArea();
        errorText.setEditable(false);
        errorText.setForeground(Color.RED);
        centerPanel.add(new JScrollPane(errorText), BorderLayout.CENTER);

        // Go Back button
        goBackButton = new JButton("Go Back");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(goBackButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void updateView(GenreDistributionViewModel viewModel) {
        centerPanel.removeAll(); // Clear previous content in the center panel

        if (viewModel.getError() != null) {
            errorText.setText("Error: " + viewModel.getError());
            centerPanel.add(new JScrollPane(errorText), BorderLayout.CENTER);
        } else {
            Map<String, Integer> genreData = viewModel.getGenreData();
            if (genreData == null || genreData.isEmpty()) {
                errorText.setText("No genres found.");
                centerPanel.add(new JScrollPane(errorText), BorderLayout.CENTER);
            } else {
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
                centerPanel.add(chartPanel, BorderLayout.CENTER);
            }
        }

        centerPanel.revalidate();
        centerPanel.repaint();
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