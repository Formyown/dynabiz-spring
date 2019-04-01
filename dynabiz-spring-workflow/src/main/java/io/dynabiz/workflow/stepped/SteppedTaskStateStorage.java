package io.dynabiz.workflow.stepped;

import java.util.concurrent.TimeUnit;

public interface SteppedTaskStateStorage {
    boolean save(String token, TaskState state, long ttl, boolean force);
    TaskState find(String token);
    void remove(String token);
    void updateExpire(String token, long ttl);
}
