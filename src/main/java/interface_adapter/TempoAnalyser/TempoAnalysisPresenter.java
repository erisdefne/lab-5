package interface_adapter.TempoAnalyser;

import java.util.Map;
import java.util.StringJoiner;

/**
 * The presenter class responsible for preparing and formatting the tempo analysis results.
 */
public class TempoAnalysisPresenter {
    private Map<String, Integer> tempoResults;
    private String errorMessage;

    public void present(Map<String, Integer> tempoResults) {
        this.tempoResults = tempoResults;
        this.errorMessage = null;
    }

    public void setErrorMessage(String errorMessage) {
        this.tempoResults = null;
        this.errorMessage = errorMessage;
    }

    public String getFormattedOutput() {
        if (errorMessage != null) {
            return "Error: " + errorMessage;
        }
        final StringJoiner output = new StringJoiner("\n");
        output.add("Tempo Analysis Results:");
        for (Map.Entry<String, Integer> entry : tempoResults.entrySet()) {
            output.add(entry.getKey() + ": " + entry.getValue());
        }
        return output.toString();
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
