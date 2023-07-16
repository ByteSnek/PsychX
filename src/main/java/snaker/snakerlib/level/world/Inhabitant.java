package snaker.snakerlib.level.world;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ServerLevelAccessor;
import snaker.snakerlib.internal.BooleanOp;

import java.util.function.Predicate;

/**
 * Created by SnakerBone on 15/07/2023
 **/
public interface Inhabitant<T extends Entity>
{
    Predicate<BooleanOp> extraSpawnConditions(EntityType<T> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random);

    interface Conditions
    {
        Predicate<BooleanOp> NONE = booleanOp -> booleanOp.apply(null, null);
        Predicate<BooleanOp> ALLOW = booleanOp -> booleanOp.apply(true, true);
    }
}
