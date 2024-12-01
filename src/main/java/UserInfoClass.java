//import com.fasterxml.jackson.databind.JsonNode;
//import data_access.DataGetterClass;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class UserInfoClass {
//    private static final String Url = "https://api.spotify.com/v1/me/top/tracks?";
//
//    // Makes an api call to Spotify to get the user's top tracks over the past certain period and
//    //@param timerange takes the time of how long in the past you want data for. limit is how many tracks you want to see
//    // returns genre of each artist of the tracks in the data returned from Spotify
//    public static Map<String, Integer> getTopTrackGenres(String timerange, String limit) throws IOException {
//        // Construct the query URL
//        JsonNode data = DataGetterClass.getData(Url + "time_range=" + timerange + "&limit=" + limit + "&offset=0");
//        List artistIdsList = getArtistIds(data);
//        List artistGenre = getArtistGenre(artistIdsList);
//        return organizeTracks(artistGenre);
//    }
//
//    // return the artist ids list
//    public static List getArtistIds(JsonNode data) {
//        JsonNode itemsNode = data.get("items");
//        //System.out.println(itemsNode);
//        List artistIdsList = new ArrayList();
//        for (int i = 0; i < data.get("items").size(); i++) {
//            JsonNode item = itemsNode.get(i);
//            artistIdsList.add(item.get("artists").get(0).get("id").asText());
//        }
//        return artistIdsList;
//    }
//
//    // returns the genre of all artists of the tracks in a user's top tracks
//    public static List<String> getArtistGenre(List<String> trackIdsList) throws IOException {
//        String artist_url = "https://api.spotify.com/v1/artists/";
//        List<String> genresList = new ArrayList<>();
//        for (int i = 0; i < trackIdsList.size(); i++) {
//            JsonNode data = DataGetterClass.getData(artist_url + trackIdsList.get(i));
//            for (int j = 0; j < data.get("genres").size(); j++) {
//                if (data.get("genres").get(j) != null) {
//                    genresList.add(data.get("genres").get(j).asText()); // Convert JsonNode to String
//                }
//            }
//        }
//        return genresList;
//    }
//
//    static List<String> allGenres = new ArrayList<>()
//    {{
//        add("pop");
//        add("rap");
//        add("r&b");
//        add("hip hop");
//        add("indie");
//        add("singer-songwriter");
//        add("metal");
//        add("house");
//        add("techno");
//        add("country");
//        add("punk");
//        add("dance");
//        add("classical");
//        add("jazz");
//        add("edm");
//        add("disco");
//        add("rock");
//        add("soul");
//        add("grunge");
//    }};
//
//
//    // returns a condensed version of the genres that the user listens to. Hashmap maps the genre
//    // with the number of tracks of that genre
//    public static Map<String, Integer> organizeTracks(List<String> genresList) {
//        // Temporary map to hold updates
//        Map<String, Integer> genreCounts = new HashMap<>();
//
//        for (String genericGenre : allGenres) {
//            genreCounts.put(genericGenre, 0);
//        }
//
//        for (String genre : genresList) {
//            boolean matched = false;
//            for (String genericGenre: allGenres) {
//                if (genre.contains(genericGenre)) {
//                    genreCounts.put(genericGenre, genreCounts.get(genericGenre) +1);
//                    matched = true;
//                }
//            }
//            if(!matched){
//                genreCounts.put(genre, 1);
//            }
//        }
//
//        // Apply updates after iteration
//        return genreCounts;
//    }

    //pop, rap, r&b, hip hop, indie, singer, metal, house, techno, country, punk, dance, classical, jazz, blues, disco, edm, trap
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
//}

