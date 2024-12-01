package use_case.top_artists;

import java.util.List;

/**
 * Output data class for the Top Artists use case.
 */
public class TopArtistsOutputData {

    private final List<String> topArtists;

    public TopArtistsOutputData(List<String> topArtists) {
        this.topArtists = topArtists;
    }

    public List<String> getTopArtists() {
        return topArtists;
    }
}

