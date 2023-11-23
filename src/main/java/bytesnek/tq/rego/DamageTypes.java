package bytesnek.tq.rego;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

import bytesnek.snakerlib.resources.ResourceReference;

/**
 * Created by SnakerBone on 22/11/2023
 **/
public class DamageTypes
{
    public static final ResourceKey<DamageType> COSMIC = key("cosmic");

    public static void bootstrap(BootstapContext<DamageType> context)
    {
        context.register(COSMIC, new DamageType("cosmic", DamageScaling.ALWAYS, 1.2F));
    }

    static ResourceKey<DamageType> key(String name)
    {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceReference(name));
    }
}
