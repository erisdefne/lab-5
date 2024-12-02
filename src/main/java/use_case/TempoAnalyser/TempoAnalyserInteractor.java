package use_case.TempoAnalyser;

import com.fasterxml.jackson.databind.JsonNode;
import data_access.DataGetterClass;
import entity.CurrentUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TempoAnalyserInteractor implements TempoAnalyserInputBoundary {
    private final TempoAnalyserOutputBoundary presenter;
    private static final String TEMPO_API_URL = "https://api.spotify.com/v1/tracks/";

    public TempoAnalyserInteractor(TempoAnalyserOutputBoundary presenter) {
        this.presenter = presenter;
    }


    @Override
    public void analyseTempo(String timeRange, int limit) {
        Map<String, Integer> tempoCategories = new HashMap<>();
        tempoCategories.put("Slow", 0);
        tempoCategories.put("Moderate", 0);
        tempoCategories.put("Fast", 0);

        try {
            // Fetch user's top tracks
            String topTracksUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=" + timeRange + "&limit=" + limit;
            JsonNode tracksData = DataGetterClass.getData(topTracksUrl, CurrentUser.getInstance());
            System.out.println("Tracks Data: " + tracksData);

            // Extract track items
            JsonNode items = tracksData.get("items");
            if (items == null || !items.isArray() || items.isEmpty()) {
                System.out.println("No tracks found.");
                presenter.presentTempoAnalysis(tempoCategories);
                return;
            }

            // Analyze tempo for each track
            for (JsonNode item : items) {
                String trackId = item.get("id").asText();
                String queryUrl = TEMPO_API_URL + trackId;
                JsonNode trackData = DataGetterClass.getData(queryUrl, CurrentUser.getInstance());

                // Check if tempo data exists
                if (trackData != null && trackData.has("tempo")) {
                    double tempo = trackData.get("tempo").asDouble();

                    // Categorize tempo
                    if (tempo < 90) {
                        tempoCategories.put("Slow", tempoCategories.get("Slow") + 1);
                    } else if (tempo < 120) {
                        tempoCategories.put("Moderate", tempoCategories.get("Moderate") + 1);
                    } else {
                        tempoCategories.put("Fast", tempoCategories.get("Fast") + 1);
                    }
                } else {
                    System.out.println("Tempo data missing for track: " + trackId);
                }
            }

            presenter.presentTempoAnalysis(tempoCategories);

        } catch (IOException e) {
            presenter.handleError("Failed to retrieve tempo data: " + e.getMessage());
        }
    }

}