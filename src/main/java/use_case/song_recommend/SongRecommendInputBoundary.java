package use_case.song_recommend;

import use_case.login.LoginInputData;

import java.util.List;

public interface SongRecommendInputBoundary {
    void fetchRecommendedSongs(SongRecommendInputData songRecommendInputData);
}

