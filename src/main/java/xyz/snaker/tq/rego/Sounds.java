package xyz.snaker.tq.rego;

import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.Tourniqueted;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class Sounds
{
    public static final DeferredRegister<SoundEvent> REGISTRAR = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Tourniqueted.MODID);

    public static final RegistryObject<SoundEvent> CONFUSE = register("confuse");
    public static final RegistryObject<SoundEvent> EARTH = register("earth");
    public static final RegistryObject<SoundEvent> FOG = register("fog");
    public static final RegistryObject<SoundEvent> GAZE = register("gaze");
    public static final RegistryObject<SoundEvent> NIGHT = register("night");
    public static final RegistryObject<SoundEvent> TING = register("ting");
    public static final RegistryObject<SoundEvent> PEW = register("pew");
    public static final RegistryObject<SoundEvent> FIELD = register("field");
    public static final RegistryObject<SoundEvent> HIT = register("hit");
    public static final RegistryObject<SoundEvent> LASER = register("laser");
    public static final RegistryObject<SoundEvent> PULSE = register("pulse");
    public static final RegistryObject<SoundEvent> COSMO_HURT = register("cosmo_hurt");
    public static final RegistryObject<SoundEvent> SHOOT = register("utterfly_shoot");
    public static final RegistryObject<SoundEvent> UTTERFLY_AMBIENT = register("utterfly_ambient");
    public static final RegistryObject<SoundEvent> FLUTTERFLY_AMBIENT = register("flutterfly_ambient");
    public static final RegistryObject<SoundEvent> SNIPE_HURT = register("snipe_hurt");
    public static final RegistryObject<SoundEvent> SNIPE_AMBIENT = register("snipe_ambient");
    public static final RegistryObject<SoundEvent> ENTITY_DEATH = register("entity_death");
    public static final RegistryObject<SoundEvent> RANDOM_FX = register("random_fx");

    public static final RegistryObject<SoundEvent> O = register("o");
    public static final RegistryObject<SoundEvent> XXED = register("xxed");
    public static final RegistryObject<SoundEvent> RELATIVE_RESOLUTE = register("relative_resolute");
    public static final RegistryObject<SoundEvent> REGENERATOR = register("regenerator");
    public static final RegistryObject<SoundEvent> LIMBO = register("limbo");
    public static final RegistryObject<SoundEvent> WIRE = register("wire");

    static RegistryObject<SoundEvent> register(String name)
    {
        return REGISTRAR.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourcePath(name)));
    }
}
