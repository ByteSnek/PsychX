package xyz.snaker.tq.rego;

import java.util.function.Supplier;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.loot.Add;

import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import com.mojang.serialization.Codec;

/**
 * Created by SnakerBone on 29/08/2023
 **/
public class LootModifiers
{
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> REGISTER = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Tourniqueted.MODID);

    public static final Supplier<Codec<? extends IGlobalLootModifier>> ADD = REGISTER.register("add", Add.CODEC);
}
