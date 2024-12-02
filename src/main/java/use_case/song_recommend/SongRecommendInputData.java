package use_case.song_recommend;

import java.util.List;

/**
 * The Input Data for the Login Use Case.
 */
public class SongRecommendInputData {

    private final String topGenre;
    private final List<String> userToptracks;


    public SongRecommendInputData(String topGenre, List<String> userToptracks) {
        this.topGenre = topGenre;
        this.userToptracks = userToptracks;
    }

    String getTopGenre() {
        return topGenre;
    }

    List<String> getUserToptracks() {
        return userToptracks;
    }

}
