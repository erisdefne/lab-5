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
            String topTracksUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=" + timeRange + "&limit=" + limit;
            JsonNode tracksData = DataGetterClass.getData(topTracksUrl, CurrentUser.getInstance());

            JsonNode items = tracksData.get("items");
            if (items == null || !items.isArray() || items.isEmpty()) {
                setDefaultTempoValues(tempoCategories);
                presenter.presentTempoAnalysis(tempoCategories);
                return;
            }

            for (JsonNode item : items) {
                String trackId = item.get("id").asText();
                String queryUrl = TEMPO_API_URL + trackId;
                JsonNode trackData = DataGetterClass.getData(queryUrl, CurrentUser.getInstance());

                double tempo = trackData.get("tempo").asDouble();
                if (tempo < 90) {
                    tempoCategories.put("Slow", tempoCategories.get("Slow") + 1);
                } else if (tempo < 120) {
                    tempoCategories.put("Moderate", tempoCategories.get("Moderate") + 1);
                } else {
                    tempoCategories.put("Fast", tempoCategories.get("Fast") + 1);
                }
            }

            presenter.presentTempoAnalysis(tempoCategories);

        } catch (IOException e) {
            setDefaultTempoValues(tempoCategories);
            presenter.handleError("Failed to retrieve tempo data: " + e.getMessage());
        }
    }

    private void setDefaultTempoValues(Map<String, Integer> tempoCategories) {
        tempoCategories.put("Slow", 8);
        tempoCategories.put("Moderate", 5);
        tempoCategories.put("Fast", 4);
    }
}
