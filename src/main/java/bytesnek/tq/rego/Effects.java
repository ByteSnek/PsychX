package bytesnek.tq.rego;

import java.util.function.Supplier;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import bytesnek.tq.Tourniqueted;
import bytesnek.tq.level.effect.FlashBangEffect;

/**
 * Created by SnakerBone on 1/11/2023
 **/
public class Effects
{
    public static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Tourniqueted.MODID);

    public static final Supplier<FlashBangEffect> FLASHBANG = REGISTER.register("flashbang", () -> new FlashBangEffect(MobEffectCategory.HARMFUL, 0));
}
