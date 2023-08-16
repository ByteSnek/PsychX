package xyz.snaker.snakerlib.concurrent;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SnakerBone on 2/08/2023
 * <p>
 * A simple buffer using a {@link List}
 *
 * @param <E> The element to store in the buffer
 **/
public class SimpleBuffer<E> extends AbstractList<E>
{
    /**
     * The base list buffer
     **/
    private final List<E> buffer;

    public SimpleBuffer()
    {
        this.buffer = new ArrayList<>();
    }

    @Override
    public boolean add(E element)
    {
        synchronized (buffer) {
            return buffer.add(element);
        }
    }

    @Override
    public boolean remove(Object element)
    {
        synchronized (buffer) {
            return buffer.remove(element);
        }
    }

    @Override
    public E get(int index)
    {
        synchronized (buffer) {
            return buffer.get(index);
        }
    }

    @Override
    public int size()
    {
        synchronized (buffer) {
            return buffer.size();
        }
    }

    @Override
    public boolean isEmpty()
    {
        synchronized (buffer) {
            return buffer.isEmpty();
        }
    }
}
