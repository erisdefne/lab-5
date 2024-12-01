package use_case.tempo_analyser;

import java.util.Map;

public class TempoAnalyserOutputData {
    private final Map<String, Integer> tempoCategories;

    public TempoAnalyserOutputData(Map<String, Integer> tempoCategories) {
        this.tempoCategories = tempoCategories;
    }

    public Map<String, Integer> getTempoCategories() {
        return tempoCategories;
    }
}
