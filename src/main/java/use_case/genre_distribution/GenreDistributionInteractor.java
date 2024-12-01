package use_case.genre_distribution;

import com.fasterxml.jackson.databind.JsonNode;
import data_access.DataGetterClass;
import entity.CurrentUser;
import interface_adapter.genre_distribution.GenreDistributionPresenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GenreDistributionInteractor {
    private static final String URL = "https://api.spotify.com/v1/me/top/tracks?time_range=medium_term";
    private final GenreDistributionPresenter presenter;

    public GenreDistributionInteractor(GenreDistributionPresenter presenter) {
        this.presenter = presenter;
    }

    public void fetchGenreDistribution(CurrentUser currentUser) throws IOException {
        JsonNode data = DataGetterClass.getData(URL, currentUser);
        Map<String, Integer> genreCounts = new HashMap<>();

        if (data.has("items")) {
            for (JsonNode track : data.get("items")) {
                JsonNode genres = track.get("album").get("genres");
                if (genres != null) {
                    for (JsonNode genre : genres) {
                        String genreName = genre.asText();
                        genreCounts.put(genreName, genreCounts.getOrDefault(genreName, 0) + 1);
                    }
                }
            }
        }

        presenter.presentGenreData(genreCounts);
    }

    public GenreDistributionPresenter getPresenter() {
        return presenter;
    }
}