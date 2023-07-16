package snaker.tq.level.entity.mob;

import net.minecraft.Util;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.Tags;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snaker.snakerlib.internal.BooleanOp;
import snaker.snakerlib.level.entity.SnakerMob;
import snaker.snakerlib.utility.LevelUtil;
import snaker.tq.client.render.entity.CosmoRenderer;
import snaker.tq.level.entity.ComatoseInhabitant;
import snaker.tq.level.entity.EntityVariants;
import snaker.tq.rego.Rego;

import java.util.function.Predicate;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class Cosmo extends SnakerMob implements ComatoseInhabitant<Cosmo>
{
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Cosmo.class, EntityDataSerializers.INT);

    public Cosmo(EntityType<? extends Monster> type, Level level)
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

    public static <T extends Entity> boolean spawnRules(EntityType<T> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random)
    {
        return LevelUtil.isDimension(level, Rego.Keys.COMATOSE);
    }

    @Override
    @SuppressWarnings({"all", "deprecation"})
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData group, @Nullable CompoundTag tag)
    {
        EntityVariants.Cosmo variant = Util.getRandom(EntityVariants.Cosmo.values(), random);
        EntityVariants.Cosmo end = EntityVariants.Cosmo.PURPLE;
        EntityVariants.Cosmo nether = EntityVariants.Cosmo.RED;
        EntityVariants.Cosmo alpha = EntityVariants.Cosmo.ALPHA;
        boolean isNether = accessor.getLevel().dimension() == Level.NETHER;
        boolean isEnd = accessor.getLevel().dimension() == Level.END;
        boolean alphaFlag = variant == alpha && random.nextInt(64) == 0;
        if (isNether) {
            setVariant(nether);
            if (alphaFlag) {
                setVariant(alpha);
            }
        } else if (isEnd) {
            setVariant(end);
            if (alphaFlag) {
                setVariant(alpha);
            }
        } else {
            setVariant(variant);
            if (alphaFlag) {
                setVariant(alpha);
            }
        }
        return super.finalizeSpawn(accessor, difficulty, reason, group, tag);
    }

    @Override
    public void tick()
    {
        Predicate<ServerPlayer> isPlayerCreative = ServerPlayer::isCreative;
        LivingEntity lastHurtByMob = getLastHurtByMob();
        if (lastHurtByMob instanceof ServerPlayer player) {
            if (!isPlayerCreative.test(player)) {
                setTarget(player);
            }
        }
        super.tick();
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount)
    {
        if (!level().isClientSide) {
            Entity entity = source.getEntity();
            if (entity != null) {
                if (entity instanceof Player player) {
                    ItemStack heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);
                    if (player.isCreative()) {
                        return super.hurt(source, amount);
                    }
                    if (!player.isCreative() && heldItem.is(Tags.Items.STONE)) {
                        return super.hurt(source, getMaxHealth() / random.nextInt(4, 8));
                    } else if (!player.isCreative() && !heldItem.is(Tags.Items.STONE)) {
                        return false;
                    }
                } else {
                    return super.hurt(source, amount);
                }
            }
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean fireImmune()
    {
        return getVariant().equals(EntityVariants.Cosmo.RED);
    }

    @Override
    public @NotNull Component getName()
    {
        Component name = Component.translatable("entity.tq.alpha_cosmo");
        return getVariant().equals(EntityVariants.Cosmo.ALPHA) ? name : super.getName();
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity)
    {
        LivingEntity target = getTarget();
        if (target == null) {
            return false;
        }
        if (getVariant().equals(EntityVariants.Cosmo.ALPHA)) {
            target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 25, 1));
            target.teleportTo(target.getRandomX(16), target.getY(), target.getRandomZ(16));
            level().playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.FOX_TELEPORT, SoundSource.BLOCKS, 1, 1);
        }
        return super.doHurtTarget(entity);
    }

    public static RenderType getRenderType(EntityVariants.Cosmo type)
    {
        return CosmoRenderer.TYPE.get(type);
    }

    public EntityVariants.Cosmo getVariant()
    {
        return EntityVariants.Cosmo.byId(getTypeVariant() & 255);
    }

    private int getTypeVariant()
    {
        return entityData.get(VARIANT);
    }

    public void setVariant(EntityVariants.Cosmo variant)
    {
        entityData.set(VARIANT, variant.getId() & 255);
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        entityData.define(VARIANT, 0);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);
        entityData.set(VARIANT, tag.getInt("Variant"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", getTypeVariant());
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.SCULK_CLICKING;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source)
    {
        return Rego.SOUND_COSMO_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return Rego.SOUND_ENTITY_DEATH.get();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public Predicate<BooleanOp> extraSpawnConditions(EntityType<Cosmo> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random)
    {
        return Conditions.NONE;
    }
}
