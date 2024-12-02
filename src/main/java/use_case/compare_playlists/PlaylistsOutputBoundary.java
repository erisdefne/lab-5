package use_case.compare_playlists;

public interface PlaylistsOutputBoundary {
    void prepareSuccessView(PlaylistsOutputData outputData);
    void prepareFailView(String errorMessage);
}
