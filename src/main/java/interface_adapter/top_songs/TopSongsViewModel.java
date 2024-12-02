package interface_adapter.top_songs;

import use_case.topsongs.TopSongsInteractor.Song;

import java.util.List;

public class TopSongsViewModel {
    private List<Song> songs;
    private String error;

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}