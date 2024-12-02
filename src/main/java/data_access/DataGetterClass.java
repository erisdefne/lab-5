package data_access;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.CurrentUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataGetterClass {


    public static JsonNode getData(String queryUrl, CurrentUser currentUser) throws IOException {
        // Construct the query URL
        final String token = currentUser.getAccessToken();

        //LoginClass.getAccess_token();
        //String queryUrl = SEARCH_URL + userName (for getting user info)
        //"https://api.spotify.com/v1/"
        //https://api.spotify.com/v1/me

        // Set up the connection
        URL url = new URL(queryUrl); //queryUrl
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + token);

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
//        try { // Replace with actual token from getToken()
//            System.out.println(getData("https://api.spotify.com/v1/artists?ids=5rWU8Vm32I8BJtPtrY5JC7%2C20wkVLutqVOYrc0kxFs7rA%2C5069JTmv5ZDyPeZaCCXiCg%2C3fMbdgg4jU18AjLCKBhRSm%2C1rg7cCiRd1SEUz8zGwOnM2%2C2CIMQHirSU0MQqyYHq0eOx"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
