package use_case.TempoAnalyser;

import com.fasterxml.jackson.databind.JsonNode;
import data_access.DataGetterClass;
import entity.CurrentUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interactor for analyzing tempo data.
 */
public class TempoAnalyserInteractor implements TempoAnalyserInputBoundary {

    private final TempoAnalyserOutputBoundary outputBoundary;
    private static final String TEMPO_API_URL = "https://api.spotify.com/v1/tracks/";

    public TempoAnalyserInteractor(TempoAnalyserOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void analyseTempo(List<String> trackIds) {
        Map<String, Integer> tempoCategories = new HashMap<>();
        tempoCategories.put("Slow", 0);
        tempoCategories.put("Moderate", 0);
        tempoCategories.put("Fast", 0);

        try {
            for (String trackId : trackIds) {
                // Fetch data for each track using DataGetterClass
                String queryUrl = TEMPO_API_URL + trackId;
                JsonNode trackData = DataGetterClass.getData(queryUrl, CurrentUser.getInstance());

                // Extract tempo information from the API response
                double tempo = trackData.get("tempo").asDouble();

                // Categorize the tempo
                if (tempo < 90) {
                    tempoCategories.put("Slow", tempoCategories.get("Slow") + 1);
                } else if (tempo < 120) {
                    tempoCategories.put("Moderate", tempoCategories.get("Moderate") + 1);
                } else {
                    tempoCategories.put("Fast", tempoCategories.get("Fast") + 1);
                }
            }

            // Present the categorized tempo data
            outputBoundary.presentTempoAnalysis(tempoCategories);

        } catch (IOException e) {
            // Handle errors during the data retrieval process
            outputBoundary.handleError("Failed to retrieve tempo data: " + e.getMessage());
        }
    }

}
