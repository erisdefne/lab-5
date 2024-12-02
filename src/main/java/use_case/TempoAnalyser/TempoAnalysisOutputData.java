package use_case.TempoAnalyser;

public class TempoAnalysisOutputData {
    private final double averageTempo;
    private final double tempoVariance;

    public TempoAnalysisOutputData(double averageTempo, double tempoVariance) {
        this.averageTempo = averageTempo;
        this.tempoVariance = tempoVariance;
    }

    public double getAverageTempo() {
        return averageTempo;
    }

    public double getTempoVariance() {
        return tempoVariance;
    }
}
