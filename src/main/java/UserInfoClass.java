import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoClass {
    private static final String Url = "https://api.spotify.com/v1/me/top/tracks?";

    // Makes an api call to Spotify to get the user's top tracks over the past certain period and
    //@param timerange takes the time of how long in the past you want data for. limit is how many tracks you want to see
    // returns genre of each artist of the tracks in the data returned from Spotify
    public static Map<String, Integer> getTopTrackGenres(String timerange, String limit) throws IOException {
        // Construct the query URL
        JsonNode data = DataGetterClass.getData(Url + "time_range=" + timerange + "&limit=" + limit + "&offset=0");
        List artistIdsList = getArtistIds(data);
        List artistGenre = getArtistGenre(artistIdsList);
        return organizeTracks(artistGenre);
    }

    // return the artist ids list
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

    // returns the genre of all artists of the tracks in a user's top tracks
    public static List<String> getArtistGenre(List<String> trackIdsList) throws IOException {
        String artist_url = "https://api.spotify.com/v1/artists?ids=";
        List<String> genresList = new ArrayList<>();
        String idsString = "";
        for (int i = 1; i < trackIdsList.size() + 1; i++) {
            idsString = idsString + (trackIdsList.get(i-1)) + "%2C";
            if (i%50 == 0 || i == trackIdsList.size() ) {
                StringBuffer sb = new StringBuffer(idsString);
                sb.delete(idsString.length() - 3, idsString.length());
                String queryUrl = artist_url + sb.toString();
                JsonNode data = DataGetterClass.getData(queryUrl).get("artists");
                    for (int j = 0; j < data.size(); j++) {
                        if (data.get(j).get("genres").size() != 0) {
                            for (int k = 0; k < data.get(j).get("genres").size(); k++) {
                                genresList.add(data.get(j).get("genres").get(k).asText()); // Convert JsonNode to String
                            }
                        }
                    }
                idsString = "";
            }
        }
        return genresList;
    }

    static List<String> allGenres = new ArrayList<>()
    {{
        add("pop");
        add("rap");
        add("r&b");
        add("hip hop");
        add("indie");
        add("singer-songwriter");
        add("metal");
        add("house");
        add("techno");
        add("country");
        add("punk");
        add("dance");
        add("classical");
        add("jazz");
        add("edm");
        add("disco");
        add("rock");
        add("soul");
        add("grunge");
    }};


    // returns a condensed version of the genres that the user listens to. Hashmap maps the genre
    // with the number of tracks of that genre
    public static Map<String, Integer> organizeTracks(List<String> genresList) {
        // Temporary map to hold updates
        Map<String, Integer> genreCounts = new HashMap<>();

        for (String genericGenre : allGenres) {
            genreCounts.put(genericGenre, 0);
        }

        for (String genre : genresList) {
            boolean matched = false;
            for (String genericGenre: allGenres) {
                if (genre.contains(genericGenre)) {
                    genreCounts.put(genericGenre, genreCounts.get(genericGenre) +1);
                    matched = true;
                }
            }
            if(!matched){
                genreCounts.put(genre, 1);
            }
        }

        // Apply updates after iteration
        return genreCounts;
    }

    //pop, rap, r&b, hip hop, indie, singer, metal, house, techno, country, punk, dance, classical, jazz, blues, disco, edm, trap

    public static void main(String[] args) throws IOException {
        try {
            System.out.println(getTopTrackGenres("short_term", "50"));
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        try { // Replace with actual token from getToken()
//            List<String> genresList = getTopTrackGenres("short_term", "50");
//            HashMap genresMap = new HashMap();
//            for (int i = 0; i < genresList.size(); i++) {
//                if (genresMap.get(genresList.get(i)) == null) {
//                    genresMap.put(genresList.get(i), 1);
//                }
//                else {
//                    genresMap.replace(genresList.get(i),(int) genresMap.get(genresList.get(i)) + 1);
//                }
//            }
//            System.out.println(organizeTracks(genresList));
//            //System.out.println(genresMap);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

