package xyz.snaker.snakerlib.utility;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.forge.snapshots.ForgeSnapshotsMod;

/**
 * Created by SnakerBone on 7/08/2023
 **/
public class ForgePath extends ResourceLocation
{
    public ForgePath(String path)
    {
        super(ForgeSnapshotsMod.BRANDING_ID, path);
    }
}
