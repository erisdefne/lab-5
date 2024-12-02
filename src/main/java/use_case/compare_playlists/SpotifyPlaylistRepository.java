package use_case.compare_playlists;

import data_access.DataGetterClass;
import entity.CurrentUser;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SpotifyPlaylistRepository implements PlaylistRepository {

    private static final String URL_FIRST_PART = "https://api.spotify.com/v1/playlists/";
    private static final String URL_SECOND_PART = "/tracks?fields=items(track(artists(id)))&limit=100&offset=0";
    public static CurrentUser currentUser;

    @Override
    public String getPlaylistId(String playlistName, String playlistOwner) throws IOException {
        String urlFirstPart = "https://api.spotify.com/v1/search?q=";
        String urlSecondPart = "&type=playlist&market=20&limit=50&offset=0";
        String dividedPlaylistName = "";
        for (String word: playlistName.split(" ")) {
            dividedPlaylistName += word + "+";
        }
        System.out.println(dividedPlaylistName);
        String foundPlaylistId = "";
        JsonNode data2 = DataGetterClass.getData(urlFirstPart + dividedPlaylistName + urlSecondPart, currentUser);
        JsonNode data3 = data2.get("playlists").get("items");
        for (int i = 0; i < data3.size(); i++) {
            JsonNode searchResult = data3.get(i);
            if (! searchResult.isNull()) {
                String name =  searchResult.get("owner").get("display_name").asText().trim();
                String playlist =  searchResult.get("name").asText().trim();
                if (playlistName.trim().equals(playlist) & playlistOwner.trim().equals(name)) {
                    foundPlaylistId = searchResult.get("id").asText();
                    System.out.println("I found: " + foundPlaylistId);
                    break;
                }
            }
        }
        if (foundPlaylistId.isEmpty()) {
            System.out.println("No playlist found");
            return null;
        }
        return foundPlaylistId;
    }

    @Override
    public List getPlaylistGenres(String playlistId) throws IOException {
        String urlFirstPart = "https://api.spotify.com/v1/playlists/"; //need playlist id in the middle
        String urlSecondPart = "/tracks?fields=items%28track%28artists%28id%29%29%29&limit=100&offset=0";
        JsonNode data = DataGetterClass.getData(urlFirstPart + playlistId + urlSecondPart, currentUser);
        List artistIds = new ArrayList();
        JsonNode items = data.get("items");
        for (int i = 0; i < data.get("items").size(); i++) {
            JsonNode track = items.get(i).get("track");
            for (int j = 0; j < track.get("artists").size(); j++) {
                artistIds.add(track.get("artists").get(j).get("id").asText());
            }
        }
        List artistGenreList = getArtistGenre(artistIds);
        return artistGenreList;
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

    public static List<String> getArtistGenre(List<String> trackIdsList) throws IOException {
        // This method fetches genres of an artist (you would implement this using an API call)
        String artist_url = "https://api.spotify.com/v1/artists?ids=";
        List<String> genresList = new ArrayList<>();
        String idsString = "";
        for (int i = 1; i < trackIdsList.size() + 1; i++) {
            idsString = idsString + (trackIdsList.get(i-1)) + "%2C";
            if (i%50 == 0 || i == trackIdsList.size() ) {
                StringBuffer sb = new StringBuffer(idsString);
                sb.delete(idsString.length() - 3, idsString.length());
                String queryUrl = artist_url + sb.toString();
                JsonNode data = DataGetterClass.getData(queryUrl, currentUser).get("artists");
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

}

