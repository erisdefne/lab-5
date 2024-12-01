package entity;

/**
 * Provides methods for categorizing tempo into predefined ranges.
 */
public class TempoAnalysisEntity {
    public static final int SLOW_TEMPO_THRESHOLD = 90;
    public static final int MODERATE_TEMPO_THRESHOLD = 120;

    public static String categorizeTempo(double tempo) {
        if (tempo < SLOW_TEMPO_THRESHOLD) {
            return "Slow";
        }
        else if (tempo <= MODERATE_TEMPO_THRESHOLD) {
            return "Moderate";
        }
        else {
            return "Fast";
        }
    }
}
