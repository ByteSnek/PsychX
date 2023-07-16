package snaker.snakerlib.resources;

import net.minecraft.resources.ResourceLocation;
import snaker.snakerlib.SnakerLib;
import snaker.snakerlib.utility.ResourceDirectoryBuilder;

/**
 * Created by SnakerBone on 15/02/2023
 **/
public class Identifier extends ResourceLocation
{
    public Identifier(ResourceDirectoryBuilder builder)
    {
        super(builder.getPath());
    }

    public Identifier(String path)
    {
        super(SnakerLib.TORNIQUETED_MODID, path);
    }

    public <T> Identifier(Class<T> clazz)
    {
        super(SnakerLib.TORNIQUETED_MODID, clazz.getSimpleName().toLowerCase());
    }
}
