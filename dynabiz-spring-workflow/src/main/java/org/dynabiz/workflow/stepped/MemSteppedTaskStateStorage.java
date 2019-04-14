package org.dynabiz.workflow.stepped;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dynabiz.demitassedb.DB;
import org.dynabiz.demitassedb.MapDB;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



public class MemSteppedTaskStateStorage implements SteppedTaskStateStorage {
    private Map<String, Object> map = new ConcurrentHashMap<>();
    private final static String NS = "steppedTask:";
    private final static String LIMIT_NS = "steppedTask:limit:";

    private ObjectMapper mapper;
    private DB db;

    public MemSteppedTaskStateStorage() {
        this.mapper = new ObjectMapper();
        this.db = new MapDB();


    }

    @Override
    public boolean save(String token,  SteppedTaskState state, long ttl, boolean force) {
        String strData;
        try {
            strData = mapper.writeValueAsString(state);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if(force){
            this.db.set(NS + token, strData, ttl);
            return true;
        }else {
            return this.db.setIfAbsent(NS + token, strData, ttl);
        }
    }

    @Override
    public SteppedTaskState find(String token) {
        try {
            return mapper.readValue((String)this.db.get(NS + token), SteppedTaskState.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean remove(String token) {
        return this.db.remove(NS + token) == null;
    }

    @Override
    public void updateExpire(String token, long ttl) {
        db.updateExpire(NS + token, ttl);
    }
}
