package org.dynabiz.spring.web.security.core.accesstoken;


import org.dynabiz.BusinessException;
import org.dynabiz.util.RandomString;

import java.util.Map;
import java.util.Set;

public class AccessTokenBuilder {
    private static final int ACCESS_TOKEN_LEN = 20;
    private static final int REFRESH_TOKEN_LEN = 25;

    private String role;
    private Set<String> permissions;
    private String uid;
    private String appType;
    private String appVer;
    private AccessTokenConfigurationProperties config;

    public AccessTokenBuilder(AccessTokenConfigurationProperties config){
        if(config == null) throw new IllegalArgumentException("Config");
        this.config = config;
    }

    public AccessToken build(){
        String access = RandomString.nextHex(ACCESS_TOKEN_LEN);
        String refresh = RandomString.nextHex(REFRESH_TOKEN_LEN);

        Map<String, AccessTokenConfigurationProperties.TokenExpire> expireMap = config.getExpire();
        if(!expireMap.containsKey(appType)) throw new BusinessException("App type not found");
        long accessExpireIn = System.currentTimeMillis() + expireMap.get(appType).getAccess();
        long refreshExpireIn = System.currentTimeMillis() + expireMap.get(appType).getRefresh();

        return new AccessToken(access, refresh, accessExpireIn,refreshExpireIn,
                appType, appVer, role, permissions, uid);
    }

    public AccessTokenBuilder bindUid(String uid){
        this.uid = uid;
        return this;
    }

    public AccessTokenBuilder setRole(String role){
        this.role = role;
        return this;
    }

    public AccessTokenBuilder setAppTypeVersion(String type, String ver){
        this.appType = type;
        this.appVer = ver;
        return this;
    }

    public AccessTokenBuilder setPermissions(Set<String> permissions){
        this.permissions = permissions;
        return this;
    }


    public AccessTokenBuilder fromAccessToken(AccessToken token){
        this.role = token.getRole();
        this.permissions = token.getPermissions();
        this.uid = token.getUid();
        this.appType = token.getAppType();
        this.appVer = token.getAppVersion();
        return this;
    }

}
