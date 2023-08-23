package xyz.snaker.snakerlib.utility.tools;

import java.lang.annotation.Annotation;

import xyz.snaker.snakerlib.SnakerLib;

/**
 * Created by SnakerBone on 22/08/2023
 **/
public class AnnotationStuff
{
    public static <V> boolean isPresent(V obj, Class<? extends Annotation> annotationClass)
    {
        return obj.getClass().isAnnotationPresent(annotationClass);
    }

    public static boolean isPresent(Class<?> clazz, Class<? extends Annotation> annotationClass)
    {
        return clazz.isAnnotationPresent(annotationClass);
    }

    public static boolean isPresent(Class<? extends Annotation> annotationClass)
    {
        return SnakerLib.STACK_WALKER.getCallerClass().isAnnotationPresent(annotationClass);
    }

    public static <V> boolean isNotPresent(V obj, Class<? extends Annotation> annotationClass)
    {
        return !isPresent(obj, annotationClass);
    }

    public static boolean isNotPresent(Class<?> clazz, Class<? extends Annotation> annotationClass)
    {
        return !isPresent(clazz, annotationClass);
    }

    public static boolean isNotPresent(Class<? extends Annotation> annotationClass)
    {
        return !isPresent(annotationClass);
    }
}
