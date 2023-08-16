package xyz.snaker.snakerlib.internal;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by SnakerBone on 1/07/2023
 * <p>
 * A simple implementation of a multimap
 *
 * @param <K> The map key
 * @param <V> The map value
 **/
public class MultiMap<K, V> extends LinkedHashMap<K, List<V>>
{
    public boolean map(K key, V value)
    {
        List<V> elements = get(key);
        if (elements == null) {
            elements = new ArrayList<>();
            super.put(key, elements);
        }
        return elements.add(value);
    }
}
