package org.dynabiz.spring.web.security.core.accesstoken;


import org.dynabiz.spring.web.security.core.SecurityContext;
import org.dynabiz.std.exception.TokenException;
import org.dynabiz.web.context.ServiceContextHolder;


import java.util.Set;

public class AccessTokenManager {

    public AccessTokenManager(AccessTokenStorage storage, AccessTokenConfigurationProperties properties) {
        this.storage = storage;
        this.properties = properties;
    }

    private AccessTokenStorage storage;

    private AccessTokenConfigurationProperties properties;

    public AccessToken load(String accessTokenValue){
        AccessToken accessToken = storage.findByAccessTokenValue(accessTokenValue);
        if(accessToken == null) throw TokenException.TOKEN_NOT_FOUND;
        ServiceContextHolder.put(new SecurityContext(accessToken.getUid(),
                accessToken.getRole(), accessToken.getPermissions()));
        return accessToken;
    }

    /**
     * 为用户申请AccessToken
     * @param uid
     * @param role
     * @param permissions
     * @return
     */
    public AccessToken assign(String uid, String role, Set<String> permissions, String appType, String appVersion){
        AccessTokenBuilder builder = new AccessTokenBuilder(properties);
        builder = builder.bindUid(uid).setRole(role).setPermissions(permissions).setAppTypeVersion(appType, appVersion);
        AccessToken accessToken = builder.build();
        storage.save(accessToken);
        //设置上下文
        ServiceContextHolder.put(new SecurityContext(uid, role, permissions));
        return accessToken;
    }

    /**
     * 续签AccessToken
     * @param refreshTokenValue
     * @return
     */
    public AccessToken refresh(String refreshTokenValue){
        AccessToken access = storage.findByRefreshTokenValue(refreshTokenValue);
        if(access == null) throw TokenException.TOKEN_NOT_FOUND;
        storage.removeByRefreshTokenValue(refreshTokenValue);
        storage.removeByAccessTokenValue(access.getAccessToken());

        AccessTokenBuilder builder = new AccessTokenBuilder(properties);
        builder = builder.fromAccessToken(access);
        AccessToken accessToken = builder.build();
        storage.save(accessToken);
        return accessToken;
    }

    /**
     * 取消AccessToken授权
     * @param accessTokenValue
     */
    public void cancel(String accessTokenValue){
        AccessToken access = storage.findByAccessTokenValue(accessTokenValue);
        if(access == null) throw TokenException.TOKEN_NOT_FOUND;
        storage.removeByRefreshTokenValue(access.getRefreshToken());
        storage.removeByAccessTokenValue(accessTokenValue);
    }

}
