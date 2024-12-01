package interface_adapter.genre_distribution;

import java.util.Map;

public class GenreDistributionViewModel {
    private Map<String, Integer> genreData;
    private String error;

    public Map<String, Integer> getGenreData() {
        return genreData;
    }

    public void setGenreData(Map<String, Integer> genreData) {
        this.genreData = genreData;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}