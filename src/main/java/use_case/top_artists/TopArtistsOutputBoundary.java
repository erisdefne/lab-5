package use_case.top_artists;

import java.util.List;

/**
 * Output boundary interface for the Top Artists use case.
 */
public interface TopArtistsOutputBoundary {

    /**
     * Present the list of top artists to the UI.
     *
     * @param topArtists A list of artist names ranked by play count.
     */
    void presentTopArtists(List<String> topArtists);

    /**
     * Handle errors during the data retrieval process.
     *
     * @param errorMessage The error message to display.
     */
    void handleError(String errorMessage);
}
