package snaker.snakerlib.resources;

import net.minecraft.resources.ResourceLocation;
import snaker.snakerlib.SnakerLib;
import snaker.snakerlib.utility.ResourceDirectoryBuilder;

/**
 * Created by SnakerBone on 15/02/2023
 **/
public class SnakerBoneResourceLocation extends ResourceLocation
{
    public SnakerBoneResourceLocation(ResourceDirectoryBuilder builder)
    {
        super(builder.getPath());
    }

    public SnakerBoneResourceLocation(String path)
    {
        super(SnakerLib.SNAKERBONE_MODID, path);
    }

    public <T> SnakerBoneResourceLocation(Class<T> clazz)
    {
        super(SnakerLib.SNAKERBONE_MODID, clazz.getSimpleName().toLowerCase());
    }
}
