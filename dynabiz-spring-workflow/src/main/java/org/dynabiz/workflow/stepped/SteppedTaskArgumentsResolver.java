package org.dynabiz.workflow.stepped;

import java.lang.reflect.Parameter;

public interface SteppedTaskArgumentsResolver {
    Object get(String name);
    Object get(int index);
    <T> T get(Class<T> tClass);
    Object[] resolve(Parameter... parameters);
}
