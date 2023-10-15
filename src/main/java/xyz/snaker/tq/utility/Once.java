package xyz.snaker.tq.utility;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A simple single use latch.
 * Example:
 * <pre>
 *     StringBuilder b = new StringBuilder();
 *     Once once = new Once();
 *     for (String s : strings) {
 *         if (once.once()) {
 *             b.append(", ");
 *         }
 *         b.append(s);
 *     }
 * </pre>
 * <p>
 * Created by covers1624 on 5/9/21.
 * <p>
 * Modified by SnakerBone on 15/10/23
 */
public class Once
{
    private final AtomicBoolean bool = new AtomicBoolean();

    public boolean once()
    {
        return !bool.getAndSet(true); // Why does it return the previous value????? Anyways... fixed it...
    }

    public void reset()
    {
        bool.getAndSet(false);
    }
}
