package xyz.snaker.snakerlib.utility;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.utility.tools.StringStuff;

import net.minecraft.resources.ResourceLocation;

/**
 * Created by SnakerBone on 15/02/2023
 **/
public class ResourcePath extends ResourceLocation
{
    /**
     * An example resource path of a solid texture
     **/
    public static final ResourcePath SOLID_TEXTURE = new ResourcePath("textures/solid.png");

    /**
     * An example resource path of a transparent texture
     **/
    public static final ResourcePath NO_TEXTURE = new ResourcePath("textures/clear.png");

    /**
     * Creates a resource path with the class name that called this method as the path
     *
     * @return A new resource path
     **/
    public static ResourcePath thisClass()
    {
        Class<?> clazz = SnakerLib.STACK_WALKER.getCallerClass();
        if (clazz == null) {
            throw new NoClassDefFoundError("Could not find class");
        }
        return fromClass(clazz);
    }

    /**
     * Creates a resource path using a class name
     *
     * @param clazz The class to get the name from
     * @return A new resource path with the class name
     **/
    public static <T> ResourcePath fromClass(Class<T> clazz)
    {
        return new ResourcePath(clazz);
    }

    public ResourcePath(String path)
    {
        super(SnakerLib.MOD.get(), path);
    }

    private ResourcePath(Class<?> clazz)
    {
        super(SnakerLib.MOD.get(), StringStuff.i18nf(clazz.getSimpleName()));
    }
}
