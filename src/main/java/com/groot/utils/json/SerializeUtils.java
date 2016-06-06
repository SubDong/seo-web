package com.groot.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by baizz on 2014-9-5.
 */
public class SerializeUtils {

    private static ObjectMapper mapper;

    static {
        if (mapper == null)
            mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    /**
     * 序列化为byte数组
     *
     * @param o
     * @return
     */
    public static byte[] serialize(Object o) {
        Objects.requireNonNull(o);
        try {
            return mapper.writeValueAsBytes(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反序列化为List
     *
     * @param bytes
     * @param _class
     * @param <T>
     * @return
     */
    public static <T> List<T> deSerializeList(byte[] bytes, Class<T> _class) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, _class);
        try {
            return mapper.readValue(bais, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反序列化为Map
     *
     * @param bytes
     * @param _class
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> deSerializeMap(byte[] bytes, Class<T> _class) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        JavaType javaType = mapper.getTypeFactory().constructParametricType(HashMap.class, String.class, _class);
        try {
            return mapper.readValue(bais, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反序列化为复杂型的Map
     *
     * @param bytes
     * @param _class
     * @param <T>
     * @return
     */
    public static <T> Map<String, List<T>> deSerializeComplexMap(byte[] bytes, Class<T> _class) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        JavaType javaType = mapper.getTypeFactory().constructParametricType(HashMap.class, String.class, _class);
        try {
            return mapper.readValue(bais, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
