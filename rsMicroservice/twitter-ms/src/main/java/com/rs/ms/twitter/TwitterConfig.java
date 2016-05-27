package com.rs.ms.twitter;

/**
 * Created by babluj on 5/20/16.
 */
public class TwitterConfig {

    private String token;
    private String tokenSecret;

    TwitterConfig(String token, String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    public String getToken() {
        return token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }
}
