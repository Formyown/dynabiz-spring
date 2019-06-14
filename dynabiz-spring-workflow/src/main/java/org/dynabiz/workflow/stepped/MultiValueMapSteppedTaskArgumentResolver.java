package org.dynabiz.workflow.stepped;


import org.springframework.util.MultiValueMap;

import java.lang.reflect.Parameter;
import java.util.List;

public class MultiValueMapSteppedTaskArgumentResolver extends AbstractSteppedTaskArgumentsResolver {
    private final MultiValueMap<String, ?> data;

    public MultiValueMapSteppedTaskArgumentResolver(MultiValueMap<String, ?> data){
        this.data = data;
    }
    @Override
    public Object get(String name) {
        if(data.containsKey(name))
            return data.get(name).get(0);
        return null;
    }

    @Override
    public Object get(int index) {
        throw new RuntimeException("MapSteppedTaskArgumentResolver do not support this method");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Class<T> tClass) {
        for(List o : data.values()){
            if(tClass.isInstance(o.get(0))) return (T)o.get(0);
        }
        return null;
    }

}
