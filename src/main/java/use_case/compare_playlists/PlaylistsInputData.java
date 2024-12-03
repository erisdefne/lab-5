package use_case.compare_playlists;

public class PlaylistsInputData {
    private final String playlist1Name;
    private final String playlist1Owner;
    private final String playlist2Name;
    private final String playlist2Owner;

    public PlaylistsInputData(String playlist1Name, String playlist1Owner, String playlist2Name, String playlist2Owner) {
        this.playlist1Name = playlist1Name;
        this.playlist1Owner = playlist1Owner;
        this.playlist2Name = playlist2Name;
        this.playlist2Owner = playlist2Owner;
    }

    public String getPlaylist1Name() {
        return playlist1Name;
    }

    public String getPlaylist1Owner() {
        return playlist1Owner;
    }

    public String getPlaylist2Name() {
        return playlist2Name;
    }

    public String getPlaylist2Owner() {
        return playlist2Owner;
    }
}
