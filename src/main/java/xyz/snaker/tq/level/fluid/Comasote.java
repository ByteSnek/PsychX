package xyz.snaker.tq.level.fluid;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import xyz.snaker.snakerlib.utility.tools.ReflectiveStuff;
import xyz.snaker.tq.level.entity.Comatosian;
import xyz.snaker.tq.rego.Sounds;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 26/09/2023
 **/
public class Comasote extends LiquidBlock
{
    public Comasote(Supplier<? extends FlowingFluid> fluid, Properties properties)
    {
        super(fluid, properties);
    }

    @Override
    public boolean isBurning(BlockState state, BlockGetter level, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean isFireSource(BlockState state, LevelReader level, BlockPos pos, Direction direction)
    {
        return false;
    }

    @Override
    public boolean canEntityDestroy(BlockState state, BlockGetter level, BlockPos pos, Entity entity)
    {
        return false;
    }

    @Override
    @ApiStatus.OverrideOnly
    @SuppressWarnings("deprecation")
    public boolean canBeReplaced(@NotNull BlockState state, @NotNull Fluid fluid)
    {
        return false;
    }

    @Override
    @ApiStatus.OverrideOnly
    @SuppressWarnings("deprecation")
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity)
    {
        DamageSource generic = level.damageSources().generic();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        if (entity instanceof LivingEntity livingEntity) {
            if (!level.isClientSide) {
                if (livingEntity instanceof Comatosian comatosian && comatosian.isAdaptive().getValue()) {
                    return;
                }
                entity.hurt(generic, random.nextFloat(livingEntity.getMaxHealth() / random.nextInt(5, 10)));
            }
        } else {
            if (random.nextInt(50) == 0) {
                if (level.isClientSide) {
                    for (Direction direction : Direction.values()) {
                        spawnAndFlingRandomParticles(level, pos, level.random, direction);
                    }
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), Sounds.CLICK.get(), SoundSource.BLOCKS, 1, 1, false);
                } else {
                    entity.discard();
                }
            }
        }

        if (!level.isClientSide) {
            if (level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT) && !level.dimensionType().hasFixedTime()) {
                if (level instanceof ServerLevel serverLevel) {
                    long time = level.getDayTime();
                    time += level.random.nextInt(8);
                    serverLevel.setDayTime(time);
                }
            }
        }
    }

    private void spawnAndFlingRandomParticles(Level level, BlockPos pos, RandomSource random, Direction direction)
    {
        Direction.Axis axis = direction.getAxis();

        double x = axis == Direction.Axis.X ? 0.5 + 0.5625 * direction.getStepX() : random.nextFloat();
        double y = axis == Direction.Axis.Y ? 0.5 + 0.5625 * direction.getStepY() : random.nextFloat();
        double z = axis == Direction.Axis.Z ? 0.5 + 0.5625 * direction.getStepZ() : random.nextFloat();

        SimpleParticleType type = ReflectiveStuff.getRandomFieldInClass(ParticleTypes.class, o -> o instanceof SimpleParticleType, SimpleParticleType[]::new);

        level.addParticle(type, pos.getX() + x, pos.getY() + y, pos.getZ() + z, random.nextFloat(), random.nextFloat(), random.nextFloat());
        level.addParticle(type, pos.getX() + x, pos.getY() + y, pos.getZ() + z, random.nextFloat(), -random.nextFloat(), -random.nextFloat());
        level.addParticle(type, pos.getX() + x, pos.getY() + y, pos.getZ() + z, -random.nextFloat(), random.nextFloat(), -random.nextFloat());
        level.addParticle(type, pos.getX() + x, pos.getY() + y, pos.getZ() + z, -random.nextFloat(), -random.nextFloat(), random.nextFloat());
    }
}
