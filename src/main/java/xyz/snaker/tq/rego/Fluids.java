package xyz.snaker.tq.rego;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.fluid.ComasoteFluid;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 24/09/2023
 **/
public class Fluids
{
    public static final DeferredRegister<Fluid> REGISTRAR = DeferredRegister.create(ForgeRegistries.FLUIDS, Tourniqueted.MODID);

    public static final RegistryObject<FlowingFluid> COMASOTE = REGISTRAR.register("comasote", ComasoteFluid.Source::new);
    public static final RegistryObject<FlowingFluid> FLOWING_COMASOTE = REGISTRAR.register("flowing_comasote", ComasoteFluid.Flowing::new);
}