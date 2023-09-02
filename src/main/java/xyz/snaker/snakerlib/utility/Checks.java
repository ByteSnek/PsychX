package xyz.snaker.snakerlib.utility;

import java.util.Map;

/**
 * Created by SnakerBone on 2/09/2023
 **/
public class Checks
{
    public static <K, V> K checkKeyExists(Map<K, V> map, K key)
    {
        if (!map.containsKey(key)) {
            throw new RuntimeException(String.format("Key '%s' not present in map", key));
        }

        return key;
    }

    public static <K, V> K checkKeyExists(Map<K, V> map, K key, String errorMessage)
    {
        if (!map.containsKey(key)) {
            throw new RuntimeException(errorMessage);
        }

        return key;
    }

    public static <K, V> K checkKeyExists(Map<K, V> map, K key, String errorTemplate, Object... args)
    {
        if (!map.containsKey(key)) {
            throw new RuntimeException(String.format(errorTemplate, args));
        }

        return key;
    }

    public static <K, V> V checkValueExists(Map<K, V> map, V value)
    {
        if (!map.containsValue(value)) {
            throw new RuntimeException(String.format("Value '%s' not present in map", value));
        }

        return value;
    }

    public static <K, V> V checkValueExists(Map<K, V> map, V value, String errorMessage)
    {
        if (!map.containsValue(value)) {
            throw new RuntimeException(errorMessage);
        }

        return value;
    }

    public static <K, V> V checkValueExists(Map<K, V> map, V value, String errorTemplate, Object... args)
    {
        if (!map.containsValue(value)) {
            throw new RuntimeException(String.format(errorTemplate, args));
        }

        return value;
    }

    public static <K, V> K checkValueExistsFromKey(Map<K, V> map, K key)
    {
        if (map.get(key) == null) {
            throw new RuntimeException(String.format("Value '%s' not present in map", key));
        }

        return key;
    }

    public static <K, V> K checkValueExistsFromKey(Map<K, V> map, K key, String errorMessage)
    {
        if (map.get(key) == null) {
            throw new RuntimeException(errorMessage);
        }

        return key;
    }

    public static <K, V> K checkValueExistsFromKey(Map<K, V> map, K key, String errorTemplate, Object... args)
    {
        if (map.get(key) == null) {
            throw new RuntimeException(String.format(errorTemplate, args));
        }

        return key;
    }
}
