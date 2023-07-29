package snaker.snakerlib.internal;

import com.google.common.base.Suppliers;

import java.util.function.Supplier;

/**
 * Created by SnakerBone on 29/07/2023
 **/
public class MemoizedValue<T>
{
    private final Supplier<T> supplier;

    public MemoizedValue(Supplier<T> supplier)
    {
        this.supplier = Suppliers.memoize(supplier::get);
    }

    public T get()
    {
        return supplier.get();
    }
}
