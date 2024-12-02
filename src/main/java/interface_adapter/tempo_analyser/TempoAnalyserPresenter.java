package interface_adapter.tempo_analyser;

import use_case.TempoAnalyser.TempoAnalyserOutputBoundary;

import java.util.Map;

public class TempoAnalyserPresenter implements TempoAnalyserOutputBoundary {

    private Map<String, Integer> tempoAnalysis;
    private String errorMessage;

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

    public Map<String, Integer> getTempoAnalysis() {
        return tempoAnalysis;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
