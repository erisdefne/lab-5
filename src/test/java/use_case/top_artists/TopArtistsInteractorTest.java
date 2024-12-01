//package use_case.top_artists;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class TopArtistsInteractorTest {
//
//    @Test
//    void apiFailureTest() {
//        // Simulate the DataGetter behavior throwing an exception
//        TestDataGetterFailure dataGetter = new TestDataGetterFailure();
//        TestTopArtistsOutputBoundary failurePresenter = new TestTopArtistsOutputBoundary();
//
//        // Initialize the interactor with a test token
//        TopArtistsInteractor interactor = new TopArtistsInteractor(failurePresenter,);
//
//        // Simulate fetching top artists
//        interactor.fetchTopArtists("long_term", 10);
//
//        // Verify the presenter received the error message
//        assertNull(failurePresenter.receivedArtists); // No artists should be received
//        assertEquals("Failed to fetch top artists: Failed to fetch data. HTTP error: 401", failurePresenter.errorMessage);
//    }
//
//
//
//    class TestDataGetterEmpty {
//        JsonNode getData(String url, String token) {
//            ObjectMapper mapper = new ObjectMapper();
//            try {
//                // Simulate an empty JSON response
//                String jsonResponse = "{"
//                        + "\"items\": []"
//                        + "}";
//                return mapper.readTree(jsonResponse);
//            } catch (Exception e) {
//                throw new RuntimeException("Error parsing mock JSON response");
//            }
//        }
//    }
//
//
//    // Test implementation of DataGetter that simulates a failure
//    class TestDataGetterFailure {
//        JsonNode getData(String url, String token) {
//            throw new RuntimeException("API failure");
//        }
//    }
//
//    // Test implementation of the TopArtistsOutputBoundary
//    class TestTopArtistsOutputBoundary implements TopArtistsOutputBoundary {
//        List<String> receivedArtists = null;
//        String errorMessage = null;
//
//        @Override
//        public void presentTopArtists(List<String> topArtists) {
//            this.receivedArtists = new ArrayList<>(topArtists);
//        }
//
//        @Override
//        public void handleError(String errorMessage) {
//            this.errorMessage = errorMessage;
//        }
//    }
//}


