package bytesnek.tq.rego;

import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.mojang.serialization.Codec;

import bytesnek.tq.Tourniqueted;
import bytesnek.tq.level.loot.Add;

/**
 * Created by SnakerBone on 29/08/2023
 **/
public class LootModifiers
{
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> REGISTER = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Tourniqueted.MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD = REGISTER.register("add", Add.CODEC);
}
