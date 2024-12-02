package entity;

public class CurrentUser {
    private static String accessToken;
    private static String topSongs;
    private static CurrentUser instance;

    public static String getAccessToken() {
        return accessToken;
    }

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public void setAccessToken(String accessToken) {
        CurrentUser.accessToken = accessToken;
    }
}