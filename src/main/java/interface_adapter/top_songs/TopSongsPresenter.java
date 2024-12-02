package interface_adapter.top_songs;

import use_case.topsongs.TopSongsInteractor.Song;

import java.util.List;

public class TopSongsPresenter {
    private final TopSongsViewModel viewModel;

    public TopSongsPresenter(TopSongsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void presentSongs(List<Song> songs) {
        viewModel.setSongs(songs);
    }

    public void presentError(String errorMessage) {
        viewModel.setError(errorMessage);
    }
}