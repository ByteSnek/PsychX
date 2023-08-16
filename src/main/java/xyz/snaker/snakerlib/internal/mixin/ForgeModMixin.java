package xyz.snaker.snakerlib.internal.mixin;

import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.filters.VanillaPacketSplitter;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Created by SnakerBone on 10/08/2023
 **/
@Mixin(ForgeMod.class)
public class ForgeModMixin
{
    @Overwrite
    public void preInit(FMLCommonSetupEvent event)
    {
        VanillaPacketSplitter.register();
    }
}
