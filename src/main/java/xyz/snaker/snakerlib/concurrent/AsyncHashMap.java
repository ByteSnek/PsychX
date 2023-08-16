package xyz.snaker.snakerlib.concurrent;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by SnakerBone on 1/07/2023
 * <p>
 * A simple asynchronous {@link Map}
 *
 * @param <K> The map key
 * @param <V> The map value
 **/
public class AsyncHashMap<K, V> extends AbstractMap<K, V>
{
    /**
     * The base hash map
     **/
    private final HashMap<K, V> baseMap;

    public AsyncHashMap()
    {
        baseMap = new HashMap<>();
    }

    @Override
    public V put(K key, V value)
    {
        return baseMap.put(key, value);
    }

    @Override
    public V get(Object key)
    {
        return baseMap.get(key);
    }

    @Override
    public Set<Entry<K, V>> entrySet()
    {
        Set<Entry<K, V>> entrySet = baseMap.entrySet();
        HashMap<K, V> asyncMap = new HashMap<>();
        for (var entry : entrySet) {
            asyncMap.put(entry.getKey(), entry.getValue());
        }
        return asyncMap.entrySet();
    }
}