package interface_adapter.top_artists;

import use_case.top_artists.TopArtistsOutputBoundary;

import java.util.List;

/**
 * Presenter for formatting the top artists data for the UI.
 */
public class TopArtistsPresenter implements TopArtistsOutputBoundary {

    private List<String> topArtists;
    private String errorMessage;

    @Override
    public void presentTopArtists(List<String> top_artists) {
        this.topArtists = top_artists;
        this.errorMessage = null;
    }

    @Override
    public void handleError(String error) {
        this.errorMessage = error;
        this.topArtists = null;
    }

    /**
     * Get the formatted list of top artists for the UI.
     *
     * @return A list of artist names or null if an error occurred.
     */
    public List<String> getTopArtists() {
        return topArtists;
    }

    /**
     * Get the error message for the UI.
     *
     * @return An error message or null if no error occurred.
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}

