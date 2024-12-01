package interface_adapter.top_songs;

import entity.CurrentUser;
import use_case.topsongs.TopSongsInteractor;

public class TopSongsController {
    private final TopSongsInteractor topSongsInteractor;

    public TopSongsController(TopSongsInteractor topSongsInteractor) {
        this.topSongsInteractor = topSongsInteractor;
    }

    /**
     * Executes the fetching of top songs using the provided CurrentUser.
     *
     * @param currentUser the user whose top songs are being fetched
     */
    public void execute(CurrentUser currentUser) {
        topSongsInteractor.execute(currentUser);
    }
}