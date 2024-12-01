package use_case.topsongs;

import data_access.DataGetterClass;
import com.fasterxml.jackson.databind.JsonNode;
import entity.CurrentUser;
import interface_adapter.top_songs.TopSongsPresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TopSongsInteractor {
    private static final String Url = "https://api.spotify.com/v1/me/top/tracks";
    private final TopSongsPresenter presenter;

    public TopSongsInteractor(TopSongsPresenter presenter) {
        this.presenter = presenter;
    }

    public void execute(CurrentUser currentUser) {
        try {
            // Fetch the data from Spotify API
            JsonNode data = DataGetterClass.getData(Url + "?limit=20", currentUser); // Limit to top 10 tracks

            // Parse the JSON response and collect songs
            List<Song> songs = new ArrayList<>();
            if (data.has("items")) {
                for (JsonNode track : data.get("items")) {
                    String trackName = track.get("name").asText();
                    String artistName = track.get("artists").get(0).get("name").asText();
                    songs.add(new Song(trackName, artistName));
                }
            }
            // Pass songs to the presenter
            presenter.presentSongs(songs);
        } catch (IOException e) {
            presenter.presentError("Failed to fetch top songs: " + e.getMessage());
        }
    }

    public static class Song {
        private final String name;
        private final String artist;

        public Song(String name, String artist) {
            this.name = name;
            this.artist = artist;
        }

        public String getName() {
            return name;
        }

        public String getArtist() {
            return artist;
        }

        @Override
        public String toString() {
            return name + " by " + artist;
        }
    }
}