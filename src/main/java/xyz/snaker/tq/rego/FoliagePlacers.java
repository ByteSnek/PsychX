package xyz.snaker.tq.rego;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.world.tree.IllusiveFoliagePlacer;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 25/08/2023
 **/
public class FoliagePlacers
{
    public static final DeferredRegister<FoliagePlacerType<?>> REGISTRAR = DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, Tourniqueted.MODID);

    public static final RegistryObject<FoliagePlacerType<IllusiveFoliagePlacer>> ILLUSIVE = REGISTRAR.register("illusive", () -> new FoliagePlacerType<>(IllusiveFoliagePlacer.CODEC));
}
