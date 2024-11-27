package use_case.top_artists;

import com.fasterxml.jackson.databind.JsonNode;
import data_access.DataGetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Interactor for the Top Artists use case.
 */
public class TopArtistsInteractor implements TopArtistsInputBoundary {

    private static final String TOP_ARTISTS_URL = "https://api.spotify.com/v1/me/top/artists";

    private final TopArtistsOutputBoundary outputBoundary;
    private final String token; // Inject token dynamically

    public TopArtistsInteractor(TopArtistsOutputBoundary outputBoundary, String token) {
        this.outputBoundary = outputBoundary;
        this.token = token;
    }

    @Override
    public void fetchTopArtists(String timeRange, int limit) {
        try {
            // Build the API URL
            String url = TOP_ARTISTS_URL + "?time_range=" + timeRange + "&limit=" + limit;

            // Fetch data from Spotify API
            JsonNode data = DataGetter.getData(url, token);

            // Parse artist names
            List<String> topArtists = new ArrayList<>();
            JsonNode items = data.get("items");
            if (items != null) {
                for (JsonNode artist : items) {
                    topArtists.add(artist.get("name").asText());
                }
            }

            // Return results to the output boundary
            outputBoundary.presentTopArtists(topArtists);

        } catch (IOException e) {
            outputBoundary.handleError("Failed to fetch top artists: " + e.getMessage());
        }
    }
}
