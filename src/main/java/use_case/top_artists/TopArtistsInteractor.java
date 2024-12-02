package use_case.top_artists;

import com.fasterxml.jackson.databind.JsonNode;
import data_access.DataGetterClass;
import entity.CurrentUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Interactor for the Top Artists use case.
 */
public class TopArtistsInteractor implements TopArtistsInputBoundary {
    private static final String TOP_ARTISTS_URL = "https://api.spotify.com/v1/me/top/artists";

    private final TopArtistsOutputBoundary outputBoundary;
    private final CurrentUser currentUser;

    public TopArtistsInteractor(TopArtistsOutputBoundary outputBoundary, CurrentUser currentUser) {
        this.outputBoundary = outputBoundary;
        this.currentUser = currentUser;
    }

    @Override
    public void fetchTopArtists(String timeRange, int limit) {
        try {
            // Build the API URL
            final String url = TOP_ARTISTS_URL + "?time_range=" + timeRange + "&limit=" + limit;

            // Fetch data using DataGetterClass
            final JsonNode data = DataGetterClass.getData(url, currentUser);

            // Parse artist names
            final List<String> topArtists = new ArrayList<>();
            final JsonNode items = data.get("items");
            if (items != null) {
                for (JsonNode artist : items) {
                    topArtists.add(artist.get("name").asText());
                }
            }

            // Return results to the output boundary
            outputBoundary.presentTopArtists(topArtists);

        }
        catch (IOException excep) {
            outputBoundary.handleError("Failed to fetch top artists: " + excep.getMessage());
        }
    }
}

