package org.dynabiz.spring.web.security.core.accesstoken;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@ConfigurationProperties(prefix = "web.security.token")
public class AccessTokenConfigurationProperties {
    private Map<String, TokenExpire> expire = new HashMap<>();

    public Map<String, TokenExpire> getExpire() {
        return expire;
    }

    public void setExpire(Map<String, TokenExpire> expire) {
        this.expire = expire;
    }


    public static class TokenExpire{
        private long access;
        private long refresh;

        public long getAccess() {
            return access;
        }

        public void setAccess(long access) {
            this.access = access;
        }

        public long getRefresh() {
            return refresh;
        }

        public void setRefresh(long refresh) {
            this.refresh = refresh;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TokenExpire)) return false;
            TokenExpire that = (TokenExpire) o;
            return access == that.access &&
                    refresh == that.refresh;
        }

        @Override
        public int hashCode() {

            return Objects.hash(access, refresh);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccessTokenConfigurationProperties)) return false;
        AccessTokenConfigurationProperties that = (AccessTokenConfigurationProperties) o;
        return Objects.equals(expire, that.expire);
    }

    @Override
    public int hashCode() {

        return Objects.hash(expire);
    }
}
