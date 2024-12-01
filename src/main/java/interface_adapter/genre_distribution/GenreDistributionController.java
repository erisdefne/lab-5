package interface_adapter.genre_distribution;

import entity.CurrentUser;
import use_case.genre_distribution.GenreDistributionInteractor;

public class GenreDistributionController {
    private final GenreDistributionInteractor interactor;

    public GenreDistributionController(GenreDistributionInteractor interactor) {
        this.interactor = interactor;
    }

    public void fetchAndPresentGenreDistribution(CurrentUser currentUser) {
        try {
            interactor.fetchGenreDistribution(currentUser);
        } catch (Exception e) {
            interactor.getPresenter().presentError("Failed to fetch genre data: " + e.getMessage());
        }
    }
}