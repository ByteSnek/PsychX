package xyz.snaker.tq.rego;

import xyz.snaker.snakerlib.utility.ResourcePath;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class Keys
{
    public static final TagKey<Biome> COMATOSE_VEGETAL = TagKey.create(ForgeRegistries.Keys.BIOMES, new ResourcePath("comatose_vegetal"));

    public static final ResourceKey<Level> COMATOSE = ResourceKey.create(Registries.DIMENSION, new ResourcePath("comatose"));
    public static final ResourceKey<Biome> DELUSION = ResourceKey.create(Registries.BIOME, new ResourcePath("delusion"));
    public static final ResourceKey<Biome> ILLUSIVE = ResourceKey.create(Registries.BIOME, new ResourcePath("illusive"));
    public static final ResourceKey<Biome> IMMATERIAL = ResourceKey.create(Registries.BIOME, new ResourcePath("immaterial"));
    public static final ResourceKey<Biome> SPECTRAL = ResourceKey.create(Registries.BIOME, new ResourcePath("spectral"));
    public static final ResourceKey<Biome> SURREAL = ResourceKey.create(Registries.BIOME, new ResourcePath("surreal"));
}
