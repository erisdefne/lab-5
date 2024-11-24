import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataGetterClass {


    public static JsonNode getData(String queryUrl) throws IOException {
        // Construct the query URL
        String token = "BQAEPoEZmn8kMUZQD6pv4_qwZql5qfI4PBCimyvX1l6PGs5Fmd_CScE_4Xzl2psUvjeC2UgJNyuzrheqDLPWaBf1zOsP-v2e6nniGbMzz2paJ7g1RLrBrfkS5diCcf1E3T2-Pv-wU5OJ0AfA-pjg1brGAky-G0DwijbgDshxVTFY8C6Rm_6_GZPyoIo2ldbRXStHGK0H5FMpWg1WNWE1__14sPSEPedkR4I";

        //LoginClass.getAccess_token();
        //String queryUrl = SEARCH_URL + userName (for getting user info)
        //"https://api.spotify.com/v1/"
        //https://api.spotify.com/v1/me

        // Set up the connection
        URL url = new URL(queryUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + token);

        // Handle response
        int status = connection.getResponseCode();
        String message = connection.getResponseMessage();
        System.out.println(message);
        if (status != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP Error: " + status + " " + message);
        }

        // Read the JSON response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        // Parse JSON response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResult = mapper.readTree(response.toString());
        return jsonResult;

        // Print the JSON response
        //System.out.println(jsonResult.toPrettyString());
    }

//    public static void main(String[] args) {
//        System.out.println(LoginClass.getAccess_token());
//        try { // Replace with actual token from getToken()
//            String userName = "me"; // Replace with the user's name
//            if (userName.equals("me")) {
//                searchUser(userName);
//            }
//            else {
//                searchUser("users/" + userName);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
