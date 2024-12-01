package interface_adapter.TempoAnalyser;

import use_case.TempoAnalyser.TempoAnalysisInputBoundary;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The controller class responsible for handling user actions related to tempo analysis.
 */
public class TempoAnalysisController {
    private final TempoAnalysisInputBoundary inputBoundary;

    public TempoAnalysisController(TempoAnalysisInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public Map<String, Integer> handleAnalyzeTempoRequest(List<String> trackIds) {
        try {
            return inputBoundary.analyzeTempos(trackIds);
        }
        catch (IOException ioException) {
            throw new RuntimeException("Error analyzing tempos: " + ioException.getMessage(), ioException);
        }
    }
}

