package org.dynabiz.workflow.stepped;


import java.lang.reflect.Parameter;
import java.util.Map;

public class SingleModelSteppedTaskArgumentResolver extends AbstractSteppedTaskArgumentsResolver {
    private final Object model;

    public SingleModelSteppedTaskArgumentResolver(Object model){
        this.model = model;
    }
    @Override
    public Object get(String name) {
        throw new RuntimeException("SingleModelSteppedTaskArgumentResolver do not support this method");
    }

    @Override
    public Object get(int index) {
        throw new RuntimeException("SingleModelSteppedTaskArgumentResolver do not support this method");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Class<T> tClass) {
        throw new RuntimeException("SingleModelSteppedTaskArgumentResolver do not support this method");
    }

    @Override
    public Object[] resolve(Parameter... parameters){
        return new Object[]{ this.model };
    }

}
