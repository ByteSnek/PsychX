package xyz.snaker.tq.level.entity.crystal;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import com.google.common.collect.Sets;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 8/12/2023
 **/
public class ComaCrystalLightningBolt extends Entity
{
    private int life;
    public long seed;
    private int flashes;
    private boolean visualOnly;
    private ServerPlayer cause;
    private final Set<Entity> hitEntities = Sets.newHashSet();
    private int blocksSetOnFire;
    private float damage = 5.0F;

    public ComaCrystalLightningBolt(EntityType<? extends Entity> type, Level level)
    {
        super(type, level);
        this.noCulling = true;
        this.life = 2;
        this.seed = random.nextLong();
        this.flashes = random.nextInt(3) + 1;
    }

    public void setVisualOnly(boolean visualOnly)
    {
        this.visualOnly = visualOnly;
    }

    @Override
    public @NotNull SoundSource getSoundSource()
    {
        return SoundSource.WEATHER;
    }

    @Nullable
    public ServerPlayer getCause()
    {
        return cause;
    }

    public void setCause(@Nullable ServerPlayer cause)
    {
        this.cause = cause;
    }

    private void powerLightningRod()
    {
        Level level = level();

        BlockPos strikePosition = getStrikePosition();
        BlockState strikedBlock = level.getBlockState(strikePosition);

        if (strikedBlock.is(Blocks.LIGHTNING_ROD)) {
            LightningRodBlock lightningRodBlock = (LightningRodBlock) strikedBlock.getBlock();
            lightningRodBlock.onLightningStrike(strikedBlock, level, strikePosition);
        }
    }

    public void setDamage(float damage)
    {
        this.damage = damage;
    }

    public float getDamage()
    {
        return damage;
    }

    @Override
    public void tick()
    {
        super.tick();

        Level level = level();
        BlockPos strikePosition = getStrikePosition();

        double posX = getX();
        double posY = getY();
        double posZ = getZ();

        if (life == 2) {
            if (level.isClientSide()) {
                level.playLocalSound(posX, posY, posZ, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 10000F, 0.8F + random.nextFloat() * 0.2F, false);
                level.playLocalSound(posX, posY, posZ, SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.WEATHER, 2F, 0.5F + random.nextFloat() * 0.2F, false);
            } else {
                Difficulty difficulty = level.getDifficulty();

                if (difficulty == Difficulty.NORMAL || difficulty == Difficulty.HARD) {
                    spawnFire(4);
                }

                powerLightningRod();
                clearCopperOnLightningStrike(level, strikePosition);
                gameEvent(GameEvent.LIGHTNING_STRIKE);
            }
        }

        life--;

        if (life < 0) {
            if (flashes == 0) {
                discard();
            } else if (life < -random.nextInt(10)) {
                flashes--;
                life = 1;
                seed = random.nextLong();
                spawnFire(0);
            }
        }

        if (life >= 0) {
            if (!(level instanceof ServerLevel)) {
                level.setSkyFlashTime(2);
            } else if (!visualOnly) {
                List<Entity> entities = level.getEntities(this, new AABB(getX() - 3., getY() - 3., getZ() - 3., getX() + 3., getY() + 6. + 3., getZ() + 3.), Entity::isAlive);
                hitEntities.addAll(entities);
            }
        }
    }

    private BlockPos getStrikePosition()
    {
        Vec3 position = position();

        return BlockPos.containing(position.x, position.y - 1.0E-6, position.z);
    }

    private void spawnFire(int amount)
    {
        Level level = level();

        if (!visualOnly && !level.isClientSide && level.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            BlockPos pos = blockPosition();
            BlockState state = BaseFireBlock.getState(level, pos);

            if (level.getBlockState(pos).isAir() && state.canSurvive(level, pos)) {
                level.setBlockAndUpdate(pos, state);
                blocksSetOnFire++;
            }

            for (int i = 0; i < amount; ++i) {
                BlockPos offset = pos.offset(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
                state = BaseFireBlock.getState(this.level(), offset);

                if (level.getBlockState(offset).isAir() && state.canSurvive(level, offset)) {
                    level.setBlockAndUpdate(offset, state);
                    blocksSetOnFire++;
                }
            }
        }
    }

    private static void clearCopperOnLightningStrike(Level level, BlockPos pos)
    {
        BlockState struckBlock = level.getBlockState(pos);
        BlockPos struckPosition;
        BlockState transformedBlock;

        if (struckBlock.is(Blocks.LIGHTNING_ROD)) {
            struckPosition = pos.relative(struckBlock.getValue(LightningRodBlock.FACING).getOpposite());
            transformedBlock = level.getBlockState(struckPosition);
        } else {
            struckPosition = pos;
            transformedBlock = struckBlock;
        }

        if (transformedBlock.getBlock() instanceof WeatheringCopper) {
            level.setBlockAndUpdate(struckPosition, WeatheringCopper.getFirst(level.getBlockState(struckPosition)));
            BlockPos.MutableBlockPos mutable = pos.mutable();

            int amount = level.random.nextInt(3) + 3;

            for (int i = 0; i < amount; i++) {
                int randomAmount = level.random.nextInt(8) + 1;

                randomWalkCleaningCopper(level, struckPosition, mutable, randomAmount);
            }
        }
    }

    private static void randomWalkCleaningCopper(Level level, BlockPos pos, BlockPos.MutableBlockPos mutablePos, int amount)
    {
        mutablePos.set(pos);

        for (int i = 0; i < amount; ++i) {
            Optional<BlockPos> optional = randomStepCleaningCopper(level, mutablePos);

            if (optional.isEmpty()) {
                break;
            }

            mutablePos.set(optional.get());
        }
    }

    private static Optional<BlockPos> randomStepCleaningCopper(Level level, BlockPos pos)
    {
        for (BlockPos position : BlockPos.randomInCube(level.random, 10, pos, 1)) {
            BlockState stateAtRandomPos = level.getBlockState(position);

            if (stateAtRandomPos.getBlock() instanceof WeatheringCopper) {
                WeatheringCopper.getPrevious(stateAtRandomPos).ifPresent(state -> level.setBlockAndUpdate(position, state));
                return Optional.of(position);
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double scale)
    {
        double magnitude = 64. * getViewScale();

        return scale < magnitude * magnitude;
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {}

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {}

    public int getBlocksSetOnFire()
    {
        return blocksSetOnFire;
    }

    public Stream<Entity> getHitEntities()
    {
        return hitEntities.stream().filter(Entity::isAlive);
    }
}
