package xyz.snaker.tq.rego;

import xyz.snaker.snakerlib.resources.ResourceReference;
import xyz.snaker.tq.level.world.biome.*;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
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
        register(context, DELUSION, new Delusion(context));
        register(context, ILLUSION, new Illusion(context));
        register(context, IMMATERIAL, new Immaterial(context));
        register(context, SPECTRAL, new Spectral(context));
        register(context, SURREAL, new Surreal(context));
    }

    static <T extends TourniquetedBiome> void register(BootstapContext<Biome> context, ResourceKey<Biome> key, T biome)
    {
        context.register(key, biome.create());
    }

    static ResourceKey<Biome> key(String name)
    {
        return ResourceKey.create(Registries.BIOME, new ResourceReference(name));
    }

    public static class Tags
    {
        public static final TagKey<Biome> SPAWNS_FLUTTERFLY = key("spawns_flutterfly");
        public static final TagKey<Biome> SPAWNS_FROLICKER = key("spawns_frolicker");

        static TagKey<Biome> key(String name)
        {
            return TagKey.create(Registries.BIOME, new ResourceReference(name));
        }
    }
}
