package bytesnek.tq.rego;

import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import bytesnek.tq.Tourniqueted;
import bytesnek.tq.level.fluid.ComasoteFluidType;

/**
 * Created by SnakerBone on 24/09/2023
 **/
public class FluidTypes
{
    public static final DeferredRegister<FluidType> REGISTER = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Tourniqueted.MODID);

    public static final RegistryObject<FluidType> COMASOTE = register("comasote", new ComasoteFluidType(FluidType.Properties.create()
            .lightLevel(15)
            .viscosity(5)
            .density(15)
    ));

    static RegistryObject<FluidType> register(String name, FluidType type)
    {
        return REGISTER.register(name + "_fluid", () -> type);
    }
}
