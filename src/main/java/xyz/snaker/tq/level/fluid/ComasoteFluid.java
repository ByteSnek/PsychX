package xyz.snaker.tq.level.fluid;

import xyz.snaker.tq.rego.Blocks;
import xyz.snaker.tq.rego.FluidTypes;
import xyz.snaker.tq.rego.Fluids;
import xyz.snaker.tq.rego.Items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by SnakerBone on 24/09/2023
 **/
public class ComasoteFluid extends FlowingFluid
{
    @Override
    public @NotNull Fluid getFlowing()
    {
        return Fluids.FLOWING_COMA_SOTE.get();
    }

    @Override
    public @NotNull Fluid getSource()
    {
        return Fluids.COMA_SOTE.get();
    }

    @Override
    public @NotNull FluidType getFluidType()
    {
        return FluidTypes.COMASOTE.get();
    }

    @Override
    @SuppressWarnings({"deprecation", "RedundantSuppression"})
    protected boolean canConvertToSource(@NotNull Level level)
    {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state)
    {
        BlockEntity entity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
        Block.dropResources(state, level, pos, entity);
    }

    @Override
    protected int getSlopeFindDistance(@NotNull LevelReader level)
    {
        return 4;
    }

    @Override
    protected int getDropOff(@NotNull LevelReader level)
    {
        return 1;
    }

    @Override
    public @NotNull Item getBucket()
    {
        return Items.COMASOTE.get();
    }

    @Override
    protected boolean canBeReplacedWith(@NotNull FluidState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Fluid fluid, @NotNull Direction direction)
    {
        return direction == Direction.DOWN && state.getFluidType() != Fluids.COMA_SOTE.get().getFluidType();
    }

    @Override
    public int getTickDelay(@NotNull LevelReader level)
    {
        return 8;
    }

    @Override
    protected float getExplosionResistance()
    {
        return 100;
    }

    @Override
    protected @NotNull BlockState createLegacyBlock(@NotNull FluidState state)
    {
        return Blocks.COMASOTE.get().defaultBlockState().setValue(Comasote.LEVEL, getLegacyLevel(state));
    }

    @Override
    public boolean isSource(@NotNull FluidState state)
    {
        return false;
    }

    @Override
    public int getAmount(@NotNull FluidState state)
    {
        return 4;
    }

    @Override
    public boolean isSame(@NotNull Fluid fluid)
    {
        return fluid == Fluids.COMA_SOTE.get() || fluid == Fluids.FLOWING_COMA_SOTE.get();
    }

    @Nullable
    @Override
    protected ParticleOptions getDripParticle()
    {
        return ParticleTypes.SOUL_FIRE_FLAME;
    }

    @Override
    public boolean canConvertToSource(@NotNull FluidState state, @NotNull Level level, @NotNull BlockPos pos)
    {
        return false;
    }

    @Override
    protected void animateTick(@NotNull Level level, @NotNull BlockPos pos, @NotNull FluidState state, @NotNull RandomSource random)
    {
        if (canAnimate(level, pos)) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + 1;
            double z = pos.getZ() + random.nextDouble();

            if (random.nextInt(100) == 0) {
                level.addParticle(ParticleTypes.LAVA, x, y, z, 0, 0, 0);
                level.playLocalSound(x, y, z, SoundEvents.LAVA_POP, SoundSource.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.75F, false);
            }

            if (random.nextInt(200) == 0) {
                level.playLocalSound(x, y, z, SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.65F, false);
            }
        }
    }

    private boolean canAnimate(Level level, BlockPos pos)
    {
        return level.getBlockState(pos.above()).isAir() && !level.getBlockState(pos.above()).isSolidRender(level, pos.above());
    }

    public static class Source extends ComasoteFluid
    {
        @Override
        public int getAmount(@NotNull FluidState state)
        {
            return 8;
        }

        @Override
        public boolean isSource(@NotNull FluidState state)
        {
            return true;
        }
    }

    public static class Flowing extends ComasoteFluid
    {
        @Override
        protected void createFluidStateDefinition(@NotNull StateDefinition.Builder<Fluid, FluidState> builder)
        {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(@NotNull FluidState state)
        {
            return state.getValue(LEVEL);
        }
    }
}
