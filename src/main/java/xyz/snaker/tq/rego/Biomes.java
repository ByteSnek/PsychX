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
    public static final ResourceKey<Biome> DELUSION = ResourceKey.create(Registries.BIOME, new ResourcePath("delusion"));
    public static final ResourceKey<Biome> ILLUSION = ResourceKey.create(Registries.BIOME, new ResourcePath("illusion"));
    public static final ResourceKey<Biome> IMMATERIAL = ResourceKey.create(Registries.BIOME, new ResourcePath("immaterial"));
    public static final ResourceKey<Biome> SPECTRAL = ResourceKey.create(Registries.BIOME, new ResourcePath("spectral"));
    public static final ResourceKey<Biome> SURREAL = ResourceKey.create(Registries.BIOME, new ResourcePath("surreal"));

    public static void biomes(BootstapContext<Biome> context)
    {
        context.register(DELUSION, Delusion.create(context));
        context.register(ILLUSION, Illusion.create(context));
        context.register(IMMATERIAL, Immaterial.create(context));
        context.register(SPECTRAL, Spectral.create(context));
        context.register(SURREAL, Surreal.create(context));
    }
}
