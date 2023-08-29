package xyz.snaker.tq.rego;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.loot.Add;

import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.mojang.serialization.Codec;

/**
 * Created by SnakerBone on 29/08/2023
 **/
public class LootModifiers
{
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Tourniqueted.MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD = LOOT_MODIFIERS.register("add", Add.CODEC);
}
