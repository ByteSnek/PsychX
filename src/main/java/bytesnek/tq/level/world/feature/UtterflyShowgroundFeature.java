package bytesnek.tq.level.world.feature;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.AABB;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import org.jetbrains.annotations.NotNull;

import bytesnek.tq.level.entity.ComaCrystal;
import bytesnek.tq.level.world.feature.configuration.UtterflyShowgroundConfiguration;
import bytesnek.tq.rego.Blocks;
import bytesnek.tq.rego.Entities;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import static net.minecraft.world.level.block.Blocks.AIR;
import static net.minecraft.world.level.block.Blocks.BEDROCK;

/**
 * Created by SnakerBone on 15/11/2023
 **/
public class UtterflyShowgroundFeature extends Feature<UtterflyShowgroundConfiguration>
{
    public static final int NUMBER_OF_TOWERS = 10;
    private static final int TOWER_DISTANCE = 42;

    private static final LoadingCache<Long, List<Tower>> SPIKE_CACHE = CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.MINUTES).build(new UtterflyShowgroundFeature.SpikeCacheLoader());

    public UtterflyShowgroundFeature(Codec<UtterflyShowgroundConfiguration> codec)
    {
        super(codec);
    }

    public static List<Tower> getTowersForLevel(WorldGenLevel level)
    {
        RandomSource random = RandomSource.create(level.getSeed());
        long key = random.nextLong() & 65535L;
        return SPIKE_CACHE.getUnchecked(key);
    }

    public boolean place(FeaturePlaceContext<UtterflyShowgroundConfiguration> context)
    {
        UtterflyShowgroundConfiguration config = context.config();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        List<Tower> towers = config.getTowers();

        if (towers.isEmpty()) {
            towers = getTowersForLevel(level);
        }

        for (Tower tower : towers) {
            if (tower.isCenterWithinChunk(origin)) {
                placeTower(level, random, config, tower);
            }
        }

        return true;
    }

    private void placeTower(ServerLevelAccessor level, RandomSource random, UtterflyShowgroundConfiguration config, Tower tower)
    {
        int radius = tower.getRadius();

        for (BlockPos pos : BlockPos.betweenClosed(new BlockPos(tower.getCenterX() - radius, level.getMinBuildHeight(), tower.getCenterZ() - radius), new BlockPos(tower.getCenterX() + radius, tower.getHeight() + NUMBER_OF_TOWERS, tower.getCenterZ() + radius))) {
            if (pos.distToLowCornerSqr(tower.getCenterX(), pos.getY(), tower.getCenterZ()) <= (double) (radius * radius + 1) && pos.getY() < tower.getHeight()) {
                setBlock(level, pos, Blocks.COMASTONE.get().defaultBlockState());
            } else if (pos.getY() > 65) {
                setBlock(level, pos, AIR.defaultBlockState());
            }
        }

        ComaCrystal crystal = Entities.COMA_CRYSTAL.get().create(level.getLevel());

        if (crystal != null) {
            crystal.setBeamTarget(config.getTarget());
            crystal.setInvulnerable(config.isInvulnerable());
            crystal.moveTo(tower.getCenterX() + 0.5, tower.getHeight() + 1, tower.getCenterZ() + 0.5, random.nextFloat() * 360F, 0F);

            level.addFreshEntity(crystal);

            setBlock(level, new BlockPos(tower.getCenterX(), tower.getHeight(), tower.getCenterZ()), BEDROCK.defaultBlockState());
        }
    }

    public static class Tower
    {
        public static final Codec<Tower> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.INT.fieldOf("centerX")
                                .orElse(0)
                                .forGetter(tower -> tower.centerX),
                        Codec.INT.fieldOf("centerZ")
                                .orElse(0)
                                .forGetter(tower -> tower.centerZ),
                        Codec.INT.fieldOf("radius")
                                .orElse(0)
                                .forGetter(tower -> tower.radius),
                        Codec.INT.fieldOf("height")
                                .orElse(0)
                                .forGetter(tower -> tower.height),
                        Codec.BOOL.fieldOf("guarded")
                                .orElse(false)
                                .forGetter(tower -> tower.guarded)
                ).apply(instance, Tower::new)
        );

        private final int centerX;
        private final int centerZ;
        private final int radius;
        private final int height;
        private final boolean guarded;
        private final AABB topBoundingBox;

        public Tower(int centerX, int centerZ, int radius, int height, boolean guarded)
        {
            this.centerX = centerX;
            this.centerZ = centerZ;
            this.radius = radius;
            this.height = height;
            this.guarded = guarded;
            this.topBoundingBox = new AABB(centerX - radius, DimensionType.MIN_Y, centerZ - radius, centerX + radius, DimensionType.MAX_Y, centerZ + radius);
        }

        public boolean isCenterWithinChunk(BlockPos pos)
        {
            return SectionPos.blockToSectionCoord(pos.getX()) == SectionPos.blockToSectionCoord(centerX) && SectionPos.blockToSectionCoord(pos.getZ()) == SectionPos.blockToSectionCoord(centerZ);
        }

        public int getCenterX()
        {
            return centerX;
        }

        public int getCenterZ()
        {
            return centerZ;
        }

        public int getRadius()
        {
            return radius;
        }

        public int getHeight()
        {
            return height;
        }

        public boolean isGuarded()
        {
            return guarded;
        }

        public AABB getTopBoundingBox()
        {
            return topBoundingBox;
        }
    }

    public static class SpikeCacheLoader extends CacheLoader<Long, List<Tower>>
    {
        @Override
        public @NotNull List<Tower> load(@NotNull Long key)
        {
            IntArrayList towerPositions = Util.toShuffledList(IntStream.range(0, NUMBER_OF_TOWERS), RandomSource.create(key));
            List<Tower> towers = Lists.newArrayList();

            for (int position = 0; position < NUMBER_OF_TOWERS; ++position) {
                int posX = Mth.floor(TOWER_DISTANCE * Math.cos(2D * (-Math.PI + (Math.PI / NUMBER_OF_TOWERS) * (double) position)));
                int posZ = Mth.floor(TOWER_DISTANCE * Math.sin(2D * (-Math.PI + (Math.PI / NUMBER_OF_TOWERS) * (double) position)));

                int boundingBox = towerPositions.getInt(position);

                int radius = 2 + boundingBox / 3;
                int height = 76 + boundingBox * 3;

                boolean guarded = boundingBox == 1 || boundingBox == 2;

                towers.add(new Tower(posX, posZ, radius, height, guarded));
            }

            return towers;
        }
    }
}
