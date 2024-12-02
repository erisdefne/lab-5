package view;

import entity.CurrentUser;
import interface_adapter.compare_playlists.PlaylistSimilarityController;
import interface_adapter.tempo_analyser.TempoAnalyserController;
import interface_adapter.tempo_analyser.TempoAnalyserPresenter;
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
import java.util.Map;
import java.util.Iterator;
import java.util.Arrays;
import java.io.IOException;

/**
 * The View for the logged-in state of the application.
 */
public class LoggedInView extends JPanel {

    private final String viewName = "logged in";
    private boolean isMainPanel = true;
    private JButton genreDistribution;
    private JButton topSongsButton;
    private JButton tempoAnalyserButton;
    private JButton comparePlaylistsButton;
    private TopArtistsController topArtistsController;
    private TopArtistsPresenter topArtistsPresenter;
    private SongRecommendController songRecommendController;
    private SongRecommendPresenter songRecommendPresenter;
    private TempoAnalyserController tempoAnalyserController;
    private TempoAnalyserPresenter tempoAnalyserPresenter;
    private PlaylistSimilarityController playlistSimilarityController;
    public CurrentUser currentUser;

    private JTextField playlist1NameField;
    private JTextField playlist1OwnerField;
    private JTextField playlist2NameField;
    private JTextField playlist2OwnerField;
    private JButton submitButton;

    private JPanel cardPanel;
    private CardLayout cardLayout;

    public LoggedInView(JPanel cardPanel, CardLayout cardLayout) {
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns with gaps
        initializeMainPanel();
    }
        // Initialize and add buttons
        private void initializeMainPanel() {
            removeAll();
            revalidate();
            repaint();

            isMainPanel = true;

            genreDistribution = new JButton("Genre Distribution");
            add(genreDistribution);

            topSongsButton = new JButton("Top Songs");
            add(topSongsButton);

            comparePlaylistsButton = new JButton("Compare Playlists");

            // Add "Top Artists" button
            JButton topArtistsButton = new JButton("Top Artists");
            topArtistsButton.addActionListener(e -> {
                if (topArtistsController != null) {
                    topArtistsController.fetchTopArtists("long_term", 15);
                    SwingUtilities.invokeLater(this::displayTopArtists);
                }
            });
            add(topArtistsButton);

            // Add "Tempo Analysis" button
            tempoAnalyserButton = new JButton("Tempo Analyser");
            tempoAnalyserButton.addActionListener(e -> {
                if (tempoAnalyserController != null) {
                    tempoAnalyserController.analyseTempo("medium_term", 10); // Adjusted to use `timeRange` and `limit`
                    SwingUtilities.invokeLater(this::displayTempoAnalysisResults);
                }
            });
            add(tempoAnalyserButton);

            // Add "Recommend Songs" button
            JButton recommendSongsButton = new JButton("Recommend Songs");
            recommendSongsButton.addActionListener(e -> {
                if (songRecommendController != null) {
                    String topGenre = null;
                    try {
                        Map<String, Integer> topTracks = DataGateway.getTopTrackGenres("short_term", "20", currentUser);
                        topGenre = topTracks.entrySet().stream()
                                .max(Map.Entry.comparingByValue()) // Get the genre with the highest count
                                .map(Map.Entry::getKey)            // Extract the genre name
                                .orElse(null);

                        List<String> userTopTracks = DataGateway.fetchUserTopTracks(currentUser);
                        songRecommendController.fetchRecommendSongs(topGenre, userTopTracks);

                        SwingUtilities.invokeLater(this::displayRecommendedSongs);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error fetching recommended songs: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            add(recommendSongsButton);

            // Add action listener for "Compare Playlists" button
            comparePlaylistsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showPlaylistComparisonInputs();
                }
            });
            add(comparePlaylistsButton);
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

    private void displayTempoAnalysisResults() {
        if (tempoAnalyserPresenter != null) {
            String errorMessage = tempoAnalyserPresenter.getErrorMessage();
            if (errorMessage != null) {
                JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Map<String, Integer> tempoAnalysis = tempoAnalyserPresenter.getTempoAnalysis();
                StringBuilder message = new StringBuilder("Tempo Analysis Results:\n");
                tempoAnalysis.forEach((category, count) -> message.append(category).append(": ").append(count).append("\n"));
                JOptionPane.showMessageDialog(this, message.toString(), "Tempo Analysis", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void showPlaylistComparisonInputs() {
        removeAll(); // Clear the current panel
        revalidate();
        repaint();

        isMainPanel = false; // Mark this as the comparison panel state

        setLayout(new GridLayout(7, 2, 10, 10)); // Adjust layout to include the back button

        // Initialize input fields
        JLabel playlist1NameLabel = new JLabel("Playlist 1 Name:");
        playlist1NameField = new JTextField();
        JLabel playlist1OwnerLabel = new JLabel("Playlist 1 Owner:");
        playlist1OwnerField = new JTextField();

        JLabel playlist2NameLabel = new JLabel("Playlist 2 Name:");
        playlist2NameField = new JTextField();
        JLabel playlist2OwnerLabel = new JLabel("Playlist 2 Owner:");
        playlist2OwnerField = new JTextField();

        // Initialize Submit and Back buttons
        submitButton = new JButton("Submit");
        JButton backButton = new JButton("Go Back");

        // Add components to the comparison panel
        add(playlist1NameLabel);
        add(playlist1NameField);
        add(playlist1OwnerLabel);
        add(playlist1OwnerField);

        add(playlist2NameLabel);
        add(playlist2NameField);
        add(playlist2OwnerLabel);
        add(playlist2OwnerField);

        add(new JLabel()); // Empty cell for alignment
        add(submitButton);

        add(new JLabel()); // Empty cell for alignment
        add(backButton);

        // Add action listener for the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPlaylistComparison();
            }
        });

        // Add action listener for the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToMainPanel(); // Reinitialize the main panel
            }
        });
    }

    /**
     * Processes the playlist comparison by sending user inputs to the Controller.
     */
    private void processPlaylistComparison() {
        String playlist1Name = playlist1NameField.getText().trim();
        String playlist1Owner = playlist1OwnerField.getText().trim();
        String playlist2Name = playlist2NameField.getText().trim();
        String playlist2Owner = playlist2OwnerField.getText().trim();

        if (playlist1Name.isEmpty() || playlist1Owner.isEmpty() ||
                playlist2Name.isEmpty() || playlist2Owner.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (playlistSimilarityController != null) {
            playlistSimilarityController.comparePlaylists(
                    playlist1Name,
                    playlist1Owner,
                    playlist2Name,
                    playlist2Owner
            );

            cardLayout.show(cardPanel, "Similarity Score Panel");
        }
    }

    /**
     * Switches back to the main panel state (buttons view).
     */
    public void switchToMainPanel() {
        initializeMainPanel(); // Reinitialize the main panel
    }

    public void setPlaylistSimilarityController(PlaylistSimilarityController playlistSimilarityController) {
        this.playlistSimilarityController = playlistSimilarityController;
    }


    public String getViewName() {
        return viewName;
    }

    public void setTopSongsActionListener(ActionListener actionListener) {
        topSongsButton.addActionListener(actionListener);
    }

    public void setGenreDistributionActionListener(ActionListener actionListener) {
        genreDistribution.addActionListener(actionListener);
    }

    public void setTopArtistsController(TopArtistsController topArtistsController) {
        this.topArtistsController = topArtistsController;
    }

    public void setTopArtistsPresenter(TopArtistsPresenter topArtistsPresenter) {
        this.topArtistsPresenter = topArtistsPresenter;
    }

    public void setSongRecommendController(SongRecommendController songRecommendController) {
        this.songRecommendController = songRecommendController;
    }

    public void setSongRecommendPresenter(SongRecommendPresenter songRecommendPresenter) {
        this.songRecommendPresenter = songRecommendPresenter;
    }

    public void setTempoAnalyserController(TempoAnalyserController tempoAnalyserController) {
        this.tempoAnalyserController = tempoAnalyserController;
    }

    public void setTempoAnalyserPresenter(TempoAnalyserPresenter tempoAnalyserPresenter) {
        this.tempoAnalyserPresenter = tempoAnalyserPresenter;
    }

    public void setTempoAnalyserActionListener(ActionListener listener) {
        tempoAnalyserButton.addActionListener(listener);
    }
}
