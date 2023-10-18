package xyz.snaker.tq.rego;

import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.level.world.biome.*;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

/**
 * Created by SnakerBone on 23/08/2023
 **/
public class Biomes
{
    public static final ResourceKey<Biome> DELUSION = key("delusion");
    public static final ResourceKey<Biome> ILLUSION = key("illusion");
    public static final ResourceKey<Biome> IMMATERIAL = key("immaterial");
    public static final ResourceKey<Biome> SPECTRAL = key("spectral");
    public static final ResourceKey<Biome> SURREAL = key("surreal");

    public static void bootstrap(BootstapContext<Biome> context)
    {
        context.register(DELUSION, Delusion.create(context));
        context.register(ILLUSION, Illusion.create(context));
        context.register(IMMATERIAL, Immaterial.create(context));
        context.register(SPECTRAL, Spectral.create(context));
        context.register(SURREAL, Surreal.create(context));
    }

    static ResourceKey<Biome> key(String name)
    {
        return ResourceKey.create(Registries.BIOME, new ResourcePath(name));
    }
}
