package snaker.tq.utility;

import net.minecraft.resources.ResourceLocation;
import snaker.tq.Tourniqueted;

/**
 * Created by SnakerBone on 15/02/2023
 **/
public class ResourcePath extends ResourceLocation
{
    public ResourcePath(String path)
    {
        super(Tourniqueted.MODID, path);
    }
}
