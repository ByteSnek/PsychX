package xyz.snaker.tq.datagen.provider;

import java.util.function.Consumer;

import xyz.snaker.tq.rego.Blocks;
import xyz.snaker.tq.rego.Items;
import xyz.snaker.tq.utility.tools.RecipeProviderTools;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class Recipes extends RecipeProvider implements IConditionBuilder, RecipeProviderTools
{
    public Recipes(PackOutput output)
    {
        super(output);
    }

    @Override
    public void buildRecipes(@NotNull Consumer<FinishedRecipe> writer)
    {
        planks(writer, Blocks.GEOMETRIC_PLANKS, Blocks.GEOMETRIC_LOG);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.TOURNIQUET_WEBBING.get())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', Items.WEATHERED_TWINE.get())
                .unlockedBy("asap", RecipeProviderTools.asap())
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.TOURNIQUET.get())
                .pattern(" CW")
                .pattern("CWC")
                .pattern("WC ")
                .define('C', Items.TOURNIQUET_CLASP.get())
                .define('W', Items.TOURNIQUET_WEBBING.get())
                .unlockedBy("asap", RecipeProviderTools.asap())
                .save(writer);

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.SATURATED_TWINE.get()), RecipeCategory.MISC, Items.WEATHERED_TWINE.get(), 0.1F, 200)
                .unlockedBy("asap", RecipeProviderTools.asap())
                .save(writer);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.WEATHERED_TWINE.get()), RecipeCategory.MISC, Items.TOURNIQUET_CLASP.get(), 0.1F, 200)
                .unlockedBy("asap", RecipeProviderTools.asap())
                .save(writer);
    }
}
