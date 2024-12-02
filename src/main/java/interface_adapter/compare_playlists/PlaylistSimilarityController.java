package interface_adapter.compare_playlists;

import use_case.compare_playlists.PlaylistsInputBoundary;
import use_case.compare_playlists.PlaylistsInputData;

public class PlaylistSimilarityController {

    private final PlaylistsInputBoundary playlistsInputBoundary;
    private final PlaylistSimilarityPresenter playlistSimilarityPresenter;

    public PlaylistSimilarityController(PlaylistsInputBoundary playlistsInputBoundary,
                                        PlaylistSimilarityPresenter playlistSimilarityPresenter) {
        this.playlistsInputBoundary = playlistsInputBoundary;
        this.playlistSimilarityPresenter = playlistSimilarityPresenter;
    }

    public void comparePlaylists(String playlist1Name, String playlist1Owner,
                                 String playlist2Name, String playlist2Owner) {
        System.out.println("Comparing playlists..."); // Debug print to check if method is triggered
        PlaylistsInputData inputData = new PlaylistsInputData(playlist1Name, playlist1Owner, playlist2Name,
                playlist2Owner);

        playlistsInputBoundary.execute(inputData);
    }
}
