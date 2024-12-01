import com.fasterxml.jackson.databind.JsonNode;
import data_access.DataGetterClass;

import java.io.IOException;
import java.util.*;


public class SongRecommender {


    public static Map<String, String> recommendedSongs(){
        String topGenre = null;
        Map<String, String> reccomendedSongs = new HashMap<>();
        Map<String, Integer> topTracks;
        {
            try {
                topTracks = UserInfoClass.getTopTrackGenres("medium_term", "20");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(topTracks);
            Iterator<String> iterator = topTracks.keySet().iterator();
            if (iterator.hasNext()) {
                topGenre = iterator.next();
            }
        }
        Map<String, String> sameGenretracks = getAllTracks(topGenre);
        System.out.println(sameGenretracks);
        // Need user top track names for comparison
        List<String> userTopTracks = getUserTopTracks();
        System.out.println(userTopTracks);

        for (String key : sameGenretracks.keySet()) {
            boolean isPresentInUserTopTracks = false;

            for (String track : userTopTracks) {
                if (key.equals(track)) {  // Compare values instead of references
                    isPresentInUserTopTracks = true;
                    break;  // Exit inner loop since the track is found
                }
            }

            if (!isPresentInUserTopTracks) {  // Only add if the track is not in userTopTracks
                reccomendedSongs.put(key, sameGenretracks.get(key));
            }
        }
        return reccomendedSongs;
    }

    // pass in another parameter type that will check if you want artist or track recommendations.
    public static Map<String, String> getAllTracks(String genre) {
        String search_url = "https://api.spotify.com/v1/search";
        Map<String, String> consiceList = new HashMap<>();
       // if(type == "tracks"){
            try {
                JsonNode allTracks = DataGetterClass.getData(search_url + "?q=genre:" + genre + "&type=track&limit=20&offset=0");
//                System.out.println("API Response: " + allTracks.toPrettyString());
                JsonNode items = allTracks.get("tracks").get("items");
//                JSONObject jsonResponse = new JSONObject(allTracks);
//                JSONArray itemsArray = jsonResponse.getJSONObject("tracks").getJSONArray("items");

                System.out.println(items.size());
                for(int i=0; i<items.size(); i++){
                    JsonNode track = items.get(i);
                    String trackName = track.get("name").asText();
                    JsonNode artistsArray = track.get("artists").get(0);
                    String artistName = artistsArray.get("name").asText();
                    consiceList.put(trackName, artistName);
                    }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //System.out.println(consiceList);
//        }else if(type == "artist"){
//            try {
//                alltracks = data_access.DataGetterClass.getData(search_url + "?q=genre%3D%22" + genre + "%22&type=artist&limit=20&offset=0");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            for(int i=0; i<alltracks.get("artists").get("ite ms").size(); i++){
//                JsonNode track = alltracks.get("tracks").get("items").get(i);
//                consiceList.put(track.get("name").asText(), track.get("artist").get(0).get("name").asText());
//            }
        //}


        return consiceList;
    }

    public static List<String> getUserTopTracks() {
        String topTracks_url = "https://api.spotify.com/v1/me/top/tracks?";
        List<String> userToptracks = new ArrayList<>();
        JsonNode getter;
        try {
            getter = DataGetterClass.getData(topTracks_url + "?time_range=medium_term&limit=20");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < getter.get("items").size(); i++){
            JsonNode track = getter.get("items").get(i);
            userToptracks.add(track.get("name").asText());
        }

        return userToptracks;
    }
//
    public static void main(String[] args) throws IOException{
        System.out.println(recommendedSongs());
    }

}
