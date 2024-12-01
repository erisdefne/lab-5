package use_case.top_artists;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.CurrentUser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopArtistsInteractorTest {

    @Test
    void apiFailureTest() {
        // Create a test CurrentUser with a mock token
        CurrentUser currentUser = new CurrentUser();
        currentUser.setAccessToken("invalid_token"); // Simulate an invalid token

        // Test presenter to capture the output
        TestTopArtistsOutputBoundary failurePresenter = new TestTopArtistsOutputBoundary();

        // Initialize the interactor with the CurrentUser
        TopArtistsInteractor interactor = new TopArtistsInteractor(failurePresenter, currentUser);

        // Simulate fetching top artists
        interactor.fetchTopArtists("long_term", 10);

        // Verify the presenter received the error message
        assertNull(failurePresenter.receivedArtists); // No artists should be received
        assertEquals("Failed to fetch top artists: HTTP Error: 401 Unauthorized", failurePresenter.errorMessage);
    }

    // Test implementation of the TopArtistsOutputBoundary
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



