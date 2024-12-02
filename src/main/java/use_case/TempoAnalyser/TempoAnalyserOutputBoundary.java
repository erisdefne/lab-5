package use_case.TempoAnalyser;

import java.util.Map;

public interface TempoAnalyserOutputBoundary {
    void presentTempoAnalysis(Map<String, Integer> tempoCategories);
    void handleError(String errorMessage);
}

