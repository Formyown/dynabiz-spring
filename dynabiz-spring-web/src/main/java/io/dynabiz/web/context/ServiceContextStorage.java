package io.dynabiz.web.context;

import java.util.Map;

public interface ServiceContextStorage<T> {
    void saveAll(String sessionId, Map<String, T> dataMap);
    void save(String sessionId, String className, T data);
    boolean exists(String sessionId);
    boolean exists(String sessionID, String className);
    void removeAll(String sessionId);
    void remove(String sessionId, String className);
    T read(String sessionId, String className);
    Map<String, T> readAll(String sessionId);
}
