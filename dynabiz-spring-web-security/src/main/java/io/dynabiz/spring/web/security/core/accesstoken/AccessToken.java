package io.dynabiz.spring.web.security.core.accesstoken;

import java.util.Set;

public class AccessToken implements Comparable<AccessToken> {
    public AccessToken(){}

    private String accessToken;

    private String refreshToken;

    private long accessTokenExpireIn;

    private long refreshTokenExpireIn;

    private String appType;

    private String appVersion;

    private String role;

    private Set<String> permissions;

    private long uid;

    protected AccessToken(String accessToken, String refreshToken, long accessTokenExpireIn, long refreshTokenExpireIn, String appType, String appVersion, String role, Set<String> permissions, long uid) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpireIn = accessTokenExpireIn;
        this.refreshTokenExpireIn = refreshTokenExpireIn;
        this.appType = appType;
        this.appVersion = appVersion;
        this.role = role;
        this.permissions = permissions;
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", accessTokenExpireIn=" + accessTokenExpireIn +
                ", refreshTokenExpireIn=" + refreshTokenExpireIn +
                ", appType='" + appType + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", role='" + role + '\'' +
                ", permissions=" + permissions +
                ", uid=" + uid +
                '}';
    }

    @Override
    public int compareTo(AccessToken o) {
        if(accessToken.equals(o.getAccessToken())) return 0;
        if(accessTokenExpireIn == o.getAccessTokenExpireIn()){
            return accessToken.compareTo(o.getAccessToken());
        }
        return accessTokenExpireIn > o.getAccessTokenExpireIn() ? 1 : -1;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public long getAccessTokenExpireIn() {
        return accessTokenExpireIn;
    }

    public long getRefreshTokenExpireIn() {
        return refreshTokenExpireIn;
    }

    public String getAppType() {
        return appType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getRole() {
        return role;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public long getUid() {
        return uid;
    }
}
