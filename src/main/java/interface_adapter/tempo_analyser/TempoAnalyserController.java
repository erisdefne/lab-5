package interface_adapter.tempo_analyser;

import use_case.TempoAnalyser.TempoAnalyserInputBoundary;

import java.util.List;

public class TempoAnalyserController {

    private final TempoAnalyserInputBoundary interactor;

    public TempoAnalyserController(TempoAnalyserInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void analyseTempo(List<Double> tempos) {
        interactor.analyseTempo(tempos);
    }
}
