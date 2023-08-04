package snaker.snakerlib.utility;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snaker.snakerlib.math.Maths;

/**
 * Created by SnakerBone on 20/02/2023
 **/
public class MiscStuff
{
    public static <T extends Block> FlowerPotBlock addFlowerPotPlant(RegistryObject<T> normal, RegistryObject<T> potted)
    {
        FlowerPotBlock block = SketchyStuff.tryCast(Blocks.FLOWER_POT);
        block.addPlant(normal.getId(), potted);
        return block;
    }

    public static <T extends LivingEntity, E extends MobEffect> boolean addEffectDirect(T entity, E effect, @Nullable Integer duration, @Nullable Integer mul, boolean isAmbient, boolean showBubble, boolean showIcon)
    {
        return entity.addEffect(new MobEffectInstance(effect, duration == null ? Maths.secondsToTicks(10) : duration, mul == null ? 0 : mul, isAmbient, showBubble, showIcon));
    }

    public static <T extends LivingEntity, E extends MobEffect> boolean addEffectDirect(T entity, E effect, @Nullable Integer duration, @Nullable Integer mul)
    {
        return addEffectDirect(entity, effect, duration, mul, false, false, false);
    }

    public static <T extends LivingEntity, E extends MobEffect> boolean addEffectDirect(T entity, E effect)
    {
        return addEffectDirect(entity, effect, null, null);
    }

    public static <T extends LivingEntity> boolean isEntityRotating(@NotNull T entity)
    {
        return entity.getYRot() != entity.yRotO || entity.yBodyRot != entity.yBodyRotO || entity.yHeadRot != entity.yHeadRotO;
    }

    public static <T extends LivingEntity> boolean isEntityYRotating(@NotNull T entity)
    {
        return entity.getYRot() != entity.yRotO;
    }

    public static <T extends LivingEntity> boolean isEntityYBodyRotating(@NotNull T entity)
    {
        return entity.yBodyRot != entity.yBodyRotO;
    }

    public static <T extends LivingEntity> boolean isEntityYHeadRotating(@NotNull T entity)
    {
        return entity.yHeadRot != entity.yHeadRotO;
    }

    public static <T extends Entity> boolean isEntityMoving(@NotNull T entity)
    {
        return entity.getX() != entity.xo || entity.getY() != entity.yo || entity.getZ() != entity.zo;
    }

    public static <T extends Entity> boolean isEntityMovingX(@NotNull T entity)
    {
        return entity.getX() != entity.xo;
    }

    public static <T extends Entity> boolean isEntityMovingY(@NotNull T entity)
    {
        return entity.getY() != entity.yo;
    }

    public static <T extends Entity> boolean isEntityMovingZ(@NotNull T entity)
    {
        return entity.getZ() != entity.zo;
    }

    public static <T extends Entity> boolean isEntityMovingXZ(@NotNull T entity)
    {
        return entity.getX() != entity.xo || entity.getZ() != entity.zo;
    }

    public static <T extends Entity> boolean isEntityMovingXY(@NotNull T entity)
    {
        return entity.getX() != entity.xo || entity.getY() != entity.yo;
    }

    public static <T extends Entity> boolean isEntityMovingYZ(@NotNull T entity)
    {
        return entity.getY() != entity.yo || entity.getZ() != entity.zo;
    }
}