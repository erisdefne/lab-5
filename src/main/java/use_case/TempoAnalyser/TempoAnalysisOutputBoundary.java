package use_case.TempoAnalyser;

import java.util.Map;

public interface TempoAnalysisOutputBoundary {
    void presentTempoAnalysis(Map<String, Integer> tempoCategories);

    void handleError(String errorMessage);
}
