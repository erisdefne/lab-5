package use_case.TempoAnalyser;

import com.fasterxml.jackson.databind.JsonNode;
import data_access.DataGetterClass;
import entity.CurrentUser;

import java.io.IOException;

public class TempoAnalyserFetcher {
    private static final String AUDIO_FEATURES_URL = "https://api.spotify.com/v1/audio-features/";

    public double getTempo(String trackId, CurrentUser currentUser) throws IOException {
        final String url = AUDIO_FEATURES_URL + trackId;
        final JsonNode data = DataGetterClass.getData(url, currentUser);

        if (data.has("tempo")) {
            return data.get("tempo").asDouble();
        }
        else {
            throw new IOException("Tempo data not available for track ID: " + trackId);
        }
    }
}
