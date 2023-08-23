package xyz.snaker.snakerlib.concurrent;

import java.util.List;
import java.util.Map;
import java.util.Set;

import xyz.snaker.snakerlib.internal.MultiMap;

/**
 * Created by SnakerBone on 1/07/2023
 * <p>
 * A simple asynchronous {@link MultiMap}
 *
 * @param <K> The map key
 * @param <V> The map value
 **/
public class AsyncMultiMap<K, V> extends MultiMap<K, V>
{
    /**
     * The base multimap
     **/
    private final MultiMap<K, V> baseMap;

    public AsyncMultiMap()
    {
        synchronized (this) {
            baseMap = new MultiMap<>();
        }
    }

    @Override
    public List<V> put(K key, List<V> value)
    {
        synchronized (baseMap) {
            return baseMap.put(key, value);
        }
    }

    @Override
    public List<V> get(Object key)
    {
        synchronized (baseMap) {
            return baseMap.get(key);
        }
    }

    @Override
    public boolean map(K key, V value)
    {
        synchronized (baseMap) {
            return baseMap.map(key, value);
        }
    }

    @Override
    public Set<Map.Entry<K, List<V>>> entrySet()
    {
        synchronized (baseMap) {
            Set<Map.Entry<K, List<V>>> entrySet = baseMap.entrySet();
            synchronized (entrySet) {
                MultiMap<K, V> asyncMap = new MultiMap<>();
                for (var entry : entrySet) {
                    asyncMap.put(entry.getKey(), entry.getValue());
                }
                return asyncMap.entrySet();
            }
        }
    }
}
