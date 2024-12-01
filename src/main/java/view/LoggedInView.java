package view;

import interface_adapter.top_artists.TopArtistsController;
import interface_adapter.top_artists.TopArtistsPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The View for the logged-in state of the application.
 */
public class LoggedInView extends JPanel {

    private final String viewName = "logged in";
    private final JButton genreDistribution;
    private final JButton topSongsButton;
    private TopArtistsController topArtistsController;
    private TopArtistsPresenter topArtistsPresenter;

    public LoggedInView() {
        setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns with gaps

        // Initialize and add buttons
        genreDistribution = new JButton("GenreDistribution");
        add(genreDistribution);
        topSongsButton = new JButton("TopSongs");
        add(topSongsButton);
        // Add "Top Artists" button
        JButton topArtistsButton = new JButton("Top Artists");
        topArtistsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (topArtistsController != null) {
                    // Fetch top artists for the past year
                    topArtistsController.fetchTopArtists("long_term", 15);

                    // Display the results
                    SwingUtilities.invokeLater(() -> displayTopArtists());
                }
            }
        });

        add(topArtistsButton);
        // Add additional placeholder buttons
        add(new JButton("TopSongs"));
        add(new JButton("3"));
        add(new JButton("4"));
        add(new JButton("5"));
        add(new JButton("6"));
    }

    private void displayTopArtists() {
        if (topArtistsPresenter != null) {
            String errorMessage = topArtistsPresenter.getErrorMessage();
            if (errorMessage != null) {
                // Display error in a dialog
                JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                List<String> topArtists = topArtistsPresenter.getTopArtists();
                if (topArtists != null && !topArtists.isEmpty()) {
                    // Enhanced formatting and display
                    StringBuilder message = new StringBuilder("<html><body style='background-color: #d4f0f8; font-size: 16px;'>");
                    message.append("<h2 style='text-align: left;'>Spotilyze Results: Your Top Artists Ranked by Play Count</h2>");
                    message.append("<ol style='text-align: left; padding-left: 20px;'>");
                    for (int i = 0; i < topArtists.size(); i++) {
                        message.append("<li>").append(topArtists.get(i)).append("</li>");
                    }
                    message.append("</ol></body></html>");

                    JLabel label = new JLabel(message.toString());
                    label.setOpaque(true);
                    label.setBackground(new java.awt.Color(212, 240, 248));
                    label.setPreferredSize(new java.awt.Dimension(1000, 500));
                    label.setHorizontalAlignment(JLabel.LEFT);

                    // Display the JLabel in a JOptionPane
                    JOptionPane.showMessageDialog(this, label, "Spotilyze: Top Artists", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No top artists found.", "Spotilyze: Top Artists", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }



    public String getViewName() {
        return viewName;
    }

    /**
     * Sets the action listener for the "TopSongs" button.
     * @param actionListener the ActionListener to handle button clicks
     */
    public void setTopSongsActionListener(ActionListener actionListener) {
        topSongsButton.addActionListener(actionListener);
    }

    /**
     * Sets the action listener for the "GenreDistribution" button.
     * @param actionListener the ActionListener to handle button clicks
     */
    public void setGenreDistributionActionListener(ActionListener actionListener) {
        genreDistribution.addActionListener(actionListener);
    }

    public void setTopArtistsController(TopArtistsController topArtistsController) {
        this.topArtistsController = topArtistsController;
    }

    public void setTopArtistsPresenter(TopArtistsPresenter topArtistsPresenter) {
        this.topArtistsPresenter = topArtistsPresenter;
    }
}



