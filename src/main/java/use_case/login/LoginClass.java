package use_case.login;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginClass {

    private static final String CLIENT_ID = "16b096a1941b433cbb6e44973a7fc00c"; // Replace with actual client ID
    private static final String REDIRECT_URI = "http://localhost:3000/callback"; // Replace with actual redirect URI
    private static final String AUTHORIZATION_ENDPOINT = "https://accounts.spotify.com/authorize";
    private static final String TOKEN_ENDPOINT = "https://accounts.spotify.com/api/token";
    private static final String SCOPE = "user-read-private user-read-email";

    private static String codeVerifier;
    public static String access_token;

    public LoginClass() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                initializeLoginFlow();
            } catch (Exception e) {
                System.err.println("Error during login flow: " + e.getMessage());
            } finally {
                executorService.shutdown();
            }
        });
    }

    private void initializeLoginFlow() throws Exception {
        serverSetup.main(new String[]{}); // Start the server
        redirectToSpotifyAuthorize();

        while (getStoredToken("access_token") == null) {
            Thread.sleep(500); // Wait until the token is retrieved
        }

        access_token = getStoredToken("access_token");
        System.out.println("Access Token Retrieved: " + access_token);
    }

    // Redirects user to Spotify authorization page
    private static void redirectToSpotifyAuthorize() throws Exception {
        codeVerifier = generateCodeVerifier();
        String codeChallenge = generateCodeChallenge(codeVerifier);

        String authUrl = AUTHORIZATION_ENDPOINT + "?response_type=code"
                + "&client_id=" + CLIENT_ID
                + "&scope=" + URLEncoder.encode(SCOPE, "UTF-8")
                + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8")
                + "&code_challenge_method=S256"
                + "&code_challenge=" + codeChallenge;

        storeToken("code_verifier", codeVerifier);

        System.out.println("Visit this URL to log in: " + authUrl);
        openWebPage(authUrl);
    }

    private static void openWebPage(String urlString) {
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI(urlString));
        } catch (Exception e) {
            System.err.println("Failed to open browser: " + e.getMessage());
        }
    }

    // Gets token with the authorization code
    public static void getToken(String code) throws Exception {
        String codeVerifier = getStoredToken("code_verifier");

        String params = "client_id=" + CLIENT_ID
                + "&grant_type=authorization_code"
                + "&code=" + code
                + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8")
                + "&code_verifier=" + codeVerifier;

        HttpURLConnection connection = (HttpURLConnection) new URL(TOKEN_ENDPOINT).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(params.getBytes(StandardCharsets.UTF_8));
        }

        if (connection.getResponseCode() == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            access_token = jsonResponse.getString("access_token"); // Update the instance variable
            storeToken("access_token", access_token); // Persist the access token

            System.out.println("Access Token Retrieved: " + access_token);
        } else {
            System.err.println("Failed to retrieve access token. Response Code: " + connection.getResponseCode());
        }
    }

    // Generates a random code verifier
    private static String generateCodeVerifier() {
        SecureRandom random = new SecureRandom();
        byte[] code = new byte[32];
        random.nextBytes(code);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(code);
    }

    // Generates a SHA-256 code challenge based on the code verifier
    private static String generateCodeChallenge(String verifier) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(verifier.getBytes(StandardCharsets.UTF_8));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
    }

    // Stores token data in a properties file
    private static void storeToken(String key, String value) {
        try (FileOutputStream fos = new FileOutputStream("tokens.properties")) {
            Properties props = new Properties();
            props.setProperty(key, value);
            props.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Retrieves stored token data from properties file
    private static String getStoredToken(String key) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("tokens.properties")) {
            props.load(fis);
            return props.getProperty(key);
        } catch (IOException e) {
            return null;
        }
    }

    public static String getAccess_token() {
        System.out.println("We did it!  " + access_token);
        return access_token;
    }
}