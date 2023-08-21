package xyz.snaker.tq.utility.tools;

import java.util.List;

import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

/**
 * Created by SnakerBone on 21/08/2023
 **/
public interface BiomeTagProviderTools<T extends TagsProvider<Biome>>
{
    T getInstance();

    default void add(TagKey<Biome> key, List<ResourceKey<Biome>> values)
    {
        for (ResourceKey<Biome> value : values) {
            getInstance().tag(key).add(value);
        }
    }
}
