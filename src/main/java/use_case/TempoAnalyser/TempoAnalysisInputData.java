package use_case.TempoAnalyser;

public class TempoAnalysisInputData {
    private final double[] beatDurations;

    public TempoAnalysisInputData(double[] beatDurations) {
        this.beatDurations = beatDurations;
    }

    public double[] getBeatDurations() {
        return beatDurations;
    }
}

