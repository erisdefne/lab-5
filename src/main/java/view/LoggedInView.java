package view;

import data_access.DataGateway;
import interface_adapter.song_recommend.SongRecommendController;
import interface_adapter.song_recommend.SongRecommendPresenter;
import interface_adapter.song_recommend.SongRecommendState;
import interface_adapter.song_recommend.SongRecommendViewModel;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * The View for the logged-in state of the application.
 */
public class LoggedInView extends JPanel {

    private final String viewName = "logged in";
    private SongRecommendController songRecommendController;
    private SongRecommendPresenter songRecommendPresenter;

    public LoggedInView() {
        setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns with gaps

        JButton recommendSongsButton = new JButton("Recommend Songs");
        recommendSongsButton.addActionListener(e -> {
            if (songRecommendController != null) {
                String topGenre = null;
                Map<String, Integer> topTracks;
                try {
                    topTracks = DataGateway.getTopTrackGenres("short_term", "20");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Iterator<String> iterator = topTracks.keySet().iterator();
                if (iterator.hasNext()) {
                    topGenre = iterator.next();
                }
                java.util.List<String> userTopTracks; // Explicitly use java.util.List
                try {
                    userTopTracks = DataGateway.fetchUserTopTracks();
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
        add(new JButton("TopSongs"));
        add(new JButton("2"));
        add(new JButton("3"));
        add(new JButton("4"));
        add(new JButton("5"));
        add(new JButton("6"));
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

    public String getViewName() {
        return viewName;
    }

    public void setSongRecommendController(SongRecommendController songRecommendController){
        this.songRecommendController = songRecommendController;
    }

    public void setSongRecommendPresenter(SongRecommendPresenter songRecommendPresenter){
        this.songRecommendPresenter = songRecommendPresenter;
    }
}
