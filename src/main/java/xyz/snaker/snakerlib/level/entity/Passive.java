package xyz.snaker.snakerlib.level.entity;

import xyz.snaker.snakerlib.data.DefaultEntityAttributes;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by SnakerBone on 23/05/2023
 **/
public abstract class Passive extends Animal
{
    /**
     * This animal's offspring
     **/
    private final Passive offspring;

    public Passive(EntityType<? extends Animal> type, Level level, @Nullable Passive offspring, int xpReward)
    {
        super(type, level);
        this.offspring = offspring;
        this.xpReward = xpReward;
        setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16);
        setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1);
        setPathfindingMalus(BlockPathTypes.COCOA, -1);
        setPathfindingMalus(BlockPathTypes.WATER, -1);
        setPathfindingMalus(BlockPathTypes.FENCE, -1);
    }

    public Passive(EntityType<? extends Animal> type, Level level, @Nullable Passive offspring)
    {
        this(type, level, offspring, DefaultEntityAttributes.CREATURE_XP_REWARD);
    }

    /**
     * Checks if this mob is actually aggressive
     *
     * @return True if this mob is actually aggressive
     **/
    public boolean isCranky()
    {
        return isAlive() && isEffectiveAi() && isAggressive() && getTarget() != null;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob parent)
    {
        return offspring == null ? this : offspring;
    }

    @Override
    protected void registerGoals()
    {
        goalSelector.addGoal(6, new RandomStrollGoal(this, 1));
        goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1));
        goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8));
        goalSelector.addGoal(0, new FloatGoal(this));
    }
}
