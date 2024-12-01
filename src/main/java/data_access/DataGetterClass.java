package data_access;

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
        String Token = "BQByOEUh6cwcK6P96sT_wFdrMqMGqMH0CEXycNkXsyAKHeZxFSlo9UNt-urG-_iIGgX1yQpqozY22FS2AVoh7E4UND5ASOvbpzjcmnCAFspngnjhKyGAIdvy-3PSOJXs2s1dj5wwckor2NxTd5cZCIVK1mGJwcSR1t_0HeCuZx-ubxliwgYwLcc0pcxAk_EQhKzNW0GmGrVjSkMQm_Lds0vK3bl-Pqc22oBF4vwJcjcOuw";


        //LoginClass.getAccess_token();
        //String queryUrl = SEARCH_URL + userName (for getting user info)
        //"https://api.spotify.com/v1/"
        //https://api.spotify.com/v1/me

        // Set up the connection
        URL url = new URL(queryUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + Token);

        // Handle response
        int status = connection.getResponseCode();
        String message = connection.getResponseMessage();
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
