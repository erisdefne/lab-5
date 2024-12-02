package use_case.compare_playlists;

import java.io.IOException;
import java.util.List;

public interface PlaylistRepository {

    /**
     * Fetches a playlist's ID based on the playlist name and owner.
     * @param playlistName the name of the playlist.
     * @param playlistOwner the owner of the playlist.
     * @return the playlist ID.
     */
    String getPlaylistId(String playlistName, String playlistOwner) throws IOException;

    /**
     * Fetches the genres of a playlist by its ID.
     * @param playlistId the ID of the playlist.
     * @return a list of genres associated with the playlist.
     */
    List<String> getPlaylistGenres(String playlistId) throws IOException;

    // Other methods can include retrieving track information, artists, etc.
}
