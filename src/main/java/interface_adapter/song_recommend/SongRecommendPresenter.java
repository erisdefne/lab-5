package interface_adapter.song_recommend;

import interface_adapter.ViewManagerModel;
import use_case.song_recommend.SongRecommendOutputBoundary;
import use_case.song_recommend.SongRecommendOutputData;

import java.util.Map;

/**
 * The Presenter for the Song Recommendation Use Case.
 */
public class SongRecommendPresenter implements SongRecommendOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final SongRecommendViewModel songRecommendViewModel;

    public SongRecommendPresenter(ViewManagerModel viewManagerModel, SongRecommendViewModel songRecommendViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.songRecommendViewModel = songRecommendViewModel;
    }

    @Override
    public void presentRecommendedSongs(SongRecommendOutputData songRecommendOutputData) {
        // Prepare success state
        final SongRecommendState state = songRecommendViewModel.getState();
        state.setRecommendedSongs(songRecommendOutputData.getRecommendedSongs());
        state.setErrorMessage(null);
        songRecommendViewModel.setState(state);
        songRecommendViewModel.firePropertyChanged();

        // Trigger view update
        viewManagerModel.setState(songRecommendViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void handleError(String errorMessage) {
        // Prepare failure state
        final SongRecommendState state = songRecommendViewModel.getState();
        state.setRecommendedSongs(null);
        state.setErrorMessage(errorMessage);
        songRecommendViewModel.setState(state);
        songRecommendViewModel.firePropertyChanged();
    }
}