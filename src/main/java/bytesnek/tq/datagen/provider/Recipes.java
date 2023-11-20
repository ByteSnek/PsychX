package bytesnek.tq.datagen.provider;

import java.util.function.Consumer;

import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.RegistryObject;

import org.jetbrains.annotations.NotNull;

import bytesnek.tq.rego.Blocks;
import bytesnek.tq.rego.Items;

import static net.minecraft.world.item.Items.LEATHER;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class Recipes extends RecipeProvider implements IConditionBuilder
{
    public Recipes(PackOutput output)
    {
        super(output);
    }

    @Override
    public void buildRecipes(@NotNull Consumer<FinishedRecipe> writer)
    {
        addPlanksRecipe(writer, Blocks.ILLUSIVE_PLANKS, Blocks.ILLUSIVE_LOG);
        addPlanksRecipe(writer, Blocks.DELUSIVE_PLANKS, Blocks.DELUSIVE_LOG);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.TOURNIQUET_WEBBING.get())
                .pattern("LT")
                .pattern("TL")
                .define('L', LEATHER)
                .define('T', Items.WEATHERED_TWINE.get())
                .unlockedBy("tick", PlayerTrigger.TriggerInstance.tick())
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.TOURNIQUET.get())
                .pattern(" KW")
                .pattern("KWK")
                .pattern("WK ")
                .define('K', Items.FLUTTERFLY_KERATIN.get())
                .define('W', Items.TOURNIQUET_WEBBING.get())
                .unlockedBy("tick", PlayerTrigger.TriggerInstance.tick())
                .save(writer);

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.SATURATED_TWINE.get()), RecipeCategory.MISC, Items.WEATHERED_TWINE.get(), 0.1F, 200)
                .unlockedBy("tick", PlayerTrigger.TriggerInstance.tick())
                .save(writer);
    }

    private <I extends ItemLike> void addPlanksRecipe(Consumer<FinishedRecipe> writer, RegistryObject<I> output, RegistryObject<I> input)
    {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, output.get(), 4)
                .requires(input.get()).group("planks")
                .unlockedBy("asap", PlayerTrigger.TriggerInstance.tick())
                .save(writer);
    }
}
