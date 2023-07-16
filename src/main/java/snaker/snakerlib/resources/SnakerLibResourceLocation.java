package snaker.snakerlib.resources;

import net.minecraft.resources.ResourceLocation;
import snaker.snakerlib.SnakerLib;
import snaker.snakerlib.utility.ResourceDirectoryBuilder;

/**
 * Created by SnakerBone on 8/07/2023
 **/
public class SnakerLibResourceLocation extends ResourceLocation
{
    public SnakerLibResourceLocation(ResourceDirectoryBuilder builder)
    {
        super(builder.getPath());
    }

    public SnakerLibResourceLocation(String path)
    {
        super(SnakerLib.MODID, path);
    }

    public <T> SnakerLibResourceLocation(Class<T> clazz)
    {
        super(SnakerLib.MODID, clazz.getSimpleName().toLowerCase());
    }
}
