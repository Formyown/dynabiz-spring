/*
 * Copyright (c) 2018, Deyu Heng. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.dynabiz.mapper;



import org.dynabiz.util.Assert;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Deyu Heng on 2018/09/3.
 */
public class ObjectMapper {

    public static <T, ST> List<T> mapFromCollection(Class<T> targetType, Collection<ST> sourcesList){
        return mapFromCollection(targetType,sourcesList, null);
    }

    public static <T, ST> List<T> mapFromCollection(Class<T> targetType, Collection<ST> sourcesList, Mapping<ST, T> mapping){
        return sourcesList.stream().map((s)->mapFrom(targetType, s, mapping)).collect(Collectors.toList());
    }



    public static <T, ST> T mapFrom(Class<T> targetType, ST sources){
        return mapFrom(targetType, sources, null);

    }

    public static <T, ST> T mapFrom(Class<T> targetType, ST sources,  Mapping<ST, T> mapping){
        T target;
        try {
            target = targetType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new MapperException(e);
        }
        map(target, sources, mapping);
        return target;
    }

    public static <T, ST> void map(T target, ST sources){
        map(target, sources, null);
    }

    /**
     * 从"目标"类中找到映射设置
     * @return
     */
    public static List<MappingFieldDescription> getDescriptionForTargetType(Class targetType){
        MappingData config = (MappingData)targetType.getAnnotation(MappingData.class);
        Field[] fields = targetType.getDeclaredFields();
        List<MappingFieldDescription> descriptionList = new ArrayList<>(fields.length);
        MappingField fieldConfig;
        for (Field field : fields) {
            if (null != (fieldConfig = field.getAnnotation(MappingField.class))) {
                MappingFieldDescription d = new MappingFieldDescription();
                d.setConfigs(field.getType(), field.getName(), config, fieldConfig);
                descriptionList.add(d);
            }
        }
        return descriptionList;
    }


    @SuppressWarnings("unchecked")
    private static void applyDescription(List<MappingFieldDescription> descriptions, Object src, Object dest){
        for (MappingFieldDescription d: descriptions) {
            Object value = readValue(d.mappingName, d.type, src);
            if(null != d.filterClass){
                try {
                    value = ((Function)d.filterClass.newInstance()).apply(value);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new MapperException(e);
                }
            }
            setValue(d.mappingName, d.type, dest, value);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T, ST> void map(T target, ST sources, Mapping<ST, T> mapping){
        Objects.requireNonNull(target, "Target object could not be null.");
        Objects.requireNonNull(sources, "Source object could not be null.");

//        Class curType = target.getClass();
//        Map<Class, Function> filterCache = new HashMap<>();
//        do{
//            mapDeclaredFields(curType, target, sources, filterCache);
//        }while (!Object.class.equals(curType = curType.getSuperclass()));
//
//        if(mapping != null)mapping.mapTo(sources, target);
        Class curType;
        MappingData mappingData;
        if(null != (mappingData = target.getClass().getAnnotation(MappingData.class))){
            curType = target.getClass();
        }
        else if(null != (mappingData = sources.getClass().getAnnotation(MappingData.class))){
            curType = sources.getClass();
        }
        else throw new MapperException("One of the target and source must have 'MappingData'");
        do{
            List<MappingFieldDescription> descriptions = getDescriptionForTargetType(curType);
            applyDescription(descriptions, sources, target);
        }while (!Object.class.equals(curType = curType.getSuperclass()));

        if(mapping != null)mapping.mapTo(sources, target);

    }

    private static void mapDeclaredFields(Class targetType, Object target, Object sources,
                                          Map<Class, Function> filterCache){

        MappingData config = target.getClass().getAnnotation(MappingData.class);
        MappingFieldDescription fieldDescription = new MappingFieldDescription();
        Class sourceType = sources.getClass();
        Field[] fields = getMappedFields(target);
        for (Field field : fields) {

            fieldDescription.setConfigs(field.getType(), field.getName(), config, field.getAnnotation(MappingField.class));
            if(checkSourceType(fieldDescription, targetType))
                applyValue(
                        fieldDescription,
                        target,
                        Assert.notNull(findSetter(field.getType(), field.getName(), targetType),
                                new MapperException(
                                        String.format("Cannot find get method with field named %s type %s",
                                                field.getName(),
                                                field.getType()
                                        )
                                )
                        ),
                        sourceType,
                        sources,
                        filterCache
                );
        }

        Method[] methods = getMappedSetter(target);
        for (Method method : methods) {
            fieldDescription.setConfigs(
                    method.getParameterTypes()[0],
                    getFieldNameFromSetter(method.getName()),
                    config,
                    method.getAnnotation(MappingField.class)
            );
            if(checkSourceType(fieldDescription, targetType)) applyValue(
                    fieldDescription,
                    target,
                    method,
                    sourceType,
                    sources,
                    filterCache);
        }

    }


    @SuppressWarnings("unchecked")
    private static void applyValue(MappingFieldDescription fieldDescription, Object target, Method setter,
                                   Class sourceType, Object source, Map<Class, Function> filterCache){
        if(1 != setter.getParameterCount()) throw new IllegalStateException("");


        if(fieldDescription.filterClass != null){
            Function mappingFilter = filterCache.get(fieldDescription.filterClass);
            if(mappingFilter == null) {
                try {
                    filterCache.put(fieldDescription.filterClass,
                            mappingFilter = (Function)fieldDescription.filterClass.newInstance());

                    Object value = readValue(fieldDescription.mappingName, fieldDescription.type, source);
                    setter.invoke(target, mappingFilter.apply(value));
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new MapperException(e);
                }
            }

        }
        else {
            try {
                setter.invoke(target, readValue(fieldDescription.mappingName, fieldDescription.type, source));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new MapperException(e);
            }
        }


    }


    private static Object readValue(String name, Class fieldType, Object source){
        Method getter = Assert.notNull(findGetter(fieldType, name, source.getClass()),
            new MapperException(String.format("Cannot find get method with field named %s in type %s", name, source.getClass())));
        try {
            return getter.invoke(source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new MapperException(e);
        }
    }


    private static void setValue(String name, Class fieldType, Object dest, Object ...value){
        Method setter = Assert.notNull(findSetter(fieldType, name, dest.getClass()),
                new MapperException(String.format("Cannot find get method with field named %s in type %s", name, dest.getClass())));
        try{
            setter.invoke(dest, value);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            throw new MapperException(e);
        }
    }


    private static Field[] getMappedFields(Object o){
        Field[] fields = o.getClass().getDeclaredFields();
        Field[] mappedFields = new Field[fields.length];
        int mappedCount = 0;
        for (Field field : fields) {
            if (null != field.getAnnotation(MappingField.class)) {
                mappedFields[mappedCount++] = field;
            }
        }
        return Arrays.copyOf(mappedFields, mappedCount);
    }

    private static Method[] getMappedSetter(Object o){
        Method[] methods = o.getClass().getDeclaredMethods();
        Method[] mappedSetters = new Method[methods.length];
        int mappedCount = 0;
        for (Method method : methods) {
            if (null != method.getAnnotation(MappingField.class)) {
                mappedSetters[mappedCount++] = method;
            }
        }
        return Arrays.copyOf(mappedSetters, mappedCount);
    }






    private static boolean checkSourceType(MappingFieldDescription fieldDescription, Class sourceType){
        if(fieldDescription.mappingClass == null) return true;
        for(Class c : fieldDescription.mappingClass) if(sourceType.equals(c)) return true;
        return false;
    }

    private static String getFieldNameFromSetter(String methodName){
        if(methodName.startsWith("set")) methodName = methodName.substring(3);
        if(methodName.startsWith("is")) methodName = methodName.substring(2);

        if(Character.isLowerCase(methodName.charAt(0)))
            return methodName;
        else{
            char[] chars = methodName.toCharArray();
            chars[0]+=32;
            return new String(chars);
        }
    }

    private static class MappingFieldDescription {
        private String realName;
        private Class type;
        private Class[] mappingClass;
        private String mappingName;
        private Class filterClass;



        private void setConfigs(Class type, String fieldName, MappingData config, MappingField mappingField){
            this.type = type;

            this.realName = fieldName;
            this.mappingName = mappingField.name().isEmpty() ? fieldName : mappingField.name();
            this.mappingClass = mappingField.targetClass().length == 0 ?
                    (config == null ?
                            null :
                            config.targetClass().length == 0 ?
                                    null :
                                    config.targetClass()) :
                    mappingField.targetClass();
            this.filterClass = void.class.equals(mappingField.filter()) ?
                    (config == null ?
                            null :
                            void.class.equals(config.filter()) ?
                                    null :
                                    config.filter()) :
                    mappingField.filter();
        }
    }

    @SuppressWarnings("unchecked")
    private static Method findGetter(Class type, String name, Class objType){
        if(Character.isLowerCase(name.charAt(0))){
            char[] chars = name.toCharArray();
            chars[0]-=32;
            name = new String(chars);
        }


        String prefix = (boolean.class.equals(type) || Boolean.class.equals(type)) ? "is" : "get";
        try {
            return objType.getMethod(prefix + name);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static Method findSetter(Class type, String name, Class objType){
        if(Character.isLowerCase(name.charAt(0)) && Character.isLowerCase(name.charAt(1))) {
            char[] chars = name.toCharArray();
            chars[0]-=32;
            name = new String(chars);
        }
        String prefix = (boolean.class.equals(type) || Boolean.class.equals(type)) ? "is" : "set";
        Method[] methods = objType.getMethods();
        for (Method method : methods) {
            if(method.getName().equals(prefix + name)) return method;
        }
        return null;
    }
}
