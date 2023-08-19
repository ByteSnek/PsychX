package xyz.snaker.snakerlib.level.world;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by SnakerBone on 18/08/2023
 **/
public class SimpleTreeGrower extends AbstractTreeGrower
{
    /**
     * The configured feature of this tree grower
     **/
    private final ResourceKey<ConfiguredFeature<?, ?>> key;

    public SimpleTreeGrower(ResourceKey<ConfiguredFeature<?, ?>> key)
    {
        this.key = key;
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean hasFlowers)
    {
        return key;
    }
}
