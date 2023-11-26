package bytesnek.tq.level.entity.mob;

import java.util.EnumSet;

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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PowerableMob;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.network.NetworkHooks;

import org.jetbrains.annotations.NotNull;

import bytesnek.hiss.math.Maths;
import bytesnek.snakerlib.utility.Entitys;
import bytesnek.tq.level.entity.Comatosian;

/**
 * Created by SnakerBone on 16/03/2023
 **/
public class CosmicCreeperite extends Monster implements Comatosian, PowerableMob
{
    public static final EntityDataAccessor<Integer> SWELL_DIRECTION = SynchedEntityData.defineId(CosmicCreeperite.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> POWERED = SynchedEntityData.defineId(CosmicCreeperite.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IGNITED = SynchedEntityData.defineId(CosmicCreeperite.class, EntityDataSerializers.BOOLEAN);

    private int oldSwell;
    private int swell;
    private short maxSwell = 30;

    private byte explosionRadius = 3;
    private int teleportTime;

    public CosmicCreeperite(EntityType<? extends Monster> type, Level level)
    {
        super(type, level);
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
        return Mth.lerp(partialTicks, (float) oldSwell, (float) swell) / (float) (maxSwell - 2);
    }

    public int getSwellDirection()
    {
        return entityData.get(SWELL_DIRECTION);
    }

    public void setSwellDirection(int state)
    {
        entityData.set(SWELL_DIRECTION, state);
    }

    public void fakeOutExplosion()
    {
        if (level().isClientSide) {
            return;
        }

        level().playSound(null, getX(), getY(), getZ(), SoundEvents.GENERIC_EXPLODE, getSoundSource(), 1, 2);
        discard();
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

    public void teleportCreeperToTarget()
    {
        LivingEntity target = getTarget();
        if (target != null) {
            if (hasLineOfSight(target) && target.isAlive()) {
                double x = target.getX();
                double y = target.getY();
                double z = target.getZ();
                teleportTo(x, y, z);
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
        entityData.define(POWERED, false);
        entityData.define(IGNITED, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);

        if (entityData.get(POWERED)) {
            tag.putBoolean("Powered", true);
        }

        tag.putShort("Fuse", maxSwell);
        tag.putByte("ExplosionRadius", explosionRadius);
        tag.putBoolean("Ignited", isIgnited());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);

        entityData.set(POWERED, tag.getBoolean("Powered"));

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
        goalSelector.addGoal(2, new CosmicCreeperiteSwellGoal(this));
        goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Ocelot.class, 6, 1, 1.2));
        goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Cat.class, 6, 1, 1.2));
        goalSelector.addGoal(4, new MeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity)
    {
        return !(entity instanceof ServerPlayer player && player.isCreative());
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(ItemTags.CREEPER_IGNITERS)) {
            SoundEvent event = stack.is(Items.FIRE_CHARGE) ? SoundEvents.FIRECHARGE_USE : SoundEvents.FLINTANDSTEEL_USE;

            level().playSound(player, getX(), getY(), getZ(), event, getSoundSource(), 1, random.nextFloat() * 0.4F + 0.8F);

            if (!level().isClientSide) {
                ignite();

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
    public boolean isInvulnerableTo(@NotNull DamageSource source)
    {
        return source == level().damageSources().explosion(source.getEntity(), source.getDirectEntity());
    }

    @Override
    public void tick()
    {
        LivingEntity target = getTarget();

        if (target == null) {
            teleportTime++;

            if (!isIgnited()) {
                if (teleportTime >= random.nextInt(80, 440) && !Entitys.isEntityMovingXZ(this)) {
                    int targetScanRadius = 6;

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
                teleportCreeperToTarget();
            }
        }

        if (isAlive()) {
            oldSwell = swell;

            if (isIgnited()) {
                setSwellDirection(1);
            }

            int swellDir = getSwellDirection();

            if (swellDir > 0 && swell == 0) {
                playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
                gameEvent(GameEvent.PRIME_FUSE);
            }

            swell += swellDir;

            if (swell < 0) {
                swell = 0;
            }

            if (swell >= maxSwell) {
                swell = maxSwell;
                fakeOutExplosion();
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
    public boolean isPowered()
    {
        return true;
    }

    static class CosmicCreeperiteSwellGoal extends Goal
    {
        private final CosmicCreeperite creeperite;
        private LivingEntity target;

        public CosmicCreeperiteSwellGoal(CosmicCreeperite creeperite)
        {
            this.creeperite = creeperite;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            LivingEntity entity = creeperite.getTarget();
            return creeperite.getSwellDirection() > 0 || entity != null && creeperite.distanceToSqr(entity) < 9;
        }

        @Override
        public void start()
        {
            creeperite.getNavigation().stop();
            target = creeperite.getTarget();
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
                creeperite.setSwellDirection(-1);
            } else if (creeperite.distanceToSqr(target) > 49) {
                creeperite.setSwellDirection(-1);
            } else if (!creeperite.getSensing().hasLineOfSight(target)) {
                creeperite.setSwellDirection(-1);
            } else {
                creeperite.setSwellDirection(1);
            }
        }
    }
}
