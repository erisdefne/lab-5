package view;

import entity.CurrentUser;
import interface_adapter.top_artists.TopArtistsController;
import interface_adapter.top_artists.TopArtistsPresenter;

import data_access.DataGateway;
import interface_adapter.song_recommend.SongRecommendController;
import interface_adapter.song_recommend.SongRecommendPresenter;
import interface_adapter.song_recommend.SongRecommendState;
import interface_adapter.song_recommend.SongRecommendViewModel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * The View for the logged-in state of the application.
 */
public class LoggedInView extends JPanel {

    private final String viewName = "logged in";
    private final JButton genreDistribution;
    private final JButton topSongsButton;
    private TopArtistsController topArtistsController;
    private TopArtistsPresenter topArtistsPresenter;
    private SongRecommendController songRecommendController;
    private SongRecommendPresenter songRecommendPresenter;
    public CurrentUser currentUser;

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
        JButton recommendSongsButton = new JButton("Recommend Songs");
        recommendSongsButton.addActionListener(e -> {
            if (songRecommendController != null) {
                String topGenre = null;
                Map<String, Integer> topTracks;
                try {
                    topTracks = DataGateway.getTopTrackGenres("short_term", "20", currentUser);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Iterator<String> iterator = topTracks.keySet().iterator();
                if (iterator.hasNext()) {
                    topGenre = iterator.next();
                }
                java.util.List<String> userTopTracks; // Explicitly use java.util.List
                try {
                    userTopTracks = DataGateway.fetchUserTopTracks(currentUser);
                } catch (IOException ex) {
                    ex.printStackTrace(); // Print the exception for debugging
                    JOptionPane.showMessageDialog(this, "Error fetching user top tracks: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                System.out.println("Top Genre: " + topGenre); // Debug
                System.out.println("User Top Tracks: " + userTopTracks); // Debug

                songRecommendController.fetchRecommendSongs(topGenre, userTopTracks);
                SwingUtilities.invokeLater(this::displayRecommendedSongs);
            } else {
                System.out.println("SongRecommendController is null!"); // Debug
            }
        });
        add(recommendSongsButton);
        // Add buttons
    }
    private void displayRecommendedSongs() {
        if (songRecommendPresenter != null) {
            SongRecommendState viewModel = SongRecommendViewModel.getState();
            String errorMessage = viewModel.getErrorMessage();

            if (errorMessage != null) {
                JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Map<String, String> songs = viewModel.getRecommendedSongs();
                if (songs != null && !songs.isEmpty()) {
                    StringBuilder message = new StringBuilder("Recommended Songs:\n");
                    songs.forEach((track, artist) -> message.append(track).append(" by ").append(artist).append("\n"));
                    JOptionPane.showMessageDialog(this, message.toString(), "Recommendations", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No recommendations found.", "Recommendations", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
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

    public void setSongRecommendController(SongRecommendController songRecommendController){
        this.songRecommendController = songRecommendController;
    }

    public void setSongRecommendPresenter(SongRecommendPresenter songRecommendPresenter){
        this.songRecommendPresenter = songRecommendPresenter;
    }
}



