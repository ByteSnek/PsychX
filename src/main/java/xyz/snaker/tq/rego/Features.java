package xyz.snaker.tq.rego;

import java.util.function.Supplier;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.world.feature.SwirlRubbleFeature;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 25/08/2023
 **/
public class Features
{
    public static final DeferredRegister<Feature<?>> REGISTRAR = DeferredRegister.create(Registries.FEATURE, Tourniqueted.MODID);

    public static final RegistryObject<SwirlRubbleFeature> SWIRL_RUBBLE = register("swirl_rubble", () -> new SwirlRubbleFeature(BlockStateConfiguration.CODEC));

    static <F extends Feature<?>> RegistryObject<F> register(String name, Supplier<F> feature)
    {
        return REGISTRAR.register(name, feature);
    }
}
