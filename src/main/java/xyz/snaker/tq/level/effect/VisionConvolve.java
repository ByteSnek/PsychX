package xyz.snaker.tq.level.effect;

import java.util.function.Consumer;

import xyz.snaker.snakerlib.utility.tools.ColourStuff;
import xyz.snaker.tq.client.fx.EmptyMobEffectExtensions;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 6/07/2023
 **/
public class VisionConvolve extends MobEffect
{
    public VisionConvolve()
    {
        super(MobEffectCategory.NEUTRAL, ColourStuff.randomHex());
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier)
    {

    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier)
    {
        return duration > 0;
    }

    @Override
    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer)
    {
        consumer.accept(EmptyMobEffectExtensions.INSTANCE);
    }
}
