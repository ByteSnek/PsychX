package bytesnek.tq.level.world.tree;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import org.jetbrains.annotations.NotNull;

import bytesnek.tq.rego.FoliagePlacers;

/**
 * Created by SnakerBone on 21/09/2023
 **/
public class IllusiveFoliagePlacer extends FoliagePlacer
{
    public static final Codec<IllusiveFoliagePlacer> CODEC = RecordCodecBuilder.create(instance ->
            foliagePlacerParts(instance)
                    .and(instance.group(IntProvider.codec(4, 16)
                                    .fieldOf("height")
                                    .forGetter(placer -> placer.height),
                            Codec.floatRange(0.0F, 1.0F)
                                    .fieldOf("wide_bottom_layer_hole_chance")
                                    .forGetter(placer -> placer.wideBottomLayerHoleChance),
                            Codec.floatRange(0.0F, 1.0F)
                                    .fieldOf("corner_hole_chance")
                                    .forGetter(placer -> placer.wideBottomLayerHoleChance),
                            Codec.floatRange(0.0F, 1.0F)
                                    .fieldOf("hanging_leaves_chance")
                                    .forGetter(placer -> placer.hangingLeavesChance),
                            Codec.floatRange(0.0F, 1.0F)
                                    .fieldOf("hanging_leaves_extension_chance")
                                    .forGetter(placer -> placer.hangingLeavesExtensionChance)))
                    .apply(instance, IllusiveFoliagePlacer::new));

    private final IntProvider height;

    private final float wideBottomLayerHoleChance;
    private final float cornerHoleChance;
    private final float hangingLeavesChance;
    private final float hangingLeavesExtensionChance;

    public IllusiveFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider height, float wideBottomLayerHoleChance, float cornerHoleChance, float hangingLeavesChance, float hangingLeavesExtensionChance)
    {
        super(radius, offset);
        this.height = height;
        this.wideBottomLayerHoleChance = wideBottomLayerHoleChance;
        this.cornerHoleChance = cornerHoleChance;
        this.hangingLeavesChance = hangingLeavesChance;
        this.hangingLeavesExtensionChance = hangingLeavesExtensionChance;
    }

    @Override
    protected @NotNull FoliagePlacerType<?> type()
    {
        return FoliagePlacers.ILLUSIVE.get();
    }

    @Override
    protected void createFoliage(@NotNull LevelSimulatedReader level, @NotNull FoliagePlacer.FoliageSetter blockSetter, @NotNull RandomSource random, @NotNull TreeConfiguration config, int maxFreeTreeHeight, @NotNull FoliagePlacer.FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset)
    {
        boolean large = attachment.doubleTrunk();

        BlockPos pos = attachment.pos().above(offset);

        int range = foliageRadius + attachment.radiusOffset() - 1;

        placeLeavesRow(level, blockSetter, random, config, pos, range - 2, foliageHeight - 3, large);
        placeLeavesRow(level, blockSetter, random, config, pos, range - 1, foliageHeight - 4, large);

        for (int height = foliageHeight - 5; height >= 0; --height) {
            placeLeavesRow(level, blockSetter, random, config, pos, range, height, large);
        }

        placeLeavesRowWithHangingLeavesBelow(level, blockSetter, random, config, pos, range, -1, large, hangingLeavesChance, hangingLeavesExtensionChance);
        placeLeavesRowWithHangingLeavesBelow(level, blockSetter, random, config, pos, range - 1, -2, large, hangingLeavesChance, hangingLeavesExtensionChance);
    }

    @Override
    public int foliageHeight(@NotNull RandomSource random, int foliageHeight, @NotNull TreeConfiguration config)
    {
        return height.sample(random);
    }

    @Override
    protected boolean shouldSkipLocation(@NotNull RandomSource random, int localX, int localY, int localZ, int range, boolean large)
    {
        if (localY == -1 && (localX == range || localZ == range) && random.nextFloat() < wideBottomLayerHoleChance) {
            return true;
        } else {
            boolean isHorizontal = localX == range && localZ == range;
            boolean isLargeRange = range > 2;

            if (isLargeRange) {
                return isHorizontal || localX + localZ > range * 2 - 2 && random.nextFloat() < cornerHoleChance;
            } else {
                return isHorizontal && random.nextFloat() < cornerHoleChance;
            }
        }
    }
}
