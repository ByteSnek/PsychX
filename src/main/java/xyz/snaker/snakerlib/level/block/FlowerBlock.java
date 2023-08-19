package xyz.snaker.snakerlib.level.block;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 19/08/2023
 **/
public class FlowerBlock extends PlantBlock implements SuspiciousEffectHolder
{
    /**
     * The voxel shape of this block
     **/
    private static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 10, 11);

    /**
     * The suspicious stew effect
     **/
    private final Supplier<MobEffect> effectSupplier;

    /**
     * The duration of the suspicious stew effect
     **/
    private final int effectDuration;

    public FlowerBlock(Supplier<MobEffect> effectSupplier, int effectDuration, Properties properties, TagKey<Block> allowedBlocks, boolean allowDirt)
    {
        super(properties, allowedBlocks, SHAPE, allowDirt);
        this.effectSupplier = effectSupplier;
        this.effectDuration = effectDuration;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context)
    {
        Vec3 offset = state.getOffset(level, pos);
        return shape.move(offset.x, offset.y, offset.z);
    }

    @Override
    public @NotNull MobEffect getSuspiciousEffect()
    {
        return effectSupplier.get();
    }

    @Override
    public int getEffectDuration()
    {
        if (effectSupplier.get() == null && !effectSupplier.get().isInstantenous()) {
            return effectDuration * 20;
        }
        return effectDuration;
    }
}
