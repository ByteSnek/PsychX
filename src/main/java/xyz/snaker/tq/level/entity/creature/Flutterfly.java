package xyz.snaker.tq.level.entity.creature;

import xyz.snaker.snakerlib.level.entity.FlyingPassive;
import xyz.snaker.tq.level.entity.ai.FollowMobGoal;
import xyz.snaker.tq.level.world.EntitySpawner;
import xyz.snaker.tq.rego.Entities;
import xyz.snaker.tq.rego.Sounds;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class Flutterfly extends FlyingPassive
{
    public Flutterfly(EntityType<? extends FlyingPassive> type, Level level)
    {
        super(type, level);
    }

    public static boolean spawnRules(EntityType<Flutterfly> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random)
    {
        return EntitySpawner.OVERWORLD.check(level, pos, random, 0.75);
    }

    public static AttributeSupplier attributes()
    {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FLYING_SPEED, 0.25).build();
    }

    @Override
    public void registerGoals()
    {
        super.registerGoals();
        goalSelector.addGoal(6, new AvoidEntityGoal<>(this, Spider.class, 6, 1, 1.2));
        goalSelector.addGoal(4, new FollowMobGoal(this, Parrot.class, 1.25, 10));
        goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(ItemTags.FLOWERS), false));
        goalSelector.addGoal(2, new BreedGoal(this, 1));
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
    public boolean isFood(@NotNull ItemStack stack)
    {
        return stack.is(ItemTags.FLOWERS);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mate)
    {
        return new Flutterfly(Entities.FLUTTERFLY.get(), level);
    }
}
