package xyz.snaker.tq.rego;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.fluid.type.ComasoteFluidType;

import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 24/09/2023
 **/
public class FluidTypes
{
    public static final DeferredRegister<FluidType> REGISTRAR = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Tourniqueted.MODID);

    public static final RegistryObject<FluidType> COMASOTE = REGISTRAR.register("comasote", () -> new ComasoteFluidType(FluidType.Properties.create()
            .descriptionId("block." + Tourniqueted.MODID + ".comasote")
            .fallDistanceModifier(0)
            .canExtinguish(false)
            .supportsBoating(true)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
            .canHydrate(false)
    ));
}
