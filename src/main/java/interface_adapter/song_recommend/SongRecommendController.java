package interface_adapter.song_recommend;

import use_case.song_recommend.SongRecommendInputBoundary;
import use_case.song_recommend.SongRecommendInputData;

import java.util.List;

public class SongRecommendController {
    private final SongRecommendInputBoundary interactor;

    public SongRecommendController(SongRecommendInputBoundary interactor) {

        this.interactor = interactor;
    }

    public void fetchRecommendSongs(String topGenre, List<String> userTopTracks) {
        final SongRecommendInputData songRecommendInputData = new SongRecommendInputData(topGenre, userTopTracks);
        interactor.fetchRecommendedSongs(songRecommendInputData);
    }

}