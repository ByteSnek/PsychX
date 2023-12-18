package xyz.snaker.tq.level.effect;

import java.util.List;
import java.util.function.Consumer;

import xyz.snaker.tq.client.EmptyMobEffectExtensions;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 1/11/2023
 **/
public class FlashBangEffect extends MobEffect
{
    public FlashBangEffect(MobEffectCategory category, int colour)
    {
        super(category, colour);
    }

    @Override
    public List<ItemStack> getCurativeItems()
    {
        return List.of();
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier)
    {
        entity.setDeltaMovement(Vec3.ZERO);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier)
    {
        return true;
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientMobEffectExtensions> consumer)
    {
        consumer.accept(EmptyMobEffectExtensions.EMPTY);
    }
}
