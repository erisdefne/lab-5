package use_case.TempoAnalyser;

import entity.CurrentUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TempoAnalysisInteractor implements TempoAnalysisInputBoundary {
    private final TempoAnalyserFetcher fetcher;
    private final TempoAnalysisOutputBoundary outputBoundary;

    public TempoAnalysisInteractor(TempoAnalyserFetcher fetcher, TempoAnalysisOutputBoundary outputBoundary) {
        this.fetcher = fetcher;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void analyzeTempos(List<String> trackIds) {
        Map<String, Integer> categories = new HashMap<>();
        categories.put("Slow", 0);
        categories.put("Moderate", 0);
        categories.put("Fast", 0);

        try {
            for (String trackId : trackIds) {
                double tempo = fetcher.getTempo(trackId, CurrentUser.getInstance());
                if (tempo <= 90) categories.put("Slow", categories.get("Slow") + 1);
                else if (tempo <= 120) categories.put("Moderate", categories.get("Moderate") + 1);
                else categories.put("Fast", categories.get("Fast") + 1);
            }
            outputBoundary.presentTempoAnalysis(categories);
        }
        catch (Exception e) {
            outputBoundary.handleError("Error analyzing tempos: " + e.getMessage());
        }
    }
}
