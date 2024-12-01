package entity;

public class CurrentUser {
    private static CurrentUser instance;
    private String accessToken;

    private CurrentUser() {
        // Private constructor to prevent instantiation
    }

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
