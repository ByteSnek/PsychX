package bytesnek.tq.rego;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import bytesnek.tq.Tourniqueted;

/**
 * Created by SnakerBone on 24/09/2023
 **/
public class Fluids
{
    public static final DeferredRegister<Fluid> REGISTER = DeferredRegister.create(ForgeRegistries.FLUIDS, Tourniqueted.MODID);

    public static final RegistryObject<FlowingFluid> SOURCE_COMASOTE = REGISTER.register("comasote", () -> new ForgeFlowingFluid.Source(Fluids.COMASOTE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_COMASOTE = REGISTER.register("flowing_comasote", () -> new ForgeFlowingFluid.Flowing(Fluids.COMASOTE_PROPERTIES));

    public static final ForgeFlowingFluid.Properties COMASOTE_PROPERTIES = new ForgeFlowingFluid.Properties(FluidTypes.COMASOTE, Fluids.SOURCE_COMASOTE, Fluids.FLOWING_COMASOTE)
            .slopeFindDistance(2)
            .levelDecreasePerBlock(1)
            .block(Blocks.COMASOTE)
            .bucket(Items.COMASOTE_BUCKET);
}