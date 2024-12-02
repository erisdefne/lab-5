package use_case.song_recommend;

import com.fasterxml.jackson.databind.JsonNode;
import data_access.DataGetterClass;

import entity.CurrentUser;


import java.io.IOException;
import java.util.*;

public class SongRecommendInteractor implements SongRecommendInputBoundary {

    private final SongRecommendOutputBoundary outputBoundary;
    private final CurrentUser currentUser; // Dependency for accessing the token

    //private final String token; // Inject token dynamically


    public SongRecommendInteractor(SongRecommendOutputBoundary outputBoundary, CurrentUser currentUser) {
        this.outputBoundary = outputBoundary;
        this.currentUser = currentUser;
        //this.token = token;

    }

    @Override
    public void fetchRecommendedSongs(SongRecommendInputData songRecommendInputData) {
        final String topGenre = songRecommendInputData.getTopGenre();
        final List<String> userTopTracks = songRecommendInputData.getUserToptracks();
        try {
            Map<String, String> recommendedSongs = new HashMap<>();
            Map<String, String> sameGenreTracks = getAllTracks(topGenre);

            for (String key : sameGenreTracks.keySet()) {
                boolean isPresentInUserTopTracks = false;

                for (String track : userTopTracks) {
                    if (key.equals(track)) {  // Compare values instead of references
                        isPresentInUserTopTracks = true;
                        break;  // Exit inner loop since the track is found
                    }
                }

                if (!isPresentInUserTopTracks) {  // Only add if the track is not in userTopTracks
                    recommendedSongs.put(key, sameGenreTracks.get(key));
                }
            }
            final SongRecommendOutputData songRecommendOutputData = new SongRecommendOutputData(recommendedSongs);
            outputBoundary.presentRecommendedSongs(songRecommendOutputData);

        } catch (IOException e) {
            outputBoundary.handleError("Failed to fetch recommended songs: " + e.getMessage());
        }
    }

    private Map<String, String> getAllTracks(String genre) throws IOException {
        String searchUrl = "https://api.spotify.com/v1/search?q=genre:" + genre + "&type=track&limit=20";
        JsonNode allTracks = DataGetterClass.getData(searchUrl, currentUser);

        Map<String, String> conciseList = new HashMap<>();
        JsonNode items = allTracks.get("tracks").get("items");

        for (JsonNode track : items) {
            String trackName = track.get("name").asText();
            String artistName = track.get("artists").get(0).get("name").asText();
            conciseList.put(trackName, artistName);
        }
        return conciseList;
    }
}
