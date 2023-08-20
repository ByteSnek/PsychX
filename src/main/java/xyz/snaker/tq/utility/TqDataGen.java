package xyz.snaker.tq.utility;

import java.util.function.Consumer;

import xyz.snaker.snakerlib.concurrent.InstanceCapture;
import xyz.snaker.snakerlib.utility.tools.StringStuff;
import xyz.snaker.snakerlib.utility.tools.UnsafeStuff;
import xyz.snaker.tq.Tourniqueted;

import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 20/08/2023
 **/
public class TqDataGen
{
    public static final InstanceCapture<BlockTagsProvider> BLOCK_TAGS_PROVIDER = new InstanceCapture<>("BlockTagsProvider");
    public static final InstanceCapture<BlockStateProvider> BLOCK_STATE_PROVIDER = new InstanceCapture<>("BlockStateProvider");
    public static final InstanceCapture<ItemModelProvider> ITEM_MODEL_PROVIDER = new InstanceCapture<>("ItemModelProvider");
    public static final InstanceCapture<LanguageProvider> LANGUAGE_PROVIDER = new InstanceCapture<>("LanguageProvider");
    public static final InstanceCapture<TagsProvider<Biome>> BIOME_TAGS_PROVIDER = new InstanceCapture<>("TagsProvider");

    @SafeVarargs
    public static void biomeTags(TagKey<Biome> key, ResourceKey<Biome>... values)
    {
        BIOME_TAGS_PROVIDER.get().tag(key).add(values);
    }

    @SafeVarargs
    public static void customBlockTags(TagKey<Block> key, RegistryObject<Block>... values)
    {
        for (var value : values) {
            BLOCK_TAGS_PROVIDER.get().tag(key).add(value.get());
        }
    }

    @SafeVarargs
    public static void plankTags(RegistryObject<Block>... blocks)
    {
        customBlockTags(BlockTags.PLANKS, blocks);
    }

    @SafeVarargs
    public static void logTags(RegistryObject<Block>... blocks)
    {
        customBlockTags(BlockTags.LOGS, blocks);
    }

    @SafeVarargs
    public static void stoneTags(RegistryObject<Block>... blocks)
    {
        customBlockTags(Tags.Blocks.STONE, blocks);
    }

    @SafeVarargs
    public static void nyliumTags(RegistryObject<Block>... blocks)
    {
        customBlockTags(BlockTags.NYLIUM, blocks);
    }

    @SafeVarargs
    public static void mineableWithAxeTags(RegistryObject<Block>... blocks)
    {
        customBlockTags(BlockTags.MINEABLE_WITH_AXE, blocks);
    }

    @SafeVarargs
    public static void mineableWithPickaxeTags(RegistryObject<Block>... blocks)
    {
        customBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, blocks);
    }

    @SafeVarargs
    public static void addBlocksNeedingTool(Tier tier, RegistryObject<Block>... blocks)
    {
        TagKey<Block> key;

        switch (tier) {
            case WOOD -> key = Tags.Blocks.NEEDS_WOOD_TOOL;
            case STONE -> key = BlockTags.NEEDS_STONE_TOOL;
            case IRON -> key = BlockTags.NEEDS_IRON_TOOL;
            case GOLD -> key = Tags.Blocks.NEEDS_GOLD_TOOL;
            case DIAMOND -> key = BlockTags.NEEDS_DIAMOND_TOOL;
            case NETHERITE -> key = Tags.Blocks.NEEDS_NETHERITE_TOOL;
            default -> throw new RuntimeException(String.format("'%s' is not a recognized tier", tier));
        }

        customBlockTags(key, blocks);
    }

    public static void shaderBlockState(RegistryObject<Block> block)
    {
        String name = block.getId().getPath();
        ModelFile file = BLOCK_STATE_PROVIDER.get().models().withExistingParent(name, BLOCK_STATE_PROVIDER.get().modLoc("shader"));
        BLOCK_STATE_PROVIDER.get().simpleBlock(block.get(), file);
    }

    public static void nyliumBlockState(RegistryObject<Block> block)
    {
        String name = block.getId().getPath();
        ModelFile file = BLOCK_STATE_PROVIDER.get().models().withExistingParent(name, BLOCK_STATE_PROVIDER.get().mcLoc("cube_bottom_top")).texture("side", "block/" + name + "_side").texture("top", "block/" + name + "_top").texture("bottom", "block/coma_stone");
        BLOCK_STATE_PROVIDER.get().simpleBlock(block.get(), file);
    }

    public static void logBlockState(RegistryObject<Block> block)
    {
        BLOCK_STATE_PROVIDER.get().logBlock(UnsafeStuff.cast(block.get()));
    }

    public static void cubeBlockState(RegistryObject<Block> block)
    {
        BLOCK_STATE_PROVIDER.get().simpleBlock(block.get(), BLOCK_STATE_PROVIDER.get().cubeAll(block.get()));
    }

    public static void plantBlockState(RegistryObject<Block> plant, RegistryObject<Block> potted)
    {
        ResourceLocation plantVariant = plant.getId();
        ResourceLocation pottedVariant = potted.getId();
        String plantName = plantVariant.getPath();
        String pottedName = pottedVariant.getPath();
        BLOCK_STATE_PROVIDER.get().simpleBlock(plant.get(), BLOCK_STATE_PROVIDER.get().models().cross(plantName, BLOCK_STATE_PROVIDER.get().blockTexture(plant.get())).renderType("cutout"));
        BLOCK_STATE_PROVIDER.get().simpleBlock(potted.get(), BLOCK_STATE_PROVIDER.get().models().withExistingParent(pottedName, BLOCK_STATE_PROVIDER.get().mcLoc("flower_pot_cross")).renderType("cutout").texture("plant", "block/" + plantName));
    }

    public static void plantBlockState(RegistryObject<Block> plant)
    {
        ResourceLocation plantVariant = plant.getId();
        String plantName = plantVariant.getPath();
        BLOCK_STATE_PROVIDER.get().simpleBlock(plant.get(), BLOCK_STATE_PROVIDER.get().models().cross(plantName, BLOCK_STATE_PROVIDER.get().blockTexture(plant.get())).renderType("cutout"));
    }

    public static void stairsBlockState(RegistryObject<Block> stairs, RegistryObject<Block> texture)
    {
        BLOCK_STATE_PROVIDER.get().stairsBlock(UnsafeStuff.cast(stairs.get()), BLOCK_STATE_PROVIDER.get().blockTexture(texture.get()));
    }

    public static void slabBlockState(RegistryObject<Block> slab, RegistryObject<Block> texture)
    {
        BLOCK_STATE_PROVIDER.get().slabBlock(UnsafeStuff.cast(slab.get()), BLOCK_STATE_PROVIDER.get().blockTexture(texture.get()), BLOCK_STATE_PROVIDER.get().blockTexture(texture.get()));
    }

    public static void eggItemModel(RegistryObject<Item> item)
    {
        ITEM_MODEL_PROVIDER.get().withExistingParent(item.getId().getPath(), ITEM_MODEL_PROVIDER.get().modLoc("egg"));
    }

    public static void perspectiveItemModel(RegistryObject<Item> item)
    {
        ITEM_MODEL_PROVIDER.get().withExistingParent(item.getId().getPath(), ITEM_MODEL_PROVIDER.get().modLoc("perspective"));
    }

    public static void cosmoSpineModel(RegistryObject<Item> item)
    {
        ITEM_MODEL_PROVIDER.get().withExistingParent(item.getId().getPath(), ITEM_MODEL_PROVIDER.get().modLoc("cosmo_spine"));
    }

    public static void generatedItemModelForBlock(RegistryObject<Block> block)
    {
        ITEM_MODEL_PROVIDER.get().withExistingParent(block.getId().getPath(), ITEM_MODEL_PROVIDER.get().modLoc("block/" + block.getId().getPath()));
    }

    public static void handheld(RegistryObject<Item> item)
    {
        ITEM_MODEL_PROVIDER.get().withExistingParent(item.getId().getPath(), ITEM_MODEL_PROVIDER.get().mcLoc("handheld")).texture("layer0", ITEM_MODEL_PROVIDER.get().modLoc("item/" + item.getId().getPath()));
    }

    public static void palmHeld(RegistryObject<Item> item)
    {
        ITEM_MODEL_PROVIDER.get().withExistingParent(item.getId().getPath(), ITEM_MODEL_PROVIDER.get().modLoc("palm_held")).texture("layer0", ITEM_MODEL_PROVIDER.get().modLoc("item/" + item.getId().getPath()));
    }

    public static <T extends ItemLike> void basicItemModel(RegistryObject<T> item)
    {
        ITEM_MODEL_PROVIDER.get().withExistingParent(item.getId().getPath(), ITEM_MODEL_PROVIDER.get().mcLoc("item/generated")).texture("layer0", ITEM_MODEL_PROVIDER.get().modLoc("item/" + item.getId().getPath()));
    }

    public static <T extends ItemLike> void customItemModelForBlock(RegistryObject<T> block)
    {
        ITEM_MODEL_PROVIDER.get().withExistingParent(block.getId().getPath(), ITEM_MODEL_PROVIDER.get().mcLoc("item/generated")).texture("layer0", ITEM_MODEL_PROVIDER.get().modLoc("block/" + block.getId().getPath()));
    }

    public static <T extends ItemLike> void stairsRecipe(Consumer<FinishedRecipe> writer, RegistryObject<T> output, RegistryObject<T> input)
    {
        stairs(output, input).unlockedBy("asap", asap()).save(writer);
    }

    public static <T extends ItemLike> void slabRecipe(Consumer<FinishedRecipe> writer, RegistryObject<T> output, RegistryObject<T> input)
    {
        slab(output, input).unlockedBy("asap", asap()).save(writer);
    }

    private static <T extends ItemLike> ShapedRecipeBuilder stairs(RegistryObject<T> output, RegistryObject<T> input)
    {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output.get(), 4).define('#', input.get()).pattern("#  ").pattern("## ").pattern("###");
    }

    private static <T extends ItemLike> ShapedRecipeBuilder slab(RegistryObject<T> output, RegistryObject<T> input)
    {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output.get(), 6).define('#', input.get()).pattern("###");
    }

    public static <T extends ItemLike> void planksRecipe(Consumer<FinishedRecipe> writer, RegistryObject<T> output, RegistryObject<T> input)
    {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, output.get(), 4).requires(input.get()).group("planks").unlockedBy("asap", asap()).save(writer);
    }

    public static PlayerTrigger.TriggerInstance asap()
    {
        return PlayerTrigger.TriggerInstance.tick();
    }

    public static void tabTranslation(RegistryObject<CreativeModeTab> tab)
    {
        String name = tab.getId().getPath();
        LANGUAGE_PROVIDER.get().add("itemGroup." + name, StringStuff.i18nt(name));
    }

    public static <T extends EntityType<?>> void entityTranslation(RegistryObject<T> entity)
    {
        String name = entity.getId().getPath();

        if (name.equals("cosmo")) {
            LANGUAGE_PROVIDER.get().add("entity." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
            LANGUAGE_PROVIDER.get().add("entity." + Tourniqueted.MODID + "." + "alpha_" + name, StringStuff.i18nt("alpha_" + name));
            return;
        }

        LANGUAGE_PROVIDER.get().add("entity." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    public static <T extends Item> void itemTranslation(RegistryObject<T> item)
    {
        String name = item.getId().getPath();
        LANGUAGE_PROVIDER.get().add("item." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    public static <T extends Block> void blockTranslation(RegistryObject<T> block)
    {
        String name = block.getId().getPath();
        LANGUAGE_PROVIDER.get().add("block." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    public static void soundTranslation(RegistryObject<SoundEvent> sound)
    {
        String name = sound.getId().getPath();
        LANGUAGE_PROVIDER.get().add("sounds." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    public static <T extends MobEffect> void effectTranslation(RegistryObject<T> effect)
    {
        String name = effect.getId().getPath();
        LANGUAGE_PROVIDER.get().add("effect." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    public enum Tier
    {
        WOOD(),
        STONE(),
        IRON(),
        GOLD(),
        DIAMOND(),
        NETHERITE()
    }
}
