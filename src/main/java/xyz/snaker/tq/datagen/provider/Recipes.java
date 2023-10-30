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

import static net.minecraft.world.item.Items.LEATHER;

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
        planks(writer, Blocks.ILLUSIVE_PLANKS, Blocks.ILLUSIVE_LOG);
        planks(writer, Blocks.DELUSIVE_PLANKS, Blocks.DELUSIVE_LOG);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.TOURNIQUET_WEBBING.get())
                .pattern("LT")
                .pattern("TL")
                .define('L', LEATHER)
                .define('T', Items.WEATHERED_TWINE.get())
                .unlockedBy("asap", RecipeProviderTools.asap())
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.TOURNIQUET.get())
                .pattern(" KW")
                .pattern("KWK")
                .pattern("WK ")
                .define('K', Items.FLUTTERFLY_KERATIN.get())
                .define('W', Items.TOURNIQUET_WEBBING.get())
                .unlockedBy("asap", RecipeProviderTools.asap())
                .save(writer);

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.SATURATED_TWINE.get()), RecipeCategory.MISC, Items.WEATHERED_TWINE.get(), 0.1F, 200)
                .unlockedBy("asap", RecipeProviderTools.asap())
                .save(writer);
    }
}
