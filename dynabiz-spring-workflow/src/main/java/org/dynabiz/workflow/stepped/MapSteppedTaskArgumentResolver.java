package org.dynabiz.workflow.stepped;


import org.springframework.util.MultiValueMap;

import java.lang.reflect.Parameter;
import java.util.List;

public class MapSteppedTaskArgumentResolver implements SteppedTaskArgumentsResolver {
    private final MultiValueMap<String, ?> data;

    public MapSteppedTaskArgumentResolver(MultiValueMap<String, ?> data){
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

    @Override
    public Object[] resolve(Parameter... parameters) {
        Object[] values = new Object[parameters.length];
        int i = 0;
        for(Parameter p : parameters){
            Param param = p.getAnnotation(Param.class);
            if (param != null){
                values[i] = get(param.value());
            }
            else {
                values[i] = get(p.getType());
            }
            i++;
        }
        return values;
    }
}
