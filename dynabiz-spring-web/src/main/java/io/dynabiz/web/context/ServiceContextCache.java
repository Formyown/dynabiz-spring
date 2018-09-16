package io.dynabiz.web.context;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceContextCache {

    private final Map<String, AbstractServiceContext> contextMap = new ConcurrentHashMap<>();
    private String sessionId;

    public ServiceContextCache(String sessionId){
        this.sessionId = sessionId;
    }

    @JsonIgnore
    private boolean closed;

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public Map<String, AbstractServiceContext> getContextMap() {
        return contextMap;
    }

    public String getSessionId() {
        return sessionId;
    }



}
