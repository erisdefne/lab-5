package interface_adapter.compare_playlists;

import use_case.compare_playlists.PlaylistsOutputBoundary;
import use_case.compare_playlists.PlaylistsOutputData;
import view.SimilarityScorePanel;

import javax.swing.*;

public class PlaylistSimilarityPresenter implements PlaylistsOutputBoundary {

    private PlaylistSimilarityViewModel playlistSimilarityViewModel;
    private SimilarityScorePanel similarityScorePanel;  // Reference to SimilarityScorePanel

    public PlaylistSimilarityPresenter(PlaylistSimilarityViewModel playlistSimilarityViewModel, SimilarityScorePanel similarityScorePanel) {
        this.playlistSimilarityViewModel = playlistSimilarityViewModel;
        this.similarityScorePanel = similarityScorePanel;  // Initialize the reference
    }

    @Override
    public void prepareSuccessView(PlaylistsOutputData outputData) {
        System.out.println("Success: Similarity score is " + outputData.getSimilarityScore());  // Debugging

        playlistSimilarityViewModel.setSimilarityScore(outputData.getSimilarityScore());
        playlistSimilarityViewModel.firePropertyChanged();  // Notify that data has changed

        // Update the SimilarityScorePanel
        SwingUtilities.invokeLater(() -> {
            similarityScorePanel.updateSimilarityScore(outputData.getSimilarityScore());
        });
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println("Error: " + errorMessage);  // Debugging

        playlistSimilarityViewModel.setErrorMessage(errorMessage);
        playlistSimilarityViewModel.firePropertyChanged();  // Notify that data has changed

        // Update the SimilarityScorePanel with the error message
        SwingUtilities.invokeLater(() -> {
            similarityScorePanel.displayErrorMessage(errorMessage);
        });
    }
}
