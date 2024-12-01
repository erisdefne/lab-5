package use_case.top_artists;

/**
 * Input data class for the Top Artists use case.
 */
public class TopArtistsInputData {

    private final String timeRange;
    private final int limit;

    public TopArtistsInputData(String timeRange, int limit) {
        this.timeRange = timeRange;
        this.limit = limit;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public int getLimit() {
        return limit;
    }
}
