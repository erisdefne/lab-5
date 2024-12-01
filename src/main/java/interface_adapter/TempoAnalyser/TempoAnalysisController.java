package interface_adapter.TempoAnalyser;

import use_case.TempoAnalyser.TempoAnalysisInputBoundary;

import java.util.List;

public class TempoAnalysisController {
    private final TempoAnalysisInputBoundary interactor;

    public TempoAnalysisController(TempoAnalysisInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void analyseTempos(List<String> trackIds) {
        interactor.analyzeTempos(trackIds);
    }
}
