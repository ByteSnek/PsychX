package xyz.snaker.tq;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.tq.client.Shaders;
import xyz.snaker.tq.config.TqConfig;
import xyz.snaker.tq.rego.Rego;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Tourniqueted.MODID)
public class Tourniqueted
{
    public static final String MODID = "tq";

    public Tourniqueted()
    {
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, TqConfig.CLIENT_SPEC, "tourniqueted-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TqConfig.COMMON_SPEC, "tourniqueted-common.toml");
        Rego.initialize();
        SnakerLib.initialize();
        if (FMLEnvironment.dist.isClient()) {
            Shaders.initialize();
        }
    }

    public static void main(String[] args)
    {
        System.out.println();
    }
}
