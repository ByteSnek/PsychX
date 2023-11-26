package bytesnek.tq.rego;

import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredRegister;

import bytesnek.tq.Tourniqueted;
import bytesnek.tq.level.world.tree.IllusiveTrunkPlacer;

/**
 * Created by SnakerBone on 25/08/2023
 **/
public class TrunkPlacers
{
    public static final DeferredRegister<TrunkPlacerType<?>> REGISTER = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, Tourniqueted.MODID);

    public static final Supplier<TrunkPlacerType<IllusiveTrunkPlacer>> ILLUSIVE = REGISTER.register("illusive", () -> new TrunkPlacerType<>(IllusiveTrunkPlacer.CODEC));
}
