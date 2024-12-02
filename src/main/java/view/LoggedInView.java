package view;

import entity.CurrentUser;
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
    private final JButton genreDistribution;
    private final JButton topSongsButton;
    private final JButton tempoAnalyserButton;
    private TopArtistsController topArtistsController;
    private TopArtistsPresenter topArtistsPresenter;
    private SongRecommendController songRecommendController;
    private SongRecommendPresenter songRecommendPresenter;
    private TempoAnalyserController tempoAnalyserController;
    private TempoAnalyserPresenter tempoAnalyserPresenter;
    public CurrentUser currentUser;

    public LoggedInView() {
        setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns with gaps

        // Initialize and add buttons
        genreDistribution = new JButton("Genre Distribution");
        add(genreDistribution);

        topSongsButton = new JButton("Top Songs");
        add(topSongsButton);

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
                try {
                    Map<String, Integer> topTracks = DataGateway.getTopTrackGenres("short_term", "20", currentUser);
                    Iterator<String> iterator = topTracks.keySet().iterator();
                    String topGenre = iterator.hasNext() ? iterator.next() : null;

                    List<String> userTopTracks = DataGateway.fetchUserTopTracks(currentUser);
                    songRecommendController.fetchRecommendSongs(topGenre, userTopTracks);

                    SwingUtilities.invokeLater(this::displayRecommendedSongs);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error fetching recommended songs: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(recommendSongsButton);
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
                JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                List<String> topArtists = topArtistsPresenter.getTopArtists();
                if (topArtists != null && !topArtists.isEmpty()) {
                    StringBuilder message = new StringBuilder("<html><body>");
                    message.append("<h2>Your Top Artists</h2><ul>");
                    for (String artist : topArtists) {
                        message.append("<li>").append(artist).append("</li>");
                    }
                    message.append("</ul></body></html>");
                    JOptionPane.showMessageDialog(this, message.toString(), "Top Artists", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No top artists found.", "Top Artists", JOptionPane.INFORMATION_MESSAGE);
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
