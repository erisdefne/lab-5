package use_case.TempoAnalyser;

import java.util.Map;

public interface TempoAnalysisOutputBoundary {
    /**
     * Presents the categorized tempo data.
     *
     * @param tempoCategories A map where keys are tempo categories (e.g., "Slow", "Moderate", "Fast")
     *                        and values are the count of tracks in each category.
     */
    void presentTempoAnalysis(Map<String, Integer> tempoCategories);

    /**
     * Handles errors that occur during tempo analysis.
     *
     * @param errorMessage The error message to display or log.
     */
    void handleError(String errorMessage);
}
