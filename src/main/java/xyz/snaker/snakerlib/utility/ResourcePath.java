package xyz.snaker.snakerlib.utility;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.utility.tools.StringStuff;

import net.minecraft.resources.ResourceLocation;

/**
 * Created by SnakerBone on 15/02/2023
 **/
public class ResourcePath extends ResourceLocation
{
    public static final ResourcePath SOLID_TEXTURE = new ResourcePath("textures/solid.png");
    public static final ResourcePath NO_TEXTURE = new ResourcePath("textures/clear.png");

    public static <T> ResourcePath fromClass(Class<T> clazz)
    {
        return new ResourcePath(clazz);
    }

    public ResourcePath(String path)
    {
        super(SnakerLib.MOD.get(), path);
    }

    private <T> ResourcePath(Class<T> clazz)
    {
        super(SnakerLib.MOD.get(), StringStuff.i18nf(clazz.getSimpleName()));
    }
}
