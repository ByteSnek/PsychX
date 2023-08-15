package xyz.snaker.tq.rego;

import java.util.function.Supplier;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.effect.Syncope;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class Effects
{
    static final DeferredRegister<MobEffect> REGISTRAR = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Tourniqueted.MODID);

    public static final RegistryObject<Syncope> SYNCOPE = register("syncope", Syncope::new);

    static <T extends MobEffect> RegistryObject<T> register(String name, Supplier<T> type)
    {
        return REGISTRAR.register(name, type);
    }
}
