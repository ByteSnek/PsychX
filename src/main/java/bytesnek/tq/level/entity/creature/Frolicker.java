package bytesnek.tq.level.entity.creature;

import java.util.function.Predicate;

import xyz.snaker.snakerlib.level.entity.FlyingPassive;
import xyz.snaker.snakerlib.utility.Worlds;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import bytesnek.tq.level.entity.Comatosian;
import bytesnek.tq.level.world.EntitySpawner;
import bytesnek.tq.rego.Entities;
import bytesnek.tq.rego.Levels;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class Frolicker extends Butterfly implements Comatosian
{
    private final Predicate<BlockState> blocksToIgnore = state -> state.is(Blocks.WATER) || state.is(Blocks.LAVA) || state.is(Blocks.AIR) || state.is(BlockTags.LEAVES) || state.is(BlockTags.BEE_GROWABLES) || state.is(BlockTags.FLOWERS);
    private int ticksOnGround;

    public Frolicker(EntityType<? extends FlyingPassive> type, Level level)
    {
        super(type, level);
    }

    public static boolean spawnRules(EntityType<Frolicker> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random)
    {
        return EntitySpawner.OVERWORLD.check(level, pos, random, 0.75) ^ EntitySpawner.COMATOSE.check(level, pos, random, 75);
    }

    public static AttributeSupplier attributes()
    {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FLYING_SPEED, 0.25).build();
    }

    public boolean canDoFunny()
    {
        return Worlds.isDimension(level(), Levels.COMATOSE) && !isActuallyOnGround();
    }

    public boolean isActuallyOnGround()
    {
        return isOnGround() && ticksOnGround >= 10;
    }

    public boolean isOnGround()
    {
        return !blocksToIgnore.test(getBlockStateOn());
    }

    @Override
    public void registerGoals()
    {
        super.registerGoals();
        goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(ItemTags.FLOWERS), false));
        goalSelector.addGoal(2, new BreedGoal(this, 1));
    }

    @Override
    public void tick()
    {
        if (isOnGround()) {
            ticksOnGround++;
        } else {
            ticksOnGround = 0;
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
    public boolean isFood(@NotNull ItemStack stack)
    {
        return stack.is(ItemTags.FLOWERS);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mate)
    {
        return new Frolicker(Entities.FROLICKER.get(), level);
    }
}
