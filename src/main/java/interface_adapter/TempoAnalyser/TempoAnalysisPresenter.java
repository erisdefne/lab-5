package interface_adapter.TempoAnalyser;

import use_case.TempoAnalyser.TempoAnalysisOutputBoundary;

import java.util.Map;

public class TempoAnalysisPresenter implements TempoAnalysisOutputBoundary {
    private Map<String, Integer> tempoCategories;
    private String errorMessage;

    @Override
    public void presentTempoAnalysis(Map<String, Integer> tempoCategories) {
        this.tempoCategories = tempoCategories;
        this.errorMessage = null;
    }

    @Override
    public void handleError(String errorMessage) {
        this.errorMessage = errorMessage;
        this.tempoCategories = null;
    }

    public String getResults() {
        if (errorMessage != null) return "Error: " + errorMessage;
        return tempoCategories.toString();
    }
}
