package org.dynabiz.workflow.stepped;

public interface SteppedTaskStateStorage {
    boolean save(String token, SteppedTaskState state, long ttl, boolean force);
    SteppedTaskState find(String token);
    boolean remove(String token);
    void updateExpire(String token, long ttl);
}
