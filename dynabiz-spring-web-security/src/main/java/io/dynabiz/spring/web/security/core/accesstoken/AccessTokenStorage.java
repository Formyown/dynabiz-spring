package io.dynabiz.spring.web.security.core.accesstoken;

public interface AccessTokenStorage {
    void save(AccessToken token);
    AccessToken findByAccessTokenValue(String token);
    AccessToken findByRefreshTokenValue(String token);
    void removeByAccessTokenValue(String token);
    void removeByRefreshTokenValue(String token);
    boolean existsAccessTokenValue(String token);
    boolean existsRefreshTokenValue(String token);
}
