package bytesnek.tq.rego;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import bytesnek.snakerlib.utility.fluid.FluidProperties;
import bytesnek.tq.Tourniqueted;
import bytesnek.tq.level.fluid.ComasoteFluidType;

/**
 * Created by SnakerBone on 24/09/2023
 **/
public class Fluids
{
    public static final DeferredRegister<Fluid> REGISTER = DeferredRegister.create(ForgeRegistries.FLUIDS, Tourniqueted.MODID);

    public static final RegistryObject<FlowingFluid> COMASOTE = REGISTER.register("comasote", () -> new ForgeFlowingFluid.Source(Fluids.COMASOTE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_COMASOTE = REGISTER.register("flowing_comasote", () -> new ForgeFlowingFluid.Flowing(Fluids.COMASOTE_PROPERTIES));

    public static final ForgeFlowingFluid.Properties COMASOTE_PROPERTIES = FluidProperties.LAVA.apply(ImmutableTriple.of(Types.COMASOTE, COMASOTE, FLOWING_COMASOTE), ImmutablePair.of(Blocks.COMASOTE, Items.COMASOTE_BUCKET));

    public static class Types
    {
        public static final DeferredRegister<FluidType> REGISTER = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Tourniqueted.MODID);

        public static final RegistryObject<FluidType> COMASOTE = REGISTER.register("comasote", ComasoteFluidType::new);
    }
}