package xyz.snaker.tq.utility.tools;

import java.util.function.Consumer;

import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 21/08/2023
 **/
public interface RecipeProviderTools
{
    default <I extends ItemLike> void planks(Consumer<FinishedRecipe> writer, RegistryObject<I> output, RegistryObject<I> input)
    {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, output.get(), 4)
                .requires(input.get()).group("planks")
                .unlockedBy("asap", asap())
                .save(writer);
    }

    static PlayerTrigger.TriggerInstance asap()
    {
        return PlayerTrigger.TriggerInstance.tick();
    }
}
