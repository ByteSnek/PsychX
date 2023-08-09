package xyz.snaker.snakerlib.internal;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SnakerBone on 2/08/2023
 **/
public class Buffer<E> extends AbstractList<E>
{
    private final List<E> buffer;

    public Buffer()
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
