package snaker.snakerlib.level.entity;

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
import snaker.snakerlib.data.SnakerConstants;

/**
 * Created by SnakerBone on 23/05/2023
 **/
public abstract class SnakerCreature extends Animal
{
    private final SnakerCreature offspring;

    public SnakerCreature(EntityType<? extends Animal> type, Level level, @Nullable SnakerCreature offspring, int xpReward)
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

    public SnakerCreature(EntityType<? extends Animal> type, Level level, @Nullable SnakerCreature offspring)
    {
        this(type, level, offspring, SnakerConstants.EntityAttributes.CREATURE_XP_REWARD);
    }

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
