package org.dynabiz.workflow.stepped;


import org.springframework.util.MultiValueMap;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

public class MapSteppedTaskArgumentResolver extends AbstractSteppedTaskArgumentsResolver {
    private final Map<String, ?> data;

    public MapSteppedTaskArgumentResolver(Map<String, ?> data){
        this.data = data;
    }
    @Override
    public Object get(String name) {
        if(data.containsKey(name))
            return data.get(name);
        return null;
    }

    @Override
    public Object get(int index) {
        throw new RuntimeException("MapSteppedTaskArgumentResolver do not support this method");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Class<T> tClass) {
        for(Object o : data.values()){
            if(tClass.isInstance(o)) return (T)o;
        }
        return null;
    }


}
