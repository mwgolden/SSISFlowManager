package com.wgolden.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;

public class JsonNodeConverter {
    private static final Map<String, Class<?>> dataTypeToClassMap = new HashMap<>();

    static {
        dataTypeToClassMap.put("String", String.class);
        dataTypeToClassMap.put("Boolean", Boolean.class);
        dataTypeToClassMap.put("Integer", Integer.class);
        dataTypeToClassMap.put("Decimal", Double.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> T convertNode(String fieldName, String dataType, JsonNode node){
        if(node == null){
            return null;
        }
        Class<?> c = dataTypeToClassMap.get(dataType);
        switch(dataType) {
            case "String":
                if(node.get(fieldName).isTextual()){
                    return (T) c.cast(node.get(fieldName).asText());
                }
                break;
            case "Boolean":
                if(node.get(fieldName).isBoolean()){
                    return (T) c.cast(node.get(fieldName).asBoolean());
                }
                break;
            case "Integer":
                if(node.get(fieldName).isInt()){
                    return (T) c.cast(node.get(fieldName).asInt());
                }
                break;
            case "Decimal":
                if(node.get(fieldName).isDouble()){
                    return (T) c.cast(node.get(fieldName).asDouble());
                }
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported data type: %s", dataType));
        }
        throw new IllegalArgumentException(String.format("Conversion failed for %s", dataType));
    }
}
