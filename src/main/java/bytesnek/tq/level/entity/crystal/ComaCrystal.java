package bytesnek.tq.level.entity.crystal;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import org.jetbrains.annotations.NotNull;

import bytesnek.hiss.math.Maths;
import bytesnek.snakerlib.SnakerLib;
import bytesnek.tq.level.entity.mob.CrankyFlutterfly;
import bytesnek.tq.level.entity.projectile.CosmicRay;
import bytesnek.tq.rego.Entities;
import bytesnek.tq.rego.Sounds;

/**
 * Created by SnakerBone on 14/11/2023
 **/
public class ComaCrystal extends Entity implements PowerableMob
{
    private static final EntityDataAccessor<Float> SHIELD_HEALTH = SynchedEntityData.defineId(ComaCrystal.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> DEPLETING = SynchedEntityData.defineId(ComaCrystal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DEPLETING_TICKS = SynchedEntityData.defineId(ComaCrystal.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<BlockPos>> BEAM_TARGET = SynchedEntityData.defineId(ComaCrystal.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Boolean> SHOW_BOTTOM = SynchedEntityData.defineId(ComaCrystal.class, EntityDataSerializers.BOOLEAN);

    private final ComaCrystalShield shield;
    private final float shieldHealth = Maths.pow2r(random.nextInt(10000));

    private Class<? extends LivingEntity> targetClass = Player.class;

    public int time;
    private int targetSearchRange;

    private DamageSource damageSource;
    private int depletingTicks;

    public ComaCrystal(EntityType<? extends ComaCrystal> type, Level level)
    {
        super(type, level);
        this.blocksBuilding = true;
        this.time = random.nextInt(100000);
        this.targetSearchRange = 100;
        this.shield = new ComaCrystalShield(shieldHealth);
    }

    public ComaCrystal(Level level, double x, double y, double z)
    {
        this(Entities.COMA_CRYSTAL.get(), level);
        this.setPos(x, y, z);
        this.targetSearchRange = 100;
    }

    public ComaCrystalShield getShield()
    {
        return shield;
    }

    public Class<?> getTargetClass()
    {
        return targetClass;
    }

    public void setTargetClass(Class<? extends LivingEntity> targetClass)
    {
        this.targetClass = targetClass;
    }

    public int getTargetSearchRange()
    {
        return targetSearchRange;
    }

    public void setTargetSearchRange(int targetSearchRange)
    {
        this.targetSearchRange = targetSearchRange;
    }

    public void spawnCrankyFlutterflies()
    {
        for (int i = 0; i < 8; i++) {
            CrankyFlutterfly flutterfly = new CrankyFlutterfly(Entities.CRANKY_FLUTTERFLY.get(), level());
            BlockPos pos = getOnPos().above();

            switch (i) {
                case 0 -> pos = pos.relative(Direction.NORTH, 2).relative(Direction.UP);
                case 1 -> pos = pos.relative(Direction.EAST, 2).relative(Direction.UP);
                case 2 -> pos = pos.relative(Direction.SOUTH, 2).relative(Direction.UP);
                case 3 -> pos = pos.relative(Direction.WEST, 2).relative(Direction.UP);
                case 4 -> pos = pos.relative(Direction.NORTH, 1);
                case 5 -> pos = pos.relative(Direction.EAST, 1);
                case 6 -> pos = pos.relative(Direction.SOUTH, 1);
                case 7 -> pos = pos.relative(Direction.WEST, 1);
            }

            flutterfly.setPos(pos.getX(), pos.getY(), pos.getZ());

            level().addFreshEntity(flutterfly);
        }
    }

    @Override
    protected @NotNull Entity.MovementEmission getMovementEmission()
    {
        return Entity.MovementEmission.NONE;
    }

    @Override
    protected void defineSynchedData()
    {
        getEntityData().define(SHIELD_HEALTH, shieldHealth);
        getEntityData().define(DEPLETING, false);
        getEntityData().define(DEPLETING_TICKS, 0);
        getEntityData().define(BEAM_TARGET, Optional.empty());
        getEntityData().define(SHOW_BOTTOM, true);
    }

    @Override
    public void tick()
    {
        time++;

        Level level = level();
        LivingEntity target = getTarget(targetSearchRange);

        if (isDepleting()) {
            depletingTicks++;
            target = null;

            setDepletingTicks(depletingTicks);

            if (getDepletingTicks() % 60 == 0) {
                remove(Entity.RemovalReason.KILLED);

                level.explode(this, damageSource, null, getX(), getY(), getZ(), 6, true, Level.ExplosionInteraction.BLOCK);

                //spawnCrankyFlutterflies();

                setDepleting(false);
                setDepletingTicks(0);
            }
        }

        if (isAlive() && tickCount % 40 == 0) {
            SoundEvent event = shield.getCrystalSounds().shield();

            if (event != null) {
                playSound(event, 0.375F, 1);
            }
        }

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
                    CosmicRay ray = new CosmicRay(this, level);

                    ray.shoot(distX, distY, distZ, 4, 0);
                    level.addFreshEntity(ray);
                    level.playSound(null, posX, posY, posZ, Sounds.COMA_CRYSTAL_SHOOT.get(), SoundSource.BLOCKS, 0.5F, 1);
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
        if (isInvulnerableTo(source) || isDepleting()) {
            return false;
        } else {
            if (shield.hasHealth()) {
                float shieldHealth = shield.getHealth() / 1000 > 1 ? 1 : shield.getHealth() / 1000;

                if (!level().isClientSide) {
                    shield.subHealth(amount);
                    playSound(shield.getCrystalSounds().hit(), 0.375F, 0.5F + (shieldHealth / 2));
                    SnakerLib.DEVLOGGER.info(shield.getHealth());
                }

            } else {
                setDepleting(true);
                deplete();

                if (!source.is(DamageTypeTags.IS_EXPLOSION)) {
                    damageSource = source.getEntity() != null ? damageSources().explosion(this, source.getEntity()) : null;
                }
            }

            return true;
        }
    }

    private void deplete()
    {
        playSound(shield.getCrystalSounds().deplete(), 0.375F, 1);
    }

    @Nullable
    public LivingEntity getTarget(int value)
    {
        Predicate<LivingEntity> entityPredicate = livingEntity -> !(livingEntity instanceof ServerPlayer serverPlayer) || !serverPlayer.isCreative();

        return level().getNearestEntity(targetClass, TargetingConditions.DEFAULT.selector(entityPredicate), null, getX(), getY(), getZ(), new AABB(getOnPos()).inflate(value));
    }

    public float getShieldHealth()
    {
        return getEntityData().get(SHIELD_HEALTH);
    }

    public boolean isDepleting()
    {
        return getEntityData().get(DEPLETING);
    }

    public void setDepleting(boolean depleting)
    {
        getEntityData().set(DEPLETING, depleting);
    }

    public int getDepletingTicks()
    {
        return getEntityData().get(DEPLETING_TICKS);
    }

    public void setDepletingTicks(int depletingTicks)
    {
        getEntityData().set(DEPLETING_TICKS, depletingTicks);
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
