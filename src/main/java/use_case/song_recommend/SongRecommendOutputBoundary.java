package use_case.song_recommend;

import java.util.Map;

public interface SongRecommendOutputBoundary {
    void presentRecommendedSongs(SongRecommendOutputData songRecommendOutputData);
    void handleError(String errorMessage);
}
