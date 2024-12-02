package interface_adapter.tempo_analyser;

import use_case.TempoAnalyser.TempoAnalyserOutputBoundary;

import java.util.Map;

public class TempoAnalyserPresenter implements TempoAnalyserOutputBoundary {
    private String errorMessage;
    private Map<String, Integer> tempoAnalysis;

    @Override
    public void presentTempoAnalysis(Map<String, Integer> analysis) {
        this.errorMessage = null;
        this.tempoAnalysis = analysis;
    }

    @Override
    public void handleError(String error) {
        this.errorMessage = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Map<String, Integer> getTempoAnalysis() {
        return tempoAnalysis;
    }
}
