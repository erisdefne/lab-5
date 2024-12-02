package use_case.top_artists;

/**
 * Input boundary interface for the Top Artists use case.
 */
public interface TopArtistsInputBoundary {

    /**
     * Fetch the top artists for the user.
     *
     * @param timeRange The time range for fetching top artists (e.g., short_term, medium_term, long_term).
     * @param limit The number of top artists to fetch.
     */
    void fetchTopArtists(String timeRange, int limit);
}
