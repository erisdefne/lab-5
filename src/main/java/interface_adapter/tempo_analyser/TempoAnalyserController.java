package interface_adapter.tempo_analyser;

import use_case.TempoAnalyser.TempoAnalyserInputBoundary;

public class TempoAnalyserController {
    private final TempoAnalyserInputBoundary interactor;

    public TempoAnalyserController(TempoAnalyserInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void analyseTempo(String timeRange, int limit) {
        interactor.analyseTempo(timeRange, limit);
    }
}
