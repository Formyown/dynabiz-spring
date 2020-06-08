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
import java.util.stream.Stream;

import static org.dynabiz.mapper.MapperUtil.*;

/**
 * Created by Deyu Heng on 2018/09/3.
 */
public class ModelMapper {

    public static <T, ST> List<T> mapFromCollection(Class<T> targetType, Collection<ST> sourcesList){
        return mapFromCollection(targetType,sourcesList, null);
    }

    public static <T, ST> List<T> mapFromCollection(Class<T> targetType, Collection<ST> sourcesList, Mapping<ST, T> mapping){
        return sourcesList.stream().map((s)->mapFrom(targetType, s, mapping)).collect(Collectors.toList());
    }

    public static <T, ST> List<T> mapFromCollection(Class<T> targetType, Stream<ST> sourcesList, Mapping<ST, T> mapping){
        return sourcesList.map((s)->mapFrom(targetType, s, mapping)).collect(Collectors.toList());
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


    private static void applyValue(Object target, Object source, String targetName, String sourceName){
        setFieldValue(target, targetName, getFieldValue(source, sourceName));
    }

    @SuppressWarnings("unchecked")
    public static <T, ST> void map(T target, ST source, Mapping<ST, T> mapping){
        Objects.requireNonNull(target, "Target object could not be null.");
        Objects.requireNonNull(source, "Source object could not be null.");


        List<FieldDescription> sourceFields = getMappedFields(source.getClass());
        List<FieldDescription> targetFields = getMappedFields(target.getClass());

        // eg. dto to entity
        if(targetFields.size() == 0 && sourceFields.size() > 0){
            for (FieldDescription field: sourceFields) {
                applyValue(target, source, field.mappingName, field.realName);
            }
        }
        // eg. entity to dto
        else if(targetFields.size() > 0 && sourceFields.size() == 0){
            for (FieldDescription field: targetFields) {
                applyValue(target, source, field.realName, field.mappingName);
            }
        }


        if(mapping != null)mapping.mapTo(source, target);


    }
}
