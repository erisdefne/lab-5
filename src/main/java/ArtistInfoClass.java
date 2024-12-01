import data_access.DataGetterClass;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;



//public class ArtistInfoClass{
//
//    private static final String Url = "https://api.spotify.com/v1/search";
//
//    public static void searchArtist(String artistName) throws IOException {
//        // Construct the query URL
//        JsonNode data = DataGetterClass.getData(Url + "?q=" + artistName + "&type=artist&limit=1");
//        System.out.println(data.toPrettyString());
//    }
//    public static void main(String[] args) {
//        try { // Replace with actual token from getToken()
//            String artistName = "tupac"; // Replace with the artist's name
//            searchArtist(artistName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
