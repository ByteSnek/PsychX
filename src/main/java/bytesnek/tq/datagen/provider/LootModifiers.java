package bytesnek.tq.datagen.provider;

import java.util.function.Supplier;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

import bytesnek.snakerlib.resources.ResourceLocations;
import bytesnek.tq.Tourniqueted;
import bytesnek.tq.level.loot.Add;
import bytesnek.tq.rego.Items;

import static net.minecraft.world.level.block.Blocks.LARGE_FERN;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class LootModifiers extends GlobalLootModifierProvider
{
    public LootModifiers(PackOutput output)
    {
        super(output, Tourniqueted.MODID);
    }

    @Override
    public void start()
    {
        addTwineModifier(LARGE_FERN, Items.SATURATED_TWINE, 0.1);
    }

    private <T extends ItemLike> void addTwineModifier(Block block, Supplier<T> drop, double chance)
    {
        add(ResourceLocations.BLOCK.getResourceLocation(block).getPath(), twineCheck(block, drop, chance));
    }

    private <T extends ItemLike> Add twineCheck(Block block, Supplier<T> drop, double chance)
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
