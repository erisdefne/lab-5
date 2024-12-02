package use_case.song_recommend;

import java.util.List;

public interface SongRecommendInputBoundary {
    void fetchRecommendedSongs(SongRecommendInputData songRecommendInputData);
}

