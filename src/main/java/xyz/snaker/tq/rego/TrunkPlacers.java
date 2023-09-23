package xyz.snaker.tq.rego;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.world.tree.IllusiveTrunkPlacer;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 25/08/2023
 **/
public class TrunkPlacers
{
    public static final DeferredRegister<TrunkPlacerType<?>> REGISTRAR = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, Tourniqueted.MODID);

    public static final RegistryObject<TrunkPlacerType<IllusiveTrunkPlacer>> ILLUSIVE = REGISTRAR.register("illusive", () -> new TrunkPlacerType<>(IllusiveTrunkPlacer.CODEC));
}
