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
    private TopArtistsController topArtistsController;
    private TopArtistsPresenter topArtistsPresenter;

    public LoggedInView() {
        setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns with gaps

        // Add "Top Artists" button
        JButton topArtistsButton = new JButton("Top Artists");
        topArtistsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (topArtistsController != null) {
                    // Fetch top artists for the past year
                    topArtistsController.fetchTopArtists("long_term", 10);

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
                    // Format the list of top artists
                    StringBuilder message = new StringBuilder("Your Top Artists:\n");
                    for (int i = 0; i < topArtists.size(); i++) {
                        message.append(i + 1).append(". ").append(topArtists.get(i)).append("\n");
                    }

                    // Display in a dialog
                    JOptionPane.showMessageDialog(this, message.toString(), "Top Artists", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No top artists found.", "Top Artists", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setTopArtistsController(TopArtistsController topArtistsController) {
        this.topArtistsController = topArtistsController;
    }

    public void setTopArtistsPresenter(TopArtistsPresenter topArtistsPresenter) {
        this.topArtistsPresenter = topArtistsPresenter;
    }
}

