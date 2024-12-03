package use_case.compare_playlists;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ComparePlaylistInteractor implements PlaylistsInputBoundary {

    private final SpotifyPlaylistRepository playlistRepository;
    private final PlaylistsOutputBoundary playlistsOutputBoundary;

    public ComparePlaylistInteractor(SpotifyPlaylistRepository playlistRepository, PlaylistsOutputBoundary playlistsOutputBoundary) {
        this.playlistRepository = playlistRepository;
        this.playlistsOutputBoundary = playlistsOutputBoundary;
    }

    @Override
    public void execute(PlaylistsInputData inputData) {
        try {
            // Perform the playlist comparison logic
            String playlist1Id = playlistRepository.getPlaylistId(inputData.getPlaylist1Name(), inputData.getPlaylist1Owner());
            String playlist2Id = playlistRepository.getPlaylistId(inputData.getPlaylist2Name(), inputData.getPlaylist2Owner());

            if (playlist1Id == null || playlist2Id == null) {
                playlistsOutputBoundary.prepareFailView("One of the playlists was not found.");
                return;
            }

            // Do the actual comparison (simplified for this example)
            int similarityScore = comparePlaylists(playlist1Id, playlist2Id);

            // Send the result to the Presenter
            playlistsOutputBoundary.prepareSuccessView(new PlaylistsOutputData(similarityScore));

        } catch (Exception e) {
            playlistsOutputBoundary.prepareFailView("Error comparing playlists: " + e.getMessage());
        }
    }

    // Example playlist comparison method
    private int comparePlaylists(String playlist1Id, String playlist2Id) throws IOException {
        List playlist1Genres = playlistRepository.getPlaylistGenres(playlist1Id);
        List playlist2Genres = playlistRepository.getPlaylistGenres(playlist2Id);
        int totalLength = 0;
        Map<String, Integer> playlist1GenresMap = playlistRepository.organizeTracks(playlist1Genres);
        Map<String, Integer> playlist2GenresMap = playlistRepository.organizeTracks(playlist2Genres);
        System.out.println(playlist1GenresMap);
        System.out.println(playlist2GenresMap);
        // {"pop": 5, "rock": 2}
        // {"pop": 2, "indie": 8}
        int totalCommonGenres = 0;
        String lastAddedGenre = "";
        for (String playlistKeys1: playlist1GenresMap.keySet()) {
            for (String playlistKeys2: playlist2GenresMap.keySet()) {
                if (playlistKeys1.equals(playlistKeys2)) {
                    totalCommonGenres += min(playlist1GenresMap.get(playlistKeys1), playlist2GenresMap.get(playlistKeys2));
                    totalLength += max(playlist1GenresMap.get(playlistKeys1), playlist2GenresMap.get(playlistKeys2));
                    lastAddedGenre = playlistKeys1;
                    break;
                }
            }
            if (playlistKeys1 != lastAddedGenre)  {
                totalLength += playlist1GenresMap.get(playlistKeys1);
            }
        }
        double similarityScore = 0;
        if (totalLength != 0) {
            similarityScore = ((double) totalCommonGenres / (double) totalLength)*100;
        }
        return (int) similarityScore;
    }

}
