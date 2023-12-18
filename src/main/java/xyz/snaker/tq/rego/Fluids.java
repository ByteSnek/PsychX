package xyz.snaker.tq.rego;

import java.util.function.Supplier;

import xyz.snaker.snakerlib.utility.fluid.FluidProperties;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.fluid.ComasoteFluidType;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;

/**
 * Created by SnakerBone on 24/09/2023
 **/
public class Fluids
{
    public static final DeferredRegister<Fluid> REGISTER = DeferredRegister.create(BuiltInRegistries.FLUID, Tourniqueted.MODID);

    public static final Supplier<FlowingFluid> COMASOTE = REGISTER.register("comasote", () -> new BaseFlowingFluid.Source(Fluids.COMASOTE_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_COMASOTE = REGISTER.register("flowing_comasote", () -> new BaseFlowingFluid.Flowing(Fluids.COMASOTE_PROPERTIES));

    public static final BaseFlowingFluid.Properties COMASOTE_PROPERTIES = FluidProperties.LAVA.apply(ImmutableTriple.of(Types.COMASOTE, COMASOTE, FLOWING_COMASOTE), ImmutablePair.of(Blocks.COMASOTE, Items.COMASOTE_BUCKET));

    public static class Types
    {
        public static final DeferredRegister<FluidType> REGISTER = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, Tourniqueted.MODID);

        public static final Supplier<FluidType> COMASOTE = REGISTER.register("comasote", ComasoteFluidType::new);
    }
}