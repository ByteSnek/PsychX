package bytesnek.tq;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;

import bytesnek.snakerlib.SnakerLib;
import bytesnek.snakerlib.client.render.skybox.SkyBoxRenderer;
import bytesnek.snakerlib.client.render.skybox.SkyBoxTexture;
import bytesnek.tq.client.Shaders;
import bytesnek.tq.client.sound.SoundModification;
import bytesnek.tq.config.Config;
import bytesnek.tq.rego.Levels;
import bytesnek.tq.rego.Rego;

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
