package xyz.snaker.tq.datagen;

import java.util.Collection;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.block.ComatoseNyliumBlock;
import xyz.snaker.tq.level.block.ShaderBlock;
import xyz.snaker.tq.level.world.feature.Features;
import xyz.snaker.tq.rego.*;
import xyz.snaker.tq.utility.TqDataGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 21/03/2023
 **/
public class DataProviders
{
    public static final Collection<RegistryObject<Block>> BLOCK_ENTRIES = Blocks.REGISTRAR.getEntries();
    public static final Predicate<RegistryObject<Block>> BLACKLISTED_BLOCKS = block -> block.get() instanceof FlowerPotBlock;

    static class BiomeTags extends TagsProvider<Biome>
    {
        public BiomeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper)
        {
            super(output, Registries.BIOME, provider, Tourniqueted.MODID, existingFileHelper);
            TqDataGen.BIOME_TAGS_PROVIDER.capture(this);
        }

        @Override
        public void addTags(HolderLookup.@NotNull Provider provider)
        {
            TqDataGen.biomeTags(Keys.COMATOSE_VEGETAL, Keys.DELUSION, Keys.ILLUSIVE, Keys.IMMATERIAL, Keys.SPECTRAL, Keys.SURREAL);
        }
    }

    static class BlockTags extends BlockTagsProvider
    {
        public static final Predicate<RegistryObject<Block>> BLOCKS_NEED_TOOL = block -> block.get() instanceof ShaderBlock<?> || block.get() instanceof ComatoseNyliumBlock;

        public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper)
        {
            super(output, provider, Tourniqueted.MODID, helper);
            TqDataGen.BLOCK_TAGS_PROVIDER.capture(this);
        }

        @Override
        public void addTags(@NotNull HolderLookup.Provider provider)
        {
            TqDataGen.customBlockTags(Keys.COMATOSE_NYLIUM, Blocks.COMA_STONE, Blocks.DELUSIVE_NYLIUM, Blocks.ILLUSIVE_NYLIUM, Blocks.IMMATERIAL_NYLIUM, Blocks.SPECTRAL_NYLIUM, Blocks.SURREAL_NYLIUM);
            TqDataGen.stoneTags(Blocks.COMA_STONE, Blocks.DELUSIVE_NYLIUM, Blocks.ILLUSIVE_NYLIUM, Blocks.IMMATERIAL_NYLIUM, Blocks.SPECTRAL_NYLIUM, Blocks.SURREAL_NYLIUM);
            TqDataGen.nyliumTags(Blocks.DELUSIVE_NYLIUM, Blocks.ILLUSIVE_NYLIUM, Blocks.IMMATERIAL_NYLIUM, Blocks.SPECTRAL_NYLIUM, Blocks.SURREAL_NYLIUM);

            TqDataGen.mineableWithAxeTags(Blocks.GEOMETRIC_LOG, Blocks.GEOMETRIC_PLANKS, Blocks.GEOMETRIC_STAIRS, Blocks.GEOMETRIC_SLAB);
            TqDataGen.plankTags(Blocks.GEOMETRIC_PLANKS);
            TqDataGen.logTags(Blocks.GEOMETRIC_LOG);

            for (RegistryObject<Block> block : BLOCK_ENTRIES) {
                if (!BLACKLISTED_BLOCKS.test(block) && BLOCKS_NEED_TOOL.test(block)) {
                    TqDataGen.addBlocksNeedingTool(TqDataGen.Tier.STONE, block);
                    TqDataGen.mineableWithPickaxeTags(block);
                }
            }
        }
    }

    static class DatapackEntries extends DatapackBuiltinEntriesProvider
    {
        public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder().add(Registries.CONFIGURED_FEATURE, Features::configs).add(Registries.PLACED_FEATURE, Features::placements).add(ForgeRegistries.Keys.BIOME_MODIFIERS, Features::modifiers);

        public DatapackEntries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
        {
            super(output, registries, BUILDER, Set.of(Tourniqueted.MODID));
        }
    }

    static class BlockStates extends BlockStateProvider
    {
        public BlockStates(PackOutput output, ExistingFileHelper helper)
        {
            super(output, Tourniqueted.MODID, helper);
            TqDataGen.BLOCK_STATE_PROVIDER.capture(this);
        }

        @Override
        public void registerStatesAndModels()
        {
            TqDataGen.shaderBlockState(Blocks.SWIRL);
            TqDataGen.shaderBlockState(Blocks.SNOWFLAKE);
            TqDataGen.shaderBlockState(Blocks.WATERCOLOUR);
            TqDataGen.shaderBlockState(Blocks.MULTICOLOUR);
            TqDataGen.shaderBlockState(Blocks.FLARE);
            TqDataGen.shaderBlockState(Blocks.STARRY);
            TqDataGen.shaderBlockState(Blocks.GEOMETRIC);

            TqDataGen.nyliumBlockState(Blocks.ILLUSIVE_NYLIUM);
            TqDataGen.nyliumBlockState(Blocks.DELUSIVE_NYLIUM);
            TqDataGen.nyliumBlockState(Blocks.IMMATERIAL_NYLIUM);
            TqDataGen.nyliumBlockState(Blocks.SPECTRAL_NYLIUM);
            TqDataGen.nyliumBlockState(Blocks.SURREAL_NYLIUM);

            TqDataGen.cubeBlockState(Blocks.COMA_STONE);

            TqDataGen.cubeBlockState(Blocks.GEOMETRIC_PLANKS);
            TqDataGen.logBlockState(Blocks.GEOMETRIC_LOG);
            TqDataGen.stairsBlockState(Blocks.GEOMETRIC_STAIRS, Blocks.GEOMETRIC_PLANKS);
            TqDataGen.slabBlockState(Blocks.GEOMETRIC_SLAB, Blocks.GEOMETRIC_PLANKS);

            TqDataGen.plantBlockState(Blocks.CATNIP, Blocks.POTTED_CATNIP);
            TqDataGen.plantBlockState(Blocks.SPLITLEAF, Blocks.POTTED_SPLITLEAF);
            TqDataGen.plantBlockState(Blocks.SNAKEROOT);
            TqDataGen.plantBlockState(Blocks.TALL_SNAKEROOT);
            TqDataGen.plantBlockState(Blocks.GEOMETRIC_SAPLING);
        }
    }

    static class ItemModels extends ItemModelProvider
    {
        public ItemModels(PackOutput output, ExistingFileHelper helper)
        {
            super(output, Tourniqueted.MODID, helper);
            TqDataGen.ITEM_MODEL_PROVIDER.capture(this);
        }

        @Override
        public void registerModels()
        {
            TqDataGen.generatedItemModelForBlock(Blocks.SWIRL);
            TqDataGen.generatedItemModelForBlock(Blocks.SNOWFLAKE);
            TqDataGen.generatedItemModelForBlock(Blocks.WATERCOLOUR);
            TqDataGen.generatedItemModelForBlock(Blocks.MULTICOLOUR);
            TqDataGen.generatedItemModelForBlock(Blocks.FLARE);
            TqDataGen.generatedItemModelForBlock(Blocks.STARRY);
            TqDataGen.generatedItemModelForBlock(Blocks.GEOMETRIC);
            TqDataGen.generatedItemModelForBlock(Blocks.COMA_STONE);
            TqDataGen.generatedItemModelForBlock(Blocks.DELUSIVE_NYLIUM);
            TqDataGen.generatedItemModelForBlock(Blocks.ILLUSIVE_NYLIUM);
            TqDataGen.generatedItemModelForBlock(Blocks.IMMATERIAL_NYLIUM);
            TqDataGen.generatedItemModelForBlock(Blocks.SPECTRAL_NYLIUM);
            TqDataGen.generatedItemModelForBlock(Blocks.SURREAL_NYLIUM);

            TqDataGen.generatedItemModelForBlock(Blocks.GEOMETRIC_SAPLING);
            TqDataGen.generatedItemModelForBlock(Blocks.GEOMETRIC_LOG);
            TqDataGen.generatedItemModelForBlock(Blocks.GEOMETRIC_PLANKS);
            TqDataGen.generatedItemModelForBlock(Blocks.GEOMETRIC_STAIRS);
            TqDataGen.generatedItemModelForBlock(Blocks.GEOMETRIC_SLAB);

            TqDataGen.cosmoSpineModel(Items.RED_COSMO_SPINE);
            TqDataGen.cosmoSpineModel(Items.GREEN_COSMO_SPINE);
            TqDataGen.cosmoSpineModel(Items.BLUE_COSMO_SPINE);
            TqDataGen.cosmoSpineModel(Items.YELLOW_COSMO_SPINE);
            TqDataGen.cosmoSpineModel(Items.PINK_COSMO_SPINE);
            TqDataGen.cosmoSpineModel(Items.PURPLE_COSMO_SPINE);
            TqDataGen.cosmoSpineModel(Items.ALPHA_COSMO_SPINE);
            TqDataGen.cosmoSpineModel(Items.ANTI_COSMO_SPINE);

            TqDataGen.eggItemModel(Items.COSMO_SPAWN_EGG);
            TqDataGen.eggItemModel(Items.FLARE_SPAWN_EGG);
            TqDataGen.eggItemModel(Items.FLUTTERFLY_SPAWN_EGG);
            TqDataGen.eggItemModel(Items.FROLICKER_SPAWN_EGG);
            TqDataGen.eggItemModel(Items.COSMIC_CREEPER_SPAWN_EGG);
            TqDataGen.eggItemModel(Items.UTTERFLY_SPAWN_EGG);
            TqDataGen.eggItemModel(Items.ANTI_COSMO_SPAWN_EGG);
            TqDataGen.eggItemModel(Items.SNIPE_SPAWN_EGG);
            TqDataGen.eggItemModel(Items.EERIE_CRETIN_SPAWN_EGG);
            TqDataGen.eggItemModel(Items.LEET_SPAWN_EGG);
            TqDataGen.eggItemModel(Items.TEST_SPAWN_EGG);

            TqDataGen.perspectiveItemModel(Items.MOB_TAB_ICON);
            TqDataGen.perspectiveItemModel(Items.ITEM_TAB_ICON);
            TqDataGen.perspectiveItemModel(Items.BLOCK_TAB_ICON);

            TqDataGen.basicItemModel(Items.TOURNIQUET);

            TqDataGen.customItemModelForBlock(Blocks.CATNIP);
            TqDataGen.customItemModelForBlock(Blocks.SPLITLEAF);
            TqDataGen.customItemModelForBlock(Blocks.SNAKEROOT);
            TqDataGen.customItemModelForBlock(Blocks.TALL_SNAKEROOT);
            TqDataGen.customItemModelForBlock(Blocks.GEOMETRIC_SAPLING);
        }
    }

    static class BlockLootTables extends BlockLootSubProvider
    {
        public BlockLootTables()
        {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        public void generate()
        {
            for (RegistryObject<Block> block : BLOCK_ENTRIES) {
                if (!BLACKLISTED_BLOCKS.test(block)) {
                    dropSelf(block.get());
                }
            }
        }

        @Override
        public @NotNull Iterable<Block> getKnownBlocks()
        {
            return BLOCK_ENTRIES.stream().map(RegistryObject::get)::iterator;
        }
    }

    static class Recipes extends RecipeProvider implements IConditionBuilder
    {
        public Recipes(PackOutput output)
        {
            super(output);
        }

        @Override
        public void buildRecipes(@NotNull Consumer<FinishedRecipe> writer)
        {
            TqDataGen.planksRecipe(writer, Blocks.GEOMETRIC_PLANKS, Blocks.GEOMETRIC_LOG);
            TqDataGen.stairsRecipe(writer, Blocks.GEOMETRIC_STAIRS, Blocks.GEOMETRIC_PLANKS);
            TqDataGen.slabRecipe(writer, Blocks.GEOMETRIC_SLAB, Blocks.GEOMETRIC_PLANKS);
        }
    }

    static class Languages extends LanguageProvider
    {
        public Languages(PackOutput output)
        {
            super(output, Tourniqueted.MODID, Locale.US.toString().toLowerCase());
            TqDataGen.LANGUAGE_PROVIDER.capture(this);
        }

        @Override
        public void addTranslations()
        {
            TqDataGen.tabTranslation(Tabs.ITEMS);
            TqDataGen.tabTranslation(Tabs.BLOCKS);
            TqDataGen.tabTranslation(Tabs.MOBS);

            TqDataGen.itemTranslation(Items.COSMO_SPAWN_EGG);
            TqDataGen.itemTranslation(Items.SNIPE_SPAWN_EGG);
            TqDataGen.itemTranslation(Items.FLARE_SPAWN_EGG);
            TqDataGen.itemTranslation(Items.COSMIC_CREEPER_SPAWN_EGG);
            TqDataGen.itemTranslation(Items.FROLICKER_SPAWN_EGG);
            TqDataGen.itemTranslation(Items.FLUTTERFLY_SPAWN_EGG);
            TqDataGen.itemTranslation(Items.UTTERFLY_SPAWN_EGG);
            TqDataGen.itemTranslation(Items.ANTI_COSMO_SPAWN_EGG);
            TqDataGen.itemTranslation(Items.EERIE_CRETIN_SPAWN_EGG);
            TqDataGen.itemTranslation(Items.LEET_SPAWN_EGG);

            TqDataGen.itemTranslation(Items.RED_COSMO_SPINE);
            TqDataGen.itemTranslation(Items.GREEN_COSMO_SPINE);
            TqDataGen.itemTranslation(Items.BLUE_COSMO_SPINE);
            TqDataGen.itemTranslation(Items.YELLOW_COSMO_SPINE);
            TqDataGen.itemTranslation(Items.PINK_COSMO_SPINE);
            TqDataGen.itemTranslation(Items.PURPLE_COSMO_SPINE);
            TqDataGen.itemTranslation(Items.ALPHA_COSMO_SPINE);
            TqDataGen.itemTranslation(Items.ANTI_COSMO_SPINE);
            TqDataGen.itemTranslation(Items.MOB_TAB_ICON);
            TqDataGen.itemTranslation(Items.ITEM_TAB_ICON);
            TqDataGen.itemTranslation(Items.BLOCK_TAB_ICON);
            TqDataGen.itemTranslation(Items.TOURNIQUET);

            TqDataGen.blockTranslation(Blocks.SWIRL);
            TqDataGen.blockTranslation(Blocks.SNOWFLAKE);
            TqDataGen.blockTranslation(Blocks.STARRY);
            TqDataGen.blockTranslation(Blocks.GEOMETRIC);
            TqDataGen.blockTranslation(Blocks.FLARE);
            TqDataGen.blockTranslation(Blocks.MULTICOLOUR);
            TqDataGen.blockTranslation(Blocks.WATERCOLOUR);
            TqDataGen.blockTranslation(Blocks.COMA_STONE);
            TqDataGen.blockTranslation(Blocks.CATNIP);
            TqDataGen.blockTranslation(Blocks.SPLITLEAF);
            TqDataGen.blockTranslation(Blocks.SNAKEROOT);
            TqDataGen.blockTranslation(Blocks.TALL_SNAKEROOT);
            TqDataGen.blockTranslation(Blocks.GEOMETRIC_SAPLING);
            TqDataGen.blockTranslation(Blocks.GEOMETRIC_LOG);
            TqDataGen.blockTranslation(Blocks.GEOMETRIC_PLANKS);
            TqDataGen.blockTranslation(Blocks.GEOMETRIC_STAIRS);
            TqDataGen.blockTranslation(Blocks.GEOMETRIC_SLAB);
            TqDataGen.blockTranslation(Blocks.POTTED_CATNIP);
            TqDataGen.blockTranslation(Blocks.POTTED_SPLITLEAF);
            TqDataGen.blockTranslation(Blocks.ILLUSIVE_NYLIUM);
            TqDataGen.blockTranslation(Blocks.DELUSIVE_NYLIUM);
            TqDataGen.blockTranslation(Blocks.IMMATERIAL_NYLIUM);
            TqDataGen.blockTranslation(Blocks.SPECTRAL_NYLIUM);
            TqDataGen.blockTranslation(Blocks.SURREAL_NYLIUM);

            TqDataGen.entityTranslation(Entities.COSMO);
            TqDataGen.entityTranslation(Entities.SNIPE);
            TqDataGen.entityTranslation(Entities.FLARE);
            TqDataGen.entityTranslation(Entities.COSMIC_CREEPER);
            TqDataGen.entityTranslation(Entities.FROLICKER);
            TqDataGen.entityTranslation(Entities.FLUTTERFLY);
            TqDataGen.entityTranslation(Entities.UTTERFLY);
            TqDataGen.entityTranslation(Entities.EXPLOSIVE_HOMMING_ARROW);
            TqDataGen.entityTranslation(Entities.HOMMING_ARROW);
            TqDataGen.entityTranslation(Entities.EERIE_CRETIN);
            TqDataGen.entityTranslation(Entities.LEET);

            TqDataGen.soundTranslation(Sounds.CONFUSE);
            TqDataGen.soundTranslation(Sounds.EARTH);
            TqDataGen.soundTranslation(Sounds.FOG);
            TqDataGen.soundTranslation(Sounds.GAZE);
            TqDataGen.soundTranslation(Sounds.NIGHT);
            TqDataGen.soundTranslation(Sounds.SHOOT);
            TqDataGen.soundTranslation(Sounds.UTTERFLY_AMBIENT);
            TqDataGen.soundTranslation(Sounds.FLUTTERFLY_AMBIENT);
            TqDataGen.soundTranslation(Sounds.SNIPE_AMBIENT);
            TqDataGen.soundTranslation(Sounds.SNIPE_HURT);
            TqDataGen.soundTranslation(Sounds.COSMO_HURT);
            TqDataGen.soundTranslation(Sounds.ENTITY_DEATH);
            TqDataGen.soundTranslation(Sounds.TING);
            TqDataGen.soundTranslation(Sounds.PEW);
            TqDataGen.soundTranslation(Sounds.FIELD);
            TqDataGen.soundTranslation(Sounds.HIT);
            TqDataGen.soundTranslation(Sounds.LASER);
            TqDataGen.soundTranslation(Sounds.PULSE);

            TqDataGen.effectTranslation(Effects.SYNCOPE);
        }
    }
}
