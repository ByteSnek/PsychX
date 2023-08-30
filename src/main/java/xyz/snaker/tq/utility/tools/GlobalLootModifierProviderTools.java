package xyz.snaker.tq.utility.tools;

import xyz.snaker.tq.level.loot.Add;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public interface GlobalLootModifierProviderTools<T extends GlobalLootModifierProvider>
{
    T getInstance();

    default <I extends ItemLike> void addTwineModifier(Block block, RegistryObject<I> drop, double chance)
    {
        getInstance().add(drop.getId().getPath(), twineCheck(block, drop, chance));
    }

    default <I extends ItemLike> Add twineCheck(Block block, RegistryObject<I> drop, double chance)
    {
        return new Add(new LootItemCondition[]
                {
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).build(),
                        LootItemRandomChanceCondition.randomChance((float) chance).build(),
                        WeatherCheck.weather().setRaining(true).build(),
                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.SWORDS)).build()
                },
                drop.get().asItem()
        );
    }
}
