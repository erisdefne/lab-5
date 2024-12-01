package use_case.TempoAnalyser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The interface for defining the input boundary of the Tempo Analysis use case.
 */
public interface TempoAnalysisInputBoundary {
    Map<String, Integer> analyzeTempos(List<String> trackIds) throws IOException;
}
