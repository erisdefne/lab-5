package use_case.genre_distribution;

import com.fasterxml.jackson.databind.JsonNode;
import data_access.DataGetterClass;
import entity.CurrentUser;
import interface_adapter.genre_distribution.GenreDistributionPresenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GenreDistributionInteractor {
    private final GenreDistributionPresenter presenter;

    private static final String TOP_TRACKS_URL = "https://api.spotify.com/v1/me/top/tracks?time_range=medium_term&limit=50";
    private static final String ARTIST_URL = "https://api.spotify.com/v1/artists/";


    public GenreDistributionInteractor(GenreDistributionPresenter presenter) {
        this.presenter = presenter;
    }

    public void fetchGenreDistribution(CurrentUser currentUser) throws IOException {
        Map<String, Integer> genreCounts = new HashMap<>();
        Set<String> processedArtists = new HashSet<>();

        // Fetch top tracks
        JsonNode topTracks = DataGetterClass.getData(TOP_TRACKS_URL, currentUser);

        if (topTracks.has("items")) {
            for (JsonNode track : topTracks.get("items")) {
                JsonNode artist = track.get("artists").get(0); // Get the first artist of the track
                String artistId = artist.get("id").asText();

                // Avoid duplicate API calls for the same artist
                if (!processedArtists.contains(artistId)) {
                    processedArtists.add(artistId);

                    // Fetch artist details to get genres
                    JsonNode artistDetails = DataGetterClass.getData(ARTIST_URL + artistId, currentUser);
                    if (artistDetails.has("genres")) {
                        for (JsonNode genre : artistDetails.get("genres")) {
                            String genreName = genre.asText();
                            genreCounts.put(genreName, genreCounts.getOrDefault(genreName, 0) + 1);
                        }
                    }
                }
            }
        }
        presenter.presentGenreData(genreCounts);
    }

    public GenreDistributionPresenter getPresenter() {
        return presenter;
    }
}