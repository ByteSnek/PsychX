package xyz.snaker.tq.rego;

import java.util.function.Supplier;

import xyz.snaker.snakerlib.resources.ResourceReference;
import xyz.snaker.tq.Tourniqueted;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class Sounds
{
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Tourniqueted.MODID);

    public static final Supplier<SoundEvent> SNIPE_SHOOT = register("snipe_shoot");
    public static final Supplier<SoundEvent> COSMO_HURT = register("cosmo_hurt");
    public static final Supplier<SoundEvent> UTTERFLY_SHOOT = register("utterfly_shoot");
    public static final Supplier<SoundEvent> UTTERFLY_AMBIENT = register("utterfly_ambient");
    public static final Supplier<SoundEvent> FLUTTERFLY_AMBIENT = register("flutterfly_ambient");
    public static final Supplier<SoundEvent> FLARE_FLASHBANG = register("flare_flashbang");
    public static final Supplier<SoundEvent> SNIPE_HURT = register("snipe_hurt");
    public static final Supplier<SoundEvent> SNIPE_AMBIENT = register("snipe_ambient");
    public static final Supplier<SoundEvent> DEATH = register("death");
    public static final Supplier<SoundEvent> SFX = register("sfx");
    public static final Supplier<SoundEvent> COMASOTE = register("comasote");
    public static final Supplier<SoundEvent> COMA_CRYSTAL_SHOOT = register("coma_crystal_shoot");
    public static final Supplier<SoundEvent> COMA_CRYSTAL_SHIELD = register("coma_crystal_shield");
    public static final Supplier<SoundEvent> COMA_CRYSTAL_HIT = register("coma_crystal_hit");
    public static final Supplier<SoundEvent> COMA_CRYSTAL_DEPLETE = register("coma_crystal_deplete");
    public static final Supplier<SoundEvent> COMA_CRYSTAL_BEAM = register("coma_crystal_beam");

    public static final Supplier<SoundEvent> AMBIENT0 = register("ambient0");
    public static final Supplier<SoundEvent> AMBIENT1 = register("ambient1");
    public static final Supplier<SoundEvent> AMBIENT2 = register("ambient2");
    public static final Supplier<SoundEvent> AMBIENT3 = register("ambient3");

    static Supplier<SoundEvent> register(String name)
    {
        return REGISTER.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceReference(name)));
    }
}
