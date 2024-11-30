package use_case.top_artists;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data_access.DataGetter;


/**
 * Use case class for fetching the user's top artists.
 */
public class TopArtistsFetcher {

    private static final String TOP_ARTISTS_URL = "https://api.spotify.com/v1/me/top/artists";

    /**
     * Fetches the user's top artists for the given time range and limit.
     * @param timeRange The time range (short_term, medium_term, or long_term).
     * @param limit The number of top artists to fetch.
     * @param token The Spotify API access token.
     * @return A list of artist names in ranked order.
     * @throws IOException If there is an issue fetching or parsing data.
     */
    public static List<String> getTopArtists(String timeRange, int limit, String token) throws IOException {
        // Build the API URL with parameters
        String url = TOP_ARTISTS_URL + "?time_range=" + timeRange + "&limit=" + limit;

        // Fetch data from Spotify API
        JsonNode data = DataGetter.getData(url, token);

        // Parse and rank the top artists
        List<String> topArtists = new ArrayList<>();
        JsonNode items = data.get("items");
        if (items != null) {
            for (JsonNode artist : items) {
                topArtists.add(artist.get("name").asText());
            }
        }
        return topArtists;
    }
}


