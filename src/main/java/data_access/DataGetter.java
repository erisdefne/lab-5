package data_access;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A utility class for making API calls to Spotify and retrieving JSON data.
 */
public class DataGetter {

    /**
     * Makes a GET request to the given URL and parses the response as a JsonNode.
     *
     * @param queryUrl The URL to query.
     * @param token    The authorization token for the Spotify API.
     * @return The JSON response as a JsonNode.
     * @throws IOException If there is an issue with the connection or parsing.
     */
    public static JsonNode getData(String queryUrl, String token) throws IOException {
        // Set up the connection
        URL url = new URL(queryUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + token);

        // Handle response
        int status = connection.getResponseCode();
        if (status != HttpURLConnection.HTTP_OK) {
            throw new IOException("Failed to fetch data. HTTP error: " + status);
        }

        // Read response
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Parse JSON
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(response.toString());
    }
}
