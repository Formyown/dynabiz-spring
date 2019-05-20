package org.dynabiz.mapper;

import java.lang.reflect.Field;

class FieldDescription {
    /**
     * Real field name
     */
    public String realName;
    /**
     * The name that user has been given
     */
    public String mappingName;
    /**
     * The name that used to seek
     */
    public String name;

    public Class type;
    public Class[] mappingClass;

    public Class filterClass;



    public void setConfigs(Field field, MappingData mappingData, MappingField mappingField){
        this.realName = field.getName();
        this.name = mappingField.name();
        this.type = field.getType();

        this.mappingName = mappingField.name().isEmpty() ? realName : name;

        this.mappingClass = mappingField.targetClass().length == 0 ?
                (mappingData == null ?
                        null :
                        mappingData.targetClass().length == 0 ?
                                null :
                                mappingData.targetClass()) :
                mappingField.targetClass();

        this.filterClass = void.class.equals(mappingField.postFilter()) ?
                (mappingData == null ?
                        null :
                        void.class.equals(mappingData.filter()) ?
                                null :
                                mappingData.filter()) :
                mappingField.postFilter();
    }
}
