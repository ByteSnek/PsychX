package bytesnek.tq.level.entity;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import org.jetbrains.annotations.NotNull;

import bytesnek.tq.level.entity.projectile.CosmicRay;
import bytesnek.tq.rego.Entities;
import bytesnek.tq.rego.Sounds;

/**
 * Created by SnakerBone on 14/11/2023
 **/
public class ComaCrystal extends Entity implements PowerableMob
{
    private static final EntityDataAccessor<Optional<UUID>> TARGET = SynchedEntityData.defineId(ComaCrystal.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Optional<BlockPos>> BEAM_TARGET = SynchedEntityData.defineId(ComaCrystal.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Boolean> SHOW_BOTTOM = SynchedEntityData.defineId(ComaCrystal.class, EntityDataSerializers.BOOLEAN);

    public int time;
    private int targetSearchRange;

    public ComaCrystal(EntityType<? extends ComaCrystal> type, Level level)
    {
        super(type, level);
        this.blocksBuilding = true;
        this.time = random.nextInt(100000);
        this.targetSearchRange = 100;
    }

    public ComaCrystal(Level level, double x, double y, double z)
    {
        this(Entities.COMA_CRYSTAL.get(), level);
        this.setPos(x, y, z);
        this.targetSearchRange = 100;
    }

    public int getTargetSearchRange()
    {
        return targetSearchRange;
    }

    public void setTargetSearchRange(int targetSearchRange)
    {
        this.targetSearchRange = targetSearchRange;
    }

    @Override
    protected @NotNull Entity.MovementEmission getMovementEmission()
    {
        return Entity.MovementEmission.NONE;
    }

    @Override
    protected void defineSynchedData()
    {
        getEntityData().define(TARGET, Optional.empty());
        getEntityData().define(BEAM_TARGET, Optional.empty());
        getEntityData().define(SHOW_BOTTOM, true);
    }

    @Override
    public void tick()
    {
        time++;

        Level level = level();
        LivingEntity target = getTarget(targetSearchRange);

        if (target != null) {
            double posX = getX();
            double posY = getY();
            double posZ = getZ();

            double targetX = target.getX();
            double targetY = target.getY();
            double targetZ = target.getZ();

            if (target.distanceToSqr(posX, posY, posZ) < 4096) {
                double distX = targetX - getX();
                double distY = targetY - getY();
                double distZ = targetZ - getZ();

                if (tickCount % 60 == 0) {
                    RandomSource random = RandomSource.create();
                    CosmicRay ray = new CosmicRay(Entities.COSMIC_RAY.get(), level);

                    ray.shoot(distX, distY, distZ, 4, 0);
                    level.addFreshEntity(ray);
                    level.playSound(null, posX, posY, posZ, Sounds.COMA_CRYSTAL_SHOOT.get(), SoundSource.BLOCKS, 0.5F, (random.nextFloat() - random.nextFloat()) * 0.5F + 1);
                }
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag)
    {
        if (getBeamTarget() != null) {
            tag.put("BeamTarget", NbtUtils.writeBlockPos(getBeamTarget()));
        }

        tag.putBoolean("ShowBottom", showsBottom());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag)
    {
        if (tag.contains("BeamTarget", 10)) {
            setBeamTarget(NbtUtils.readBlockPos(tag.getCompound("BeamTarget")));
        }

        if (tag.contains("ShowBottom", 1)) {
            setShowBottom(tag.getBoolean("ShowBottom"));
        }
    }

    @Override
    public boolean isPickable()
    {
        return true;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount)
    {
        if (isInvulnerableTo(source)) {
            return false;
        } else {
            if (!isRemoved() && !level().isClientSide) {
                remove(Entity.RemovalReason.KILLED);

                if (!source.is(DamageTypeTags.IS_EXPLOSION)) {
                    DamageSource src = source.getEntity() != null ? damageSources().explosion(this, source.getEntity()) : null;

                    level().explode(this, src, null, getX(), getY(), getZ(), 6, false, Level.ExplosionInteraction.BLOCK);
                }
            }

            return true;
        }
    }

    @Nullable
    public LivingEntity getTarget(int value)
    {
        return level().getNearestEntity(LivingEntity.class, TargetingConditions.DEFAULT, null, getX(), getY(), getZ(), new AABB(getOnPos()).inflate(value));
    }

    public void setTargetUUID(@Nullable UUID uuid)
    {
        getEntityData().set(TARGET, Optional.ofNullable(uuid));
    }

    @Nullable
    public UUID getTargetUUID()
    {
        return getEntityData().get(TARGET).orElse(null);
    }

    public void setBeamTarget(@Nullable BlockPos pos)
    {
        getEntityData().set(BEAM_TARGET, Optional.ofNullable(pos));
    }

    @Nullable
    public BlockPos getBeamTarget()
    {
        return getEntityData().get(BEAM_TARGET).orElse(null);
    }

    public void setShowBottom(boolean pShowBottom)
    {
        getEntityData().set(SHOW_BOTTOM, pShowBottom);
    }

    public boolean showsBottom()
    {
        return getEntityData().get(SHOW_BOTTOM);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance)
    {
        return super.shouldRenderAtSqrDistance(distance) || getBeamTarget() != null;
    }

    @Override
    public ItemStack getPickResult()
    {
        return new ItemStack(Items.END_CRYSTAL);
    }

    @Override
    public boolean isPowered()
    {
        return true;
    }
}
