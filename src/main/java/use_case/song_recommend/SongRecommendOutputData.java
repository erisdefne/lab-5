package use_case.song_recommend;

import java.util.List;
import java.util.Map;

/**
 * Output data class for the Top Artists use case.
 */
public class SongRecommendOutputData {

    private final Map<String, String> recommendedSongs;

    public SongRecommendOutputData(Map<String, String> recommendedSongs) {
        this.recommendedSongs = recommendedSongs;
    }

    public Map<String, String> getRecommendedSongs() {
        return recommendedSongs;
    }
}
