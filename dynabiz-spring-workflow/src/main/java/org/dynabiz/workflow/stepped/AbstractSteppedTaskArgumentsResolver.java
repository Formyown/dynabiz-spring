package org.dynabiz.workflow.stepped;

import java.lang.reflect.Parameter;

public abstract class AbstractSteppedTaskArgumentsResolver {
    public abstract Object get(String name);
    public abstract Object get(int index);
    public abstract <T> T get(Class<T> tClass);

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
