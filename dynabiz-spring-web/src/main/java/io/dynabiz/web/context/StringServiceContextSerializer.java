package io.dynabiz.web.context;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class StringServiceContextSerializer implements ServiceContextSerializer<String> {
    private ObjectMapper mapper;

    public StringServiceContextSerializer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String serialize(AbstractServiceContext object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public <T extends AbstractServiceContext> T deserialize(Class<T> clazz, String data) {
        try {
            return mapper.readValue(data, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
