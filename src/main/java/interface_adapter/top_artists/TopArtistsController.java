package interface_adapter.top_artists;

import use_case.top_artists.TopArtistsInputBoundary;

/**
 * Controller for the Top Artists use case.
 */
public class TopArtistsController {

    private final TopArtistsInputBoundary interactor;

    public TopArtistsController(TopArtistsInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Fetch the top artists based on the specified parameters.
     *
     * @param timeRange The time range for fetching top artists (e.g., short_term, medium_term, long_term).
     * @param limit The number of top artists to fetch.
     */
    public void fetchTopArtists(String timeRange, int limit) {
        interactor.fetchTopArtists(timeRange, limit);
    }
}
