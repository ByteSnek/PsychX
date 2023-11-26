package bytesnek.tq.level.entity.mob;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.network.NetworkHooks;

import org.jetbrains.annotations.NotNull;

import bytesnek.hiss.math.Maths;
import bytesnek.snakerlib.utility.Entitys;
import bytesnek.tq.level.entity.Comatosian;
import bytesnek.tq.level.world.EntitySpawner;
import bytesnek.tq.rego.Entities;

/**
 * Created by SnakerBone on 16/03/2023
 **/
public class CosmicCreeper extends Monster implements Comatosian, PowerableMob
{
    public static final EntityDataAccessor<Integer> SWELL_DIRECTION = SynchedEntityData.defineId(CosmicCreeper.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> IGNITED = SynchedEntityData.defineId(CosmicCreeper.class, EntityDataSerializers.BOOLEAN);

    private int oldSwell;
    private int swell;
    private short maxSwell = 30;

    private byte explosionRadius = 3;
    private int teleportTime;
    private final int targetScanRadius = 6;

    public CosmicCreeper(EntityType<? extends Monster> type, Level level)
    {
        super(type, level);
    }

    public static boolean spawnRules(EntityType<CosmicCreeper> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random)
    {
        return EntitySpawner.COMATOSE.check(level, pos, random, 75);
    }

    public static AttributeSupplier attributes()
    {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.ATTACK_SPEED, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.25).build();
    }

    public float getSwelling(float partialTicks)
    {
        return Mth.lerp(partialTicks, oldSwell, swell) / (maxSwell - 2);
    }

    public int getSwellDirection()
    {
        return entityData.get(SWELL_DIRECTION);
    }

    public void setSwellDirection(int state)
    {
        entityData.set(SWELL_DIRECTION, state);
    }

    public void explodeCreeper()
    {
        if (level().isClientSide) {
            return;
        }

        double x = getX();
        double y = getY();
        double z = getZ();

        BlockPos pos = new BlockPos.MutableBlockPos(x, y, z);

        ignite();

        level().explode(this, pos.getX(), pos.getY(), pos.getZ(), targetScanRadius, Level.ExplosionInteraction.NONE);
        level().playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FOX_TELEPORT, getSoundSource(), 1, 1);

        spawnCreeperites();
        discard();
    }

    public void spawnCreeperites()
    {
        for (int i = 0; i < 4; i++) {
            CosmicCreeperite creeperite = Entities.COSMIC_CREEPERITE.get().create(level());
            BlockPos pos = getOnPos().above();

            if (creeperite != null) {
                switch (i) {
                    case 0 -> creeperite.moveTo(pos.north(), getYRot(), getXRot());
                    case 1 -> creeperite.moveTo(pos.south(), getYRot(), getXRot());
                    case 2 -> creeperite.moveTo(pos.east(), getYRot(), getXRot());
                    case 3 -> creeperite.moveTo(pos.west(), getYRot(), getXRot());
                }

                creeperite.setHealth(getMaxHealth() / 5);

                level().addFreshEntity(creeperite);
            }
        }
    }

    public boolean isIgnited()
    {
        return entityData.get(IGNITED);
    }

    public void ignite()
    {
        entityData.set(IGNITED, true);
    }

    public void randomTeleport(double x, double y, double z)
    {
        randomTeleport(x, y, z, true);
        level().playSound(null, xo, yo, zo, SoundEvents.FOX_TELEPORT, getSoundSource(), 1, 1);
    }

    public void teleportTargetToCreeper()
    {
        if (!level().isClientSide) {
            BlockPos pos = new BlockPos((int) getX(), (int) getY(), (int) getZ());
            AABB aabb = new AABB(pos).inflate(targetScanRadius);
            List<LivingEntity> targets = level().getEntitiesOfClass(LivingEntity.class, aabb);

            if (!targets.isEmpty()) {
                for (LivingEntity target : targets) {
                    double x = getX();
                    double y = getY();
                    double z = getZ();

                    if (target instanceof ServerPlayer player) {
                        if (!player.isCreative()) {
                            target.teleportTo(x, y, z);
                        }
                    } else {
                        target.teleportTo(x, y, z);
                    }
                }
            }
        }
    }

    @Override
    public int getMaxFallDistance()
    {
        return getTarget() == null ? 3 : 3 + (int) getHealth() - 1;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source)
    {
        boolean fallDamage = super.causeFallDamage(fallDistance, multiplier, source);

        swell += (int) (fallDistance * 1.5F);

        if (swell > maxSwell - 5) {
            swell = maxSwell - 5;
        }

        return fallDamage;
    }

    @Override
    public void defineSynchedData()
    {
        super.defineSynchedData();
        entityData.define(SWELL_DIRECTION, -1);
        entityData.define(IGNITED, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        tag.putShort("Fuse", maxSwell);
        tag.putByte("ExplosionRadius", explosionRadius);
        tag.putBoolean("Ignited", isIgnited());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);

        if (tag.contains("Fuse", 99)) {
            maxSwell = tag.getShort("Fuse");
        }

        if (tag.contains("ExplosionRadius", 99)) {
            explosionRadius = tag.getByte("ExplosionRadius");
        }

        if (tag.getBoolean("Ignited")) {
            ignite();
        }
    }

    @Override
    public void registerGoals()
    {
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new CosmicCreeperSwellGoal(this));
        goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Ocelot.class, 6, 1, 1.2));
        goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Cat.class, 6, 1, 1.2));
        goalSelector.addGoal(4, new MeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(ItemTags.CREEPER_IGNITERS)) {
            SoundEvent event = stack.is(Items.FIRE_CHARGE) ? SoundEvents.FIRECHARGE_USE : SoundEvents.FLINTANDSTEEL_USE;

            level().playSound(player, getX(), getY(), getZ(), event, getSoundSource(), 1, random.nextFloat() * 0.4F + 0.8F);

            if (!level().isClientSide) {
                explodeCreeper();

                if (!stack.isDamageableItem()) {
                    stack.shrink(1);
                } else {
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                }
            }

            return InteractionResult.sidedSuccess(level().isClientSide);
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public void tick()
    {
        LivingEntity target = getTarget();

        if (target == null) {
            teleportTime++;

            if (!isIgnited()) {
                if (teleportTime >= random.nextInt(80, 440) && !Entitys.isEntityMovingXZ(this)) {
                    double x = getRandomX(random.nextInt(targetScanRadius, (targetScanRadius * 2)) * Maths.clamp(random.nextDouble(), 0.875, 3.475)) - 0.5;
                    double y = getY();
                    double z = getRandomZ(random.nextInt(targetScanRadius, (targetScanRadius * 2)) * Maths.clamp(random.nextDouble(), 0.875, 3.475)) - 0.5;

                    randomTeleport(x, y, z);

                    teleportTime = 0;
                }
            }
        } else {
            getLookControl().setLookAt(target);
            if (hasLineOfSight(target)) {
                teleportTargetToCreeper();
            }
        }

        if (swell > 0) {
            if (target instanceof ServerPlayer player) {
                if (!player.isCreative() && !level().isClientSide) {
                    explodeCreeper();
                }
            }
        }

        if (isAlive()) {
            oldSwell = swell;

            if (isIgnited()) {
                setSwellDirection(1);
            }

            int swellDirection = getSwellDirection();

            if (swellDirection > 0 && swell == 0) {
                playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
                gameEvent(GameEvent.PRIME_FUSE);
            }

            swell += swellDirection;

            if (swell < 0) {
                swell = 0;
            }

            if (swell >= maxSwell) {
                swell = maxSwell;
                explodeCreeper();
            }
        }

        super.tick();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isAdaptive()
    {
        return true;
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity)
    {
        return !(entity instanceof ServerPlayer player && player.isCreative());
    }

    @Override
    public boolean isPowered()
    {
        return false;
    }

    @Override
    public void die(@NotNull DamageSource source)
    {
        spawnCreeperites();
        super.die(source);
    }

    static class CosmicCreeperSwellGoal extends Goal
    {
        private final CosmicCreeper creeper;
        private LivingEntity target;

        public CosmicCreeperSwellGoal(CosmicCreeper creeper)
        {
            this.creeper = creeper;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            LivingEntity entity = creeper.getTarget();
            return creeper.getSwellDirection() > 0 || entity != null && creeper.distanceToSqr(entity) < 9;
        }

        @Override
        public void start()
        {
            creeper.getNavigation().stop();
            target = creeper.getTarget();
        }

        @Override
        public void stop()
        {
            target = null;
        }

        @Override
        public boolean requiresUpdateEveryTick()
        {
            return true;
        }

        @Override
        public void tick()
        {
            if (target == null) {
                creeper.setSwellDirection(-1);
            } else if (creeper.distanceToSqr(target) > 49) {
                creeper.setSwellDirection(-1);
            } else if (!creeper.getSensing().hasLineOfSight(target)) {
                creeper.setSwellDirection(-1);
            } else {
                creeper.setSwellDirection(1);
            }
        }
    }
}
