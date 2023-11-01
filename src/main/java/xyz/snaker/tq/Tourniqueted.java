package xyz.snaker.tq;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.client.render.skybox.SkyBoxRenderer;
import xyz.snaker.snakerlib.client.render.skybox.SkyBoxTexture;
import xyz.snaker.tq.client.Shaders;
import xyz.snaker.tq.client.sound.SoundModification;
import xyz.snaker.tq.config.Config;
import xyz.snaker.tq.rego.Levels;
import xyz.snaker.tq.rego.Rego;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Tourniqueted.MODID)
public class Tourniqueted
{
    public static final String NAME = "Tourniqueted";
    public static final String MODID = "tq";

    public Tourniqueted()
    {
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC, "tourniqueted-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC, "tourniqueted-common.toml");
        Rego.initialize();
        SnakerLib.initialize();
        if (FMLEnvironment.dist.isClient()) {
            Shaders.initialize();
            SoundModification.intialize();
            SkyBoxRenderer.createForDimension(Levels.COMATOSE, SkyBoxTexture::new);
        }
    }
}
