import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserInfoClass {
    private static final String Url = "https://api.spotify.com/v1/me/top/tracks?";

    public static List getTopTrackGenres(String timerange, String limit) throws IOException {
        // Construct the query URL
        JsonNode data = DataGetterClass.getData(Url + "time_range=" + timerange + "&limit=" + limit + "&offset=0");
        List artistIdsList = getArtistIds(data);
        List artistGenre = getArtistGenre(artistIdsList);
        System.out.println(artistGenre);
        return artistGenre;
    }

    public static List getArtistIds(JsonNode data) {
        JsonNode itemsNode = data.get("items");
        //System.out.println(itemsNode);
        List artistIdsList = new ArrayList();
        for (int i = 0; i < data.get("items").size(); i++) {
            JsonNode item = itemsNode.get(i);
            artistIdsList.add(item.get("artists").get(0).get("id").asText());
        }
        return artistIdsList;
    }

    public static List getArtistGenre(List trackIdsList) throws IOException {
        String artist_url = "https://api.spotify.com/v1/artists/";
        List genresList = new ArrayList();
        for (int i = 0; i < trackIdsList.size(); i++) {
            JsonNode data = DataGetterClass.getData(artist_url + trackIdsList.get(i));
            for (int j = 0; j < data.get("genres").size(); j++) {
                if (data.get("genres").get(j) != null);
                    genresList.add(data.get("genres").get(j));
                }
            }
        return genresList;
    }

    public static void main(String[] args) {
        try { // Replace with actual token from getToken()
            List genresList = getTopTrackGenres("short_term", "50");
            HashMap genresMap = new HashMap();
            for (int i = 0; i < genresList.size(); i++) {
                if (genresMap.get(genresList.get(i)) == null) {
                    genresMap.put(genresList.get(i), 1);
                }
                else {
                    genresMap.replace(genresList.get(i),(int) genresMap.get(genresList.get(i)) + 1);
                    System.out.println("aaa");
                }
            }
            System.out.println(genresMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

