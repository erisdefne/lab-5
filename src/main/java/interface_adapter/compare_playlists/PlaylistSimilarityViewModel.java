package interface_adapter.compare_playlists;

public class PlaylistSimilarityViewModel {

    private int similarityScore;
    private String errorMessage;

    public void setSimilarityScore(int score) {
        this.similarityScore = score;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void firePropertyChanged() {
        // Fire an event to notify the view about the change
        // This could be through listeners or a property change mechanism
    }
}
