package use_case.TempoAnalyser;

import java.util.Map;

public interface TempoAnalyserOutputBoundary {
    void presentTempoAnalysis(Map<String, Integer> analysis);
    void handleError(String error);
}
