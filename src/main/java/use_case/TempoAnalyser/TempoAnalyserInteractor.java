package use_case.TempoAnalyser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TempoAnalyserInteractor implements TempoAnalyserInputBoundary {

    private final TempoAnalyserOutputBoundary outputBoundary;

    public TempoAnalyserInteractor(TempoAnalyserOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void analyseTempo(List<Double> tempos) {
        Map<String, Integer> tempoCategories = new HashMap<>();
        tempoCategories.put("Slow", 0);
        tempoCategories.put("Moderate", 0);
        tempoCategories.put("Fast", 0);

        for (Double tempo : tempos) {
            if (tempo < 90) {
                tempoCategories.put("Slow", tempoCategories.get("Slow") + 1);
            }
            else if (tempo < 120) {
                tempoCategories.put("Moderate", tempoCategories.get("Moderate") + 1);
            }
            else {
                tempoCategories.put("Fast", tempoCategories.get("Fast") + 1);
            }
        }

        outputBoundary.presentTempoAnalysis(tempoCategories);
    }
}
