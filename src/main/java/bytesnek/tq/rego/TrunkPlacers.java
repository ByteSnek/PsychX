package bytesnek.tq.rego;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import bytesnek.tq.Tourniqueted;
import bytesnek.tq.level.world.tree.IllusiveTrunkPlacer;

/**
 * Created by SnakerBone on 25/08/2023
 **/
public class TrunkPlacers
{
    public static final DeferredRegister<TrunkPlacerType<?>> REGISTER = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, Tourniqueted.MODID);

    public static final RegistryObject<TrunkPlacerType<IllusiveTrunkPlacer>> ILLUSIVE = REGISTER.register("illusive", () -> new TrunkPlacerType<>(IllusiveTrunkPlacer.CODEC));
}
