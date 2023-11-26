package bytesnek.tq.level.block;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
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

import bytesnek.hiss.sneaky.Reflection;
import bytesnek.hiss.sneaky.Sneaky;
import bytesnek.hiss.thread.UncaughtExceptionThread;
import bytesnek.tq.level.entity.Comatosian;
import bytesnek.tq.rego.Entities;
import bytesnek.tq.rego.Sounds;

/**
 * Created by SnakerBone on 26/09/2023
 **/
public class ComasoteBlock extends LiquidBlock
{
    private final Map<EntityType<? extends LivingEntity>, Supplier<EntityType<? extends LivingEntity>>> entityMap = Util.make(new HashMap<>(), map ->
    {
        map.put(EntityType.BEE, Sneaky.cast(Entities.FROLICKER));
        map.put(EntityType.BLAZE, Sneaky.cast(Entities.FLARE));
        map.put(EntityType.CREEPER, Sneaky.cast(Entities.COSMIC_CREEPER));
        map.put(EntityType.GHAST, Sneaky.cast(Entities.SNIPE));
    });

    public ComasoteBlock(Supplier<? extends FlowingFluid> fluid, Properties properties)
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
                if (livingEntity.isAlive()) {
                    if (livingEntity instanceof Bee bee) {
                        replaceEntity(level, bee);
                    }
                    if (livingEntity instanceof Blaze blaze) {
                        replaceEntity(level, blaze);
                    }
                    if (livingEntity instanceof Creeper creeper) {
                        replaceEntity(level, creeper);
                    }
                    if (livingEntity instanceof Ghast ghast) {
                        replaceEntity(level, ghast);
                    }
                }

                if (livingEntity instanceof Comatosian comatosian && comatosian.isAdaptive()) {
                    return;
                }

                if (livingEntity.hurt(generic, Float.NaN)) {
                    livingEntity.setHealth(livingEntity.getMaxHealth());
                    entity.invulnerableTime = Mth.clamp(entity.invulnerableTime, 10, 15);
                }
            }
        } else {
            if (random.nextInt(50) == 0) {
                if (level.isClientSide) {
                    for (Direction direction : Direction.values()) {
                        spawnAndFlingRandomParticles(level, pos, level.random, direction);
                    }
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), Sounds.COMASOTE.get(), SoundSource.BLOCKS, 1, 1, false);
                } else {
                    entity.discard();
                }
            }
        }

        if (!level.isClientSide) {
            if (level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT) && !level.dimensionType().hasFixedTime()) {
                if (level instanceof ServerLevel serverLevel && entity instanceof ServerPlayer) {
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

        SimpleParticleType type = Reflection.getRandomFieldInClass(ParticleTypes.class, o -> o instanceof SimpleParticleType, SimpleParticleType[]::new);

        level.addParticle(type, pos.getX() + x, pos.getY() + y, pos.getZ() + z, random.nextFloat(), random.nextFloat(), random.nextFloat());
        level.addParticle(type, pos.getX() + x, pos.getY() + y, pos.getZ() + z, random.nextFloat(), -random.nextFloat(), -random.nextFloat());
        level.addParticle(type, pos.getX() + x, pos.getY() + y, pos.getZ() + z, -random.nextFloat(), random.nextFloat(), -random.nextFloat());
        level.addParticle(type, pos.getX() + x, pos.getY() + y, pos.getZ() + z, -random.nextFloat(), -random.nextFloat(), random.nextFloat());
    }

    private void replaceEntity(Level level, LivingEntity killed)
    {
        EntityType<?> killedType = killed.getType();
        LivingEntity fresh = entityMap.get(killedType).get().create(level);

        if (fresh == null) {
            RuntimeException exception = new RuntimeException("Invalid entity for replacement: " + killedType.getDescription().getString());
            UncaughtExceptionThread.createAndRun(exception);
            return;
        }

        double killedX = killed.getX();
        double killedY = killed.getY();
        double killedZ = killed.getZ();

        float killedXRot = killed.getXRot();
        float killedYRot = killed.getYRot();

        float health = fresh.getMaxHealth() * killed.getHealth() / killed.getMaxHealth();

        fresh.moveTo(killedX, killedY, killedZ, killedYRot, killedXRot);
        fresh.yBodyRot = killed.yBodyRot;
        fresh.setHealth(health);

        killed.discard();

        level.addFreshEntity(fresh);
    }
}
