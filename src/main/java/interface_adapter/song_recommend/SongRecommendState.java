package interface_adapter.song_recommend;

import java.util.Map;

public class SongRecommendState {
    private Map<String, String> recommendedSongs;
    private String errorMessage;

    public Map<String, String> getRecommendedSongs() {
        return recommendedSongs;
    }

    public void setRecommendedSongs(Map<String, String> recommendedSongs) {
        this.recommendedSongs = recommendedSongs;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
