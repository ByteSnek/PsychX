package xyz.snaker.tq.rego;

import java.util.function.Supplier;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.world.feature.*;

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

    public static final RegistryObject<SwirlRubbleFeature> SWIRL_RUBBLE = registerRubble("swirl", () -> new SwirlRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<FlareRubbleFeature> FLARE_RUBBLE = registerRubble("flare", () -> new FlareRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<WaterColourRubbleFeature> WATERCOLOUR_RUBBLE = registerRubble("watercolour", () -> new WaterColourRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<BurningRubbleFeature> BURNING_RUBBLE = registerRubble("burning", () -> new BurningRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<GeometricRubbleFeature> GEOMETRIC_RUBBLE = registerRubble("geometric", () -> new GeometricRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<MultiColourRubbleFeature> MULTICOLOUR_RUBBLE = registerRubble("multicolour", () -> new MultiColourRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<SnowflakeRubbleFeature> SNOWFLAKE_RUBBLE = registerRubble("snowflake", () -> new SnowflakeRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<StarryRubbleFeature> STARRY_RUBBLE = registerRubble("starry", () -> new StarryRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<FoggyRubbleFeature> FOGGY_RUBBLE = registerRubble("foggy", () -> new FoggyRubbleFeature(BlockStateConfiguration.CODEC));

    static <F extends Feature<?>> RegistryObject<F> register(String name, Supplier<F> feature)
    {
        return REGISTRAR.register(name, feature);
    }

    static <F extends Feature<?>> RegistryObject<F> registerRubble(String name, Supplier<F> feature)
    {
        return register(name + "_rubble", feature);
    }
}
