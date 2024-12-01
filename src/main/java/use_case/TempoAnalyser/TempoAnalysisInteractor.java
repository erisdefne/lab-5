package use_case.TempoAnalyser;

import entity.TempoAnalysisEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The interactor class for implementing the Tempo Analysis use case.
 */
public class TempoAnalysisInteractor implements TempoAnalysisInputBoundary {

    @Override
    public Map<String, Integer> analyzeTempos(List<String> trackIds) throws IOException {
        final Map<String, Integer> tempoCategories = new HashMap<>();
        for (String trackId : trackIds) {
            final double tempo = fetchTrackTempo(trackId);
            final String category = TempoAnalysisEntity.categorizeTempo(tempo);
            tempoCategories.put(category, tempoCategories.getOrDefault(category, 0) + 1);
        }
        return tempoCategories;
    }

    private double fetchTrackTempo(String trackId) throws IOException {
        // Mock tempo fetching logic, replace with API or database retrieval
        return Math.random() * 200;
    }
}
