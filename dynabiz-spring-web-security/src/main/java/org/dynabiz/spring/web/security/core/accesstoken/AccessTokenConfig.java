package org.dynabiz.spring.web.security.core.accesstoken;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;



@EnableConfigurationProperties(AccessTokenConfigurationProperties.class)
public class AccessTokenConfig {

    @Bean
    public AccessTokenManager accessTokenManager(AccessTokenStorage storage,
                                                 AccessTokenConfigurationProperties properties){
        return new AccessTokenManager(storage, properties);
    }

}
