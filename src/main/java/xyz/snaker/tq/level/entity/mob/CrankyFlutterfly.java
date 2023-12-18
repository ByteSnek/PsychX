package xyz.snaker.tq.level.entity.mob;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import xyz.snaker.hiss.sneaky.Sneaky;
import xyz.snaker.tq.level.entity.Comatosian;
import xyz.snaker.tq.level.entity.creature.Flutterfly;
import xyz.snaker.tq.level.entity.utterfly.Utterfly;
import xyz.snaker.tq.rego.Sounds;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.neoforged.neoforge.network.NetworkHooks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by SnakerBone on 23/11/2023
 **/
public class CrankyFlutterfly extends Monster implements Comatosian
{
    public CrankyFlutterfly(EntityType<? extends Monster> type, Level level)
    {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 10, false);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1);
    }

    public static AttributeSupplier attributes()
    {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FLYING_SPEED, 0.25)
                .add(Attributes.ATTACK_KNOCKBACK, 1.25)
                .add(Attributes.ATTACK_SPEED, 1.25)
                .add(Attributes.ATTACK_DAMAGE, 1.5)
                .add(Attributes.ARMOR, RandomSource.create().nextDouble())
                .add(Attributes.ARMOR_TOUGHNESS, RandomSource.create().nextDouble())
                .build();
    }

    @Override
    public void registerGoals()
    {
        goalSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, true));
        goalSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Mob.class, true, this::attackPredicate));
        goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.75, false));
        goalSelector.addGoal(7, randomFlyGoal());
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        goalSelector.addGoal(7, new WaterAvoidingRandomFlyingGoal(this, 1));
        goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8));
    }

    private Goal randomFlyGoal()
    {
        try {
            Class<?> ghastClass = Class.forName("net.minecraft.world.entity.monster.Ghast");
            Ghast ghast = Sneaky.cast(ghastClass.getDeclaredConstructor(EntityType.class, Level.class).newInstance(EntityType.GHAST, level()));

            Class<?> clazz = Class.forName("net.minecraft.world.entity.monster.Ghast$RandomFloatAroundGoal");
            Constructor<?> constructor = clazz.getDeclaredConstructor(Ghast.class);
            constructor.setAccessible(true);

            return (Goal) constructor.newInstance(ghast);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean attackPredicate(LivingEntity entity)
    {
        return !(entity instanceof Utterfly) && !(entity instanceof Flutterfly) && !(entity instanceof CrankyFlutterfly);
    }

    @Override
    public @NotNull PathNavigation createNavigation(@NotNull Level level)
    {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);

        navigation.setCanOpenDoors(true);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(true);

        return navigation;
    }

    @Nullable
    @Override
    public SoundEvent getAmbientSound()
    {
        return Sounds.FLUTTERFLY_AMBIENT.get();
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
    public boolean ignoreExplosion()
    {
        return true;
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source)
    {
        return source == level().damageSources().explosion(source.getEntity(), source.getDirectEntity());
    }

    @Override
    public void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state)
    {

    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, @NotNull DamageSource source)
    {
        return false;
    }

    @Override
    public void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos)
    {
        fallDistance = 0;
    }
}
