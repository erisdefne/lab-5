package interface_adapter.genre_distribution;

import java.util.Map;

public class GenreDistributionPresenter {
    private final GenreDistributionViewModel viewModel;

    public GenreDistributionPresenter(GenreDistributionViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void presentGenreData(Map<String, Integer> genreData) {
        viewModel.setGenreData(genreData);
        viewModel.setError(null);
    }

    public void presentError(String error) {
        viewModel.setGenreData(null);
        viewModel.setError(error);
    }
}