package use_case.compare_playlists;

public class PlaylistsOutputData {
    private final int similarityScore;

    public PlaylistsOutputData(int similarityScore) {
        this.similarityScore = similarityScore;
    }

    public int getSimilarityScore() {
        return similarityScore;
    }
}
