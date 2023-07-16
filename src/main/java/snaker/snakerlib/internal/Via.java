package snaker.snakerlib.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by SnakerBone on 8/07/2023
 * <p>
 * Indicates that the target should only be accessed from the specified {@link Accessibility} in a multithreaded class.
 **/
@Via(Accessibility.ALL)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Via
{
    Accessibility value();
}
