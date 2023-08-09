package xyz.snaker.snakerlib.internal;

/**
 * Created by SnakerBone on 4/06/2023
 * <p>
 * A consumer for quad trees
 *
 * @param <U> First object
 * @param <D> Second object
 * @param <T> Third object
 * @param <Q> Fourth object
 **/
public interface QuadConsumer<U, D, T, Q>
{
    void accept(U u, D d, T t, Q q);
}
