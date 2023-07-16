package snaker.snakerlib.internal;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by SnakerBone on 1/07/2023
 * <p>
 * A simple asynchronous {@link MultiMap}
 **/
public class AsynchronousMultiMap<K, V> extends MultiMap<K, V>
{
    private final MultiMap<K, V> baseMap;

    public AsynchronousMultiMap()
    {
        baseMap = new MultiMap<>();
    }

    @Override
    public List<V> put(K key, List<V> value)
    {
        return baseMap.put(key, value);
    }

    @Override
    public List<V> get(Object key)
    {
        return baseMap.get(key);
    }

    @Override
    public boolean map(K key, V value)
    {
        return baseMap.map(key, value);
    }

    @Override
    public Set<Map.Entry<K, List<V>>> entrySet()
    {
        Set<Map.Entry<K, List<V>>> entrySet = baseMap.entrySet();
        MultiMap<K, V> asyncMap = new MultiMap<>();
        for (var entry : entrySet) {
            asyncMap.put(entry.getKey(), entry.getValue());
        }
        return asyncMap.entrySet();
    }
}
