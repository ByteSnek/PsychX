package bytesnek.tq.rego;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import bytesnek.snakerlib.resources.ResourceReference;
import bytesnek.tq.Tourniqueted;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class Sounds
{
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Tourniqueted.MODID);

    public static final RegistryObject<SoundEvent> SNIPE_SHOOT = register("snipe_shoot");
    public static final RegistryObject<SoundEvent> COSMO_HURT = register("cosmo_hurt");
    public static final RegistryObject<SoundEvent> UTTERFLY_SHOOT = register("utterfly_shoot");
    public static final RegistryObject<SoundEvent> UTTERFLY_AMBIENT = register("utterfly_ambient");
    public static final RegistryObject<SoundEvent> FLUTTERFLY_AMBIENT = register("flutterfly_ambient");
    public static final RegistryObject<SoundEvent> FLARE_FLASHBANG = register("flare_flashbang");
    public static final RegistryObject<SoundEvent> SNIPE_HURT = register("snipe_hurt");
    public static final RegistryObject<SoundEvent> SNIPE_AMBIENT = register("snipe_ambient");
    public static final RegistryObject<SoundEvent> DEATH = register("death");
    public static final RegistryObject<SoundEvent> SFX = register("sfx");
    public static final RegistryObject<SoundEvent> COMASOTE = register("comasote");
    public static final RegistryObject<SoundEvent> COMA_CRYSTAL_SHOOT = register("coma_crystal_shoot");
    public static final RegistryObject<SoundEvent> COMA_CRYSTAL_SHIELD = register("coma_crystal_shield");
    public static final RegistryObject<SoundEvent> COMA_CRYSTAL_HIT = register("coma_crystal_hit");
    public static final RegistryObject<SoundEvent> COMA_CRYSTAL_DEPLETE = register("coma_crystal_deplete");
    public static final RegistryObject<SoundEvent> COMA_CRYSTAL_BEAM = register("coma_crystal_beam");

    public static final RegistryObject<SoundEvent> AMBIENT0 = register("ambient0");
    public static final RegistryObject<SoundEvent> AMBIENT1 = register("ambient1");
    public static final RegistryObject<SoundEvent> AMBIENT2 = register("ambient2");
    public static final RegistryObject<SoundEvent> AMBIENT3 = register("ambient3");

    static RegistryObject<SoundEvent> register(String name)
    {
        return REGISTER.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceReference(name)));
    }
}
