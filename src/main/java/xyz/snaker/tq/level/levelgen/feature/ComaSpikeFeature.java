package xyz.snaker.tq.level.levelgen.feature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import com.mojang.serialization.Codec;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 2/12/2023
 **/
public class ComaSpikeFeature extends Feature<NoneFeatureConfiguration>
{
    public ComaSpikeFeature(Codec<NoneFeatureConfiguration> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context)
    {
        return false;
    }
}
