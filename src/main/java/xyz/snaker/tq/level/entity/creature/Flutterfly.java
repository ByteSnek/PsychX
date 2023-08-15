package xyz.snaker.tq.level.entity.creature;

import javax.annotation.Nullable;

import xyz.snaker.snakerlib.level.entity.SnakerFlyingCreature;
import xyz.snaker.snakerlib.utility.tools.WorldStuff;
import xyz.snaker.tq.rego.Sounds;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class Flutterfly extends SnakerFlyingCreature
{
    public Flutterfly(EntityType<? extends SnakerFlyingCreature> type, Level level)
    {
        super(type, level);
    }

    public static AttributeSupplier attributes()
    {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FLYING_SPEED, 0.25).build();
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        goalSelector.addGoal(6, new AvoidEntityGoal<>(this, Spider.class, 6f, 1D, 1.2D));
        goalSelector.addGoal(6, new AvoidEntityGoal<>(this, Parrot.class, 6f, 1D, 1.2D));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound()
    {
        return Sounds.FLUTTERFLY_AMBIENT.get();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static <T extends Entity> boolean spawnRules(EntityType<T> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random)
    {
        return WorldStuff.isDimension(level, Level.OVERWORLD);
    }
}
