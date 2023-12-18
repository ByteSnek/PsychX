package xyz.snaker.tq;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.client.render.skybox.SkyBoxRenderer;
import xyz.snaker.snakerlib.client.render.skybox.SkyBoxTexture;
import xyz.snaker.tq.client.Shaders;
import xyz.snaker.tq.client.sound.SoundModification;
import xyz.snaker.tq.config.Config;
import xyz.snaker.tq.rego.Levels;
import xyz.snaker.tq.rego.Rego;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(Tourniqueted.MODID)
public class Tourniqueted
{
    public static final String NAME = "Tourniqueted";
    public static final String MODID = "tq";

    public Tourniqueted()
    {
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
