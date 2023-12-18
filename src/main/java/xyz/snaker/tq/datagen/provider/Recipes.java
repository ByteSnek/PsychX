package xyz.snaker.tq.datagen.provider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import xyz.snaker.tq.rego.Items;

import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import org.jetbrains.annotations.NotNull;

import xyz.snaker.tq.rego.Blocks;

import static net.minecraft.world.item.Items.LEATHER;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class Recipes extends RecipeProvider implements IConditionBuilder
{
    public Recipes(PackOutput output, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(output, provider);
    }

    @Override
    public void buildRecipes(@NotNull RecipeOutput output)
    {
        addPlanksRecipe(output, Blocks.ILLUSIVE_PLANKS, Blocks.ILLUSIVE_LOG);
        addPlanksRecipe(output, Blocks.DELUSIVE_PLANKS, Blocks.DELUSIVE_LOG);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.TOURNIQUET_WEBBING.get())
                .pattern("LT")
                .pattern("TL")
                .define('L', LEATHER)
                .define('T', Items.WEATHERED_TWINE.get())
                .unlockedBy("tick", PlayerTrigger.TriggerInstance.tick())
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.TOURNIQUET.get())
                .pattern(" KW")
                .pattern("KWK")
                .pattern("WK ")
                .define('K', Items.FLUTTERFLY_KERATIN.get())
                .define('W', Items.TOURNIQUET_WEBBING.get())
                .unlockedBy("tick", PlayerTrigger.TriggerInstance.tick())
                .save(output);

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.SATURATED_TWINE.get()), RecipeCategory.MISC, Items.WEATHERED_TWINE.get(), 0.1F, 200)
                .unlockedBy("tick", PlayerTrigger.TriggerInstance.tick())
                .save(output);
    }

    private <I extends ItemLike> void addPlanksRecipe(RecipeOutput output, Supplier<I> o, Supplier<I> i)
    {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, o.get(), 4)
                .requires(i.get()).group("planks")
                .unlockedBy("asap", PlayerTrigger.TriggerInstance.tick())
                .save(output);
    }
}
