package use_case.top_artists;

import com.fasterxml.jackson.databind.JsonNode;
import data_access.DataGetterClass;
import entity.CurrentUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Use case class for fetching the user's top artists.
 */
public class TopArtistsFetcher {

    private static final String TOP_ARTISTS_URL = "https://api.spotify.com/v1/me/top/artists";

    /**
     * Fetches the user's top artists for the given time range and limit.
     * @param timeRange The time range (short_term, medium_term, or long_term).
     * @param limit The number of top artists to fetch.
     * @param currentUser The CurrentUser instance to retrieve the access token.
     * @return A list of artist names in ranked order.
     * @throws IOException If there is an issue fetching or parsing data.
     */
    public static List<String> getTopArtists(String timeRange, int limit, CurrentUser currentUser) throws IOException {
        // Build the API URL with parameters
        final String url = TOP_ARTISTS_URL + "?time_range=" + timeRange + "&limit=" + limit;

        // Fetch data from Spotify API using the new DataGetterClass
        final JsonNode data = DataGetterClass.getData(url, currentUser);

        // Parse and rank the top artists
        final List<String> topArtists = new ArrayList<>();
        final JsonNode items = data.get("items");
        if (items != null) {
            for (JsonNode artist : items) {
                topArtists.add(artist.get("name").asText());
            }
        }
        return topArtists;
    }
}


