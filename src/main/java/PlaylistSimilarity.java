//import com.fasterxml.jackson.databind.JsonNode;
//import data_access.DataGetterClass;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import static java.lang.Math.max;
//import static java.lang.Math.min;
//
//public class PlaylistSimilarity {
//    // Class for Defne's use case where the algorithm analyzez two playlists to
//    // figure out how similar they are
//    // either ask for the playlist id or playlist name + user id
//    private static String urlFirstPart = "https://api.spotify.com/v1/playlists/"; //need playlist id in the middle
//    private static String urlSecondPart = "/tracks?fields=items%28track%28artists%28id%29%29%29&limit=100&offset=0";
//
//    public static List getPlaylistGenres(String playlistId) throws IOException {
//        JsonNode data = DataGetterClass.getData(urlFirstPart + playlistId + urlSecondPart);
//        List artistIds = new ArrayList();
//        JsonNode items = data.get("items");
//        for (int i = 0; i < data.get("items").size(); i++) {
//            JsonNode track = items.get(i).get("track");
//            for (int j = 0; j < track.get("artists").size(); j++) {
//                artistIds.add(track.get("artists").get(j).get("id").asText());
//            }
//        }
//        List artistGenreList = UserInfoClass.getArtistGenre(artistIds);
//        return artistGenreList;
//    }
//
//    public static int comparePlaylists(String playlist1Name, String playlist1Owner, String playlist2Name, String playlist2Owner) throws IOException {
//        String playlist1Id = getPlaylistId(playlist1Owner, playlist1Name);
//        String playlist2Id = getPlaylistId(playlist2Owner, playlist2Name);
//        if (playlist1Id == null || playlist2Id == null) {
//            return 0;
//        }
//        List playlist1Genres = getPlaylistGenres(playlist1Id);
//        List playlist2Genres = getPlaylistGenres(playlist2Id);
//        int totalLength = 0;
//        Map<String, Integer> playlist1GenresMap = UserInfoClass.organizeTracks(playlist1Genres);
//        Map<String, Integer> playlist2GenresMap = UserInfoClass.organizeTracks(playlist2Genres);
//        System.out.println(playlist1GenresMap);
//        System.out.println(playlist2GenresMap);
//        // {"pop": 5, "rock": 2}
//        // {"pop": 2, "indie": 8}
//        int totalCommonGenres = 0;
//        String lastAddedGenre = "";
//        for (String playlistKeys1: playlist1GenresMap.keySet()) {
//            for (String playlistKeys2: playlist2GenresMap.keySet()) {
//                if (playlistKeys1.equals(playlistKeys2)) {
//                    totalCommonGenres += min(playlist1GenresMap.get(playlistKeys1), playlist2GenresMap.get(playlistKeys2));
//                    totalLength += max(playlist1GenresMap.get(playlistKeys1), playlist2GenresMap.get(playlistKeys2));
//                    lastAddedGenre = playlistKeys1;
//                    break;
//                }
//            }
//            if (playlistKeys1 != lastAddedGenre)  {
//                totalLength += playlist1GenresMap.get(playlistKeys1);
//            }
//        }
//        double similarityScore = 0;
//        if (totalLength != 0) {
//            similarityScore = ((double) totalCommonGenres / (double) totalLength)*100;
//        }
//        return (int) similarityScore;
//    }
//
//    public static String getPlaylistId(String username, String playlistName) throws IOException {
//        String urlFirstPart = "https://api.spotify.com/v1/search?q=";
//        String urlSecondPart = "&type=playlist&market=20&limit=50&offset=0";
//        String dividedPlaylistName = "";
//        for (String word: playlistName.split(" ")) {
//            dividedPlaylistName += word + "+";
//        }
//        System.out.println(dividedPlaylistName);
//        String foundPlaylistId = "";
//        JsonNode data2 = DataGetterClass.getData(urlFirstPart + dividedPlaylistName + urlSecondPart);
//        JsonNode data3 = data2.get("playlists").get("items");
//        for (int i = 0; i < data3.size(); i++) {
//            JsonNode searchResult = data3.get(i);
//            if (! searchResult.isNull()) {
//                String name =  searchResult.get("owner").get("display_name").asText().trim();
//                String playlist =  searchResult.get("name").asText().trim();
//                if (playlistName.trim().equals(playlist) & username.trim().equals(name)) {
//                    foundPlaylistId = searchResult.get("id").asText();
//                    System.out.println("I found: " + foundPlaylistId);
//                    break;
//                }
//            }
//        }
//        if (foundPlaylistId.isEmpty()) {
//            System.out.println("No playlist found");
//            return null;
//        }
//        return foundPlaylistId;
//    }
//
//
//    public static void main(String[] args) throws IOException {
//        int similarityScore = comparePlaylists("fred astaire by adam brock", "Juliet", "dancing in the kitchen", "Matthew Meyer");
//        System.out.println("The similarity score that I found is " + similarityScore + "%");
//    }
//}
