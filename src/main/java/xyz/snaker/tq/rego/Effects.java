package xyz.snaker.tq.rego;

import java.util.function.Supplier;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.effect.FlashBangEffect;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Created by SnakerBone on 1/11/2023
 **/
public class Effects
{
    public static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Tourniqueted.MODID);

    public static final Supplier<FlashBangEffect> FLASHBANG = REGISTER.register("flashbang", () -> new FlashBangEffect(MobEffectCategory.HARMFUL, 0));
}
