package bytesnek.tq.level.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import com.mojang.serialization.Codec;

import static net.minecraft.world.level.block.Blocks.*;

/**
 * Created by SnakerBone on 15/11/2023
 **/
public class ComaSpikeFeature extends Feature<NoneFeatureConfiguration>
{
    public ComaSpikeFeature(Codec<NoneFeatureConfiguration> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context)
    {
        BlockPos origin = context.origin();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();

        return placeIsland(level, random, origin, 3);
    }

    public boolean placeIsland(WorldGenLevel level, RandomSource random, BlockPos origin, int bound)
    {
        float size = random.nextInt(bound) + 4.0F;

        for (int y = 0; size > 0.5F; y--) {
            for (int x = Mth.floor(-size); x <= Mth.ceil(size); ++x) {
                for (int z = Mth.floor(-size); z <= Mth.ceil(size); ++z) {
                    if ((float) (x * x + z * z) <= (size + 1F) * (size + 1F)) {
                        if (random.nextInt(4) == 0) {
                            setBlock(level, origin.offset(x, y, z), RED_MUSHROOM_BLOCK.defaultBlockState());
                        } else if (random.nextInt(4) == 0) {
                            setBlock(level, origin.offset(x, y, z), OBSIDIAN.defaultBlockState());
                        } else {
                            setBlock(level, origin.offset(x, y, z), SNOW_BLOCK.defaultBlockState());
                        }
                    }
                }
            }

            size -= random.nextInt(2) + 0.5F;
        }

        return true;
    }
}
