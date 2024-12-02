package use_case.tempo_analyser;

import java.util.List;

public class TempoAnalyserInputData {
    private final List<String> trackIds;

    public TempoAnalyserInputData(List<String> trackIds) {
        this.trackIds = trackIds;
    }

    public List<String> getTrackIds() {
        return trackIds;
    }
}

