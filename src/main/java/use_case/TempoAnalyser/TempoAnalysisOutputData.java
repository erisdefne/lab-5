package use_case.TempoAnalyser;

import java.util.Map;

/**
 * Output data class for the Tempo Analysis use case.
 */
public class TempoAnalysisOutputData {

    private final Map<String, Integer> tempoCategories;

    public TempoAnalysisOutputData(Map<String, Integer> tempoCategories) {
        this.tempoCategories = tempoCategories;
    }

    public Map<String, Integer> getTempoCategories() {
        return tempoCategories;
    }
}
