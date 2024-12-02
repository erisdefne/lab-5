package use_case.top_artists;

import entity.CurrentUser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopArtistsInteractorTest {

    @Test
    void apiFailureTest() {
        CurrentUser currentUser = new CurrentUser();
        currentUser.setAccessToken("invalid_token");

        TestTopArtistsOutputBoundary failurePresenter = new TestTopArtistsOutputBoundary();

        TopArtistsInteractor interactor = new TopArtistsInteractor(failurePresenter, currentUser);

        interactor.fetchTopArtists("long_term", 10);

        assertNull(failurePresenter.receivedArtists);
        assertEquals("Failed to fetch top artists: HTTP Error: 401 Unauthorized", failurePresenter.errorMessage);
    }

    class TestTopArtistsOutputBoundary implements TopArtistsOutputBoundary {
        List<String> receivedArtists = null;
        String errorMessage = null;

        @Override
        public void presentTopArtists(List<String> topArtists) {
            this.receivedArtists = new ArrayList<>(topArtists);
        }

        @Override
        public void handleError(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}

