import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import data_access.DataGetterClass;
import entity.CurrentUser;

/**
 * Analyzes the tempo of tracks and categorizes them into predefined ranges.
 */
public class TempoAnalyser {

    // URL for Spotify Audio Features API
    private static final String AUDIO_FEATURES_URL = "https://api.spotify.com/v1/audio-features/";

    // Logger for logging errors and events
    private static final Logger LOGGER = Logger.getLogger(TempoAnalyser.class.getName());

    // Constants for tempo thresholds
    private static final int SLOW_TEMPO_THRESHOLD = 90;
    private static final int MODERATE_TEMPO_THRESHOLD = 120;

    // Categories
    private static final String CATEGORY_SLOW = "Slow (0-90 BPM)";
    private static final String CATEGORY_MODERATE = "Moderate (91-120 BPM)";
    private static final String CATEGORY_FAST = "Fast (121+ BPM)";

    /**
     * Analyzes the tempos of a list of Spotify tracks and categorizes them into three groups:
     * Slow (0-90 BPM), Moderate (91-120 BPM), and Fast (121+ BPM).
     *
     * @param currentUser is the current user
     * @param trackIds List of Spotify track IDs. Cannot be null.
     * @return A map containing tempo categories as keys and their respective counts as values.
     * @throws IllegalArgumentException If the trackIds list is null.
     * @throws IOException If there is an error while making API calls or parsing responses.
     */
    public static Map<String, Integer> analyzeTempos(List<String> trackIds, CurrentUser currentUser) throws IOException {
        if (trackIds == null || trackIds.isEmpty()) {
            throw new IllegalArgumentException("Track IDs list cannot be null or empty.");
        }

        // Initialize the map with tempo categories
        final Map<String, Integer> tempoCategories = new HashMap<>();
        tempoCategories.put(CATEGORY_SLOW, 0);
        tempoCategories.put(CATEGORY_MODERATE, 0);
        tempoCategories.put(CATEGORY_FAST, 0);

        // Analyze each track's tempo and categorize it
        for (String trackId : trackIds) {
            final double tempo = fetchTrackTempo(trackId, currentUser);
            if (tempo <= SLOW_TEMPO_THRESHOLD) {
                tempoCategories.put(CATEGORY_SLOW, tempoCategories.get(CATEGORY_SLOW) + 1);
            }
            else if (tempo <= MODERATE_TEMPO_THRESHOLD) {
                tempoCategories.put(CATEGORY_MODERATE, tempoCategories.get(CATEGORY_MODERATE) + 1);
            }
            else {
                tempoCategories.put(CATEGORY_FAST, tempoCategories.get(CATEGORY_FAST) + 1);
            }
        }

        return tempoCategories;
    }

    /**
     * Fetches the tempo of a single track using its Spotify track ID.
     *
     * @param trackId The Spotify track ID. Cannot be null.
     * @param currentUser The current user object containing the access token.
     * @return The tempo (in BPM) of the track.
     * @throws IllegalArgumentException If the trackId is null.
     * @throws IOException If there is an error while making the API call or parsing the response.
     */
    private static double fetchTrackTempo(String trackId, CurrentUser currentUser) throws IOException {
        if (trackId == null) {
            throw new IllegalArgumentException("Track ID cannot be null.");
        }

        final String queryUrl = AUDIO_FEATURES_URL + trackId;
        final JsonNode data = DataGetterClass.getData(queryUrl, currentUser);

        if (data.has("tempo")) {
            return data.get("tempo").asDouble();
        }
        else {
            throw new IOException("Tempo data not available.");
        }
    }

    /**
     * Main method for testing the TempoAnalyser class.
     * Demonstrates the analysis of tempo categories for a list of Spotify tracks.
     *
     * @param args Command-line arguments (not used in this example).
     */
    public static void main(String[] args) {
        try {
            // Example: Replace with actual Spotify track IDs
            final List<String> trackIds = List.of(
                    "3n3Ppam7vgaVa1iaRUc9Lp",
                    "1p80LdxRV74UKvL8gnD7ky",
                    "7qjzFv4UdY1iWLLx7mPBI3"
            );

            // Create a CurrentUser instance with a valid access token
            final CurrentUser currentUser = new CurrentUser();

            // Call analyzeTempos with trackIds and currentUser
            final Map<String, Integer> tempoData = analyzeTempos(trackIds, currentUser);

            // Print the tempo distribution
            System.out.println("Tempo Distribution:");
            tempoData.forEach((category, count) -> {
                System.out.println(category + ": " + count);
            });
        }
        catch (IOException | IllegalArgumentException exception) {
            System.err.println("An error occurred: " + exception.getMessage());
        }
    }

}
