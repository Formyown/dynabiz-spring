package org.dynabiz.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class MapperUtil {
    public static boolean checkSourceType(FieldDescription fieldDescription, Class sourceType){
        if(fieldDescription.mappingClass == null) return true;
        for(Class c : fieldDescription.mappingClass) if(sourceType.equals(c)) return true;
        return false;
    }

    public static List<FieldDescription> getMappedFields(Class c){
        Class curClass = c;
        List<FieldDescription> mappedFields = new ArrayList<>();
        do{
            Field[] fields = curClass.getDeclaredFields();
            MappingData mappingData = (MappingData)curClass.getDeclaredAnnotation(MappingData.class);
            for (Field field : fields) {
                MappingField mappingField;
                if (null != (mappingField = field.getAnnotation(MappingField.class))) {
                    FieldDescription description = new FieldDescription();
                    description.setConfigs(field, mappingData, mappingField);
                    mappedFields.add(description);
                }
            }
        }while (!Object.class.equals(curClass = curClass.getSuperclass()));

        return mappedFields;
    }


    public static Object getFieldValue(Object o, String name){
        try {
            return getField(o, name).get(o);
        }
        catch (IllegalAccessException e) {
            throw new MapperException(e);
        }
    }

    public static void setFieldValue(Object o, String name, Object value){
        try {
            getField(o, name).set(o, value);
        } catch (IllegalAccessException e) {
            throw new MapperException(e);
        }
    }

    public static Field getField(Object o, String name){

        Field field = null;
        Class curClass = o.getClass();
        do{
            for(Field f : o.getClass().getDeclaredFields()){
                if(f.getName().equals(name)){
                    field = f;
                    break;
                }
            }
            // find one match field from current
            if(field != null){
                break;
            }
        }while (!Object.class.equals(curClass = curClass.getSuperclass()));

        if(field == null) throw new MapperException(
                String.format("Can not find field \"%s\" from type %s.", name, o.getClass().getTypeName()));
        field.setAccessible(true);

        return field;
    }
}
