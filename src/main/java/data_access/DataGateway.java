package data_access;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.*;

public class DataGateway {
    //public static String Token = "BQCRB4zB6Tqt2GouKfaYlrXhhEiT8sY7V_iI_4AlfuWITkdzWhPv3B7HvliqzJO2k41sompWCIIEBZ7wIi8gNNE4skdsPYAdQTBR0P2fT49QHRYDw9xbycJ585qR66s9b7RUgsfZb18LX1pbSQPmzbjlCTElwGsZvUJXCIMSUDKOzu-OyBgfpStIDO8HD_JHYMHdFDQBAY-41x7CKUVam0wNUaTLhBY4dUI5HPmtuoyBYg";

    public static List<String> fetchUserTopTracks() throws IOException {
        String topTracksUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=short_term&limit=20";
        List<String> userTopTracks = new ArrayList<>();
        JsonNode getter = DataGetterClass.getData(topTracksUrl);

        for (JsonNode track : getter.get("items")) {
            userTopTracks.add(track.get("name").asText());
        }

        return userTopTracks;
    }
}
