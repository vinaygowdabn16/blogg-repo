package com.blogapi.security;

public class JwtAuthResponse {

    private String accessToken;
    private String tokenType="bearer";

    public JwtAuthResponse(String accessToken){
        this.accessToken=accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType){
        this.tokenType=tokenType;
    }

    public String getTokenType(){
        return tokenType;
    }
}
