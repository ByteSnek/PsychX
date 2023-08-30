package xyz.snaker.tq.level.entity.mob;

import xyz.snaker.snakerlib.level.entity.Hostile;
import xyz.snaker.snakerlib.utility.tools.WorldStuff;
import xyz.snaker.tq.rego.Keys;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

/**
 * Created by SnakerBone on 15/07/2023
 **/
public class EerieCretin extends Hostile
{
    public EerieCretin(EntityType<? extends Monster> type, Level level)
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
        return WorldStuff.isDimension(level, Keys.COMATOSE) && WorldStuff.random(random, 75);
    }
}
