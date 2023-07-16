package snaker.tq;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLEnvironment;
import snaker.snakerlib.SnakerLib;
import snaker.tq.client.BossBarHandler;
import snaker.tq.client.Shaders;
import snaker.tq.config.TqConfig;
import snaker.tq.rego.Rego;

@Mod(Torniqueted.MODID)
public class Torniqueted
{
    public static final String MODID = "tq";

    public Torniqueted()
    {
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, TqConfig.CLIENT_SPEC, "torniqueted-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TqConfig.COMMON_SPEC, "torniqueted-common.toml");
        Rego.initialize();
        SnakerLib.initialize();
        if (FMLEnvironment.dist.isClient()) {
            Shaders.initialize();
            BossBarHandler.initialize();
        }
    }
}
