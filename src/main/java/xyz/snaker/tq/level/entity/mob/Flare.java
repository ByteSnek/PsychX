package xyz.snaker.tq.level.entity.mob;

import java.util.function.Predicate;

import xyz.snaker.snakerlib.level.entity.Hostile;
import xyz.snaker.snakerlib.level.entity.ai.SwitchGameModeGoal;
import xyz.snaker.snakerlib.utility.tools.WorldStuff;
import xyz.snaker.tq.rego.Keys;
import xyz.snaker.tq.rego.Sounds;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 02/01/2023
 **/
public class Flare extends Hostile
{
    public Flare(EntityType<? extends Flare> type, Level level)
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
        return WorldStuff.isDimension(level, Keys.COMATOSE);
    }

    @Override
    protected void registerGoals()
    {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new SwitchGameModeGoal(this));
        goalSelector.addGoal(6, new RandomStrollGoal(this, 1));
        goalSelector.addGoal(5, new HurtByTargetGoal(this));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.3, false));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity)
    {
        entity.setSecondsOnFire(5);
        return super.doHurtTarget(entity);
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.BLAZE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source)
    {
        return SoundEvents.BLAZE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return Sounds.ENTITY_DEATH.get();
    }

    @Override
    public boolean fireImmune()
    {
        return true;
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
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
