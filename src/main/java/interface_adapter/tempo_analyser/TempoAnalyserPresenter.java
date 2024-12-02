package interface_adapter.tempo_analyser;

import use_case.TempoAnalyser.TempoAnalyserOutputBoundary;

import java.util.Map;

public class TempoAnalyserPresenter implements TempoAnalyserOutputBoundary {
    private String errorMessage;
    private Map<String, Integer> tempoAnalysis;

    @Override
    public void presentTempoAnalysis(Map<String, Integer> tempoCategories) {
        this.tempoAnalysis = tempoCategories;
        this.errorMessage = null;
    }

    @Override
    public void handleError(String errorMessage) {
        this.errorMessage = errorMessage;
        this.tempoAnalysis = null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Map<String, Integer> getTempoAnalysis() {
        return tempoAnalysis;
    }
}
