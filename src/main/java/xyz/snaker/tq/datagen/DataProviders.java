package xyz.snaker.tq.datagen;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.block.DelusiveBlock;
import xyz.snaker.tq.level.block.ShaderBlock;
import xyz.snaker.tq.level.world.feature.Features;
import xyz.snaker.tq.rego.*;
import xyz.snaker.tq.utility.tools.*;

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

    static class BiomeTags extends TagsProvider<Biome> implements BiomeTagProviderTools<BiomeTags>
    {
        public BiomeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper)
        {
            super(output, Registries.BIOME, provider, Tourniqueted.MODID, existingFileHelper);
        }

        @Override
        public void addTags(HolderLookup.@NotNull Provider provider)
        {
            add(Keys.COMATOSE_VEGETAL, List.of(Keys.DELUSION, Keys.ILLUSIVE, Keys.IMMATERIAL, Keys.SPECTRAL, Keys.SURREAL));
        }

        @Override
        public BiomeTags getInstance()
        {
            return this;
        }
    }

    static class BlockTags extends BlockTagsProvider implements BlockTagsProviderTools<BlockTags>
    {
        public static final Predicate<RegistryObject<Block>> BLOCKS_NEED_TOOL = block -> block.get() instanceof ShaderBlock<?> || block.get() instanceof DelusiveBlock;

        public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper)
        {
            super(output, provider, Tourniqueted.MODID, helper);
        }

        @Override
        public void addTags(@NotNull HolderLookup.Provider provider)
        {
            custom(Keys.COMATOSE_NYLIUM, List.of(Blocks.COMA_STONE, Blocks.DELUSIVE_NYLIUM, Blocks.ILLUSIVE_NYLIUM, Blocks.IMMATERIAL_NYLIUM, Blocks.SPECTRAL_NYLIUM, Blocks.SURREAL_NYLIUM));
            stone(List.of(Blocks.COMA_STONE, Blocks.DELUSIVE_NYLIUM, Blocks.ILLUSIVE_NYLIUM, Blocks.IMMATERIAL_NYLIUM, Blocks.SPECTRAL_NYLIUM, Blocks.SURREAL_NYLIUM));
            nylium(List.of(Blocks.DELUSIVE_NYLIUM, Blocks.ILLUSIVE_NYLIUM, Blocks.IMMATERIAL_NYLIUM, Blocks.SPECTRAL_NYLIUM, Blocks.SURREAL_NYLIUM));

            mineableWithAxe(List.of(Blocks.GEOMETRIC_LOG, Blocks.GEOMETRIC_PLANKS));
            planks(List.of(Blocks.GEOMETRIC_PLANKS));
            logs(List.of(Blocks.GEOMETRIC_LOG));

            for (RegistryObject<Block> block : BLOCK_ENTRIES) {
                if (!BLACKLISTED_BLOCKS.test(block) && BLOCKS_NEED_TOOL.test(block)) {
                    toolRequired(ToolTier.STONE, List.of(block));
                    mineableWithPickaxe(List.of(block));
                }
            }
        }

        @Override
        public BlockTags getInstance()
        {
            return this;
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

    static class BlockStates extends BlockStateProvider implements BlockStateProviderTools<BlockStates>
    {
        public BlockStates(PackOutput output, ExistingFileHelper helper)
        {
            super(output, Tourniqueted.MODID, helper);
        }

        @Override
        public void registerStatesAndModels()
        {
            shader(Blocks.SWIRL);
            shader(Blocks.SNOWFLAKE);
            shader(Blocks.WATERCOLOUR);
            shader(Blocks.MULTICOLOUR);
            shaderWithLayer(Blocks.FLARE, Blocks.COMA_STONE);
            shader(Blocks.STARRY);
            shader(Blocks.GEOMETRIC);

            nylium(Blocks.ILLUSIVE_NYLIUM);
            nylium(Blocks.DELUSIVE_NYLIUM);
            nylium(Blocks.IMMATERIAL_NYLIUM);
            nylium(Blocks.SPECTRAL_NYLIUM);
            nylium(Blocks.SURREAL_NYLIUM);

            cube(Blocks.COMA_STONE);
            cube(Blocks.GEOMETRIC_PLANKS);

            log(Blocks.GEOMETRIC_LOG);

            plant(Blocks.PINKTAILS, Blocks.POTTED_CATNIP);
            plant(Blocks.SPLITLEAF, Blocks.POTTED_SPLITLEAF);
            plant(Blocks.SNAKEROOT);
            plant(Blocks.TALL_SNAKEROOT);
            plant(Blocks.GEOMETRIC_SAPLING);
        }

        @Override
        public BlockStates getInstance()
        {
            return this;
        }
    }

    static class ItemModels extends ItemModelProvider implements ItemModelProviderTools<ItemModels>
    {
        public ItemModels(PackOutput output, ExistingFileHelper helper)
        {
            super(output, Tourniqueted.MODID, helper);
        }

        @Override
        public void registerModels()
        {
            blockItem(Blocks.SWIRL);
            blockItem(Blocks.SNOWFLAKE);
            blockItem(Blocks.WATERCOLOUR);
            blockItem(Blocks.MULTICOLOUR);
            blockItem(Blocks.FLARE);
            blockItem(Blocks.STARRY);
            blockItem(Blocks.GEOMETRIC);
            blockItem(Blocks.COMA_STONE);
            blockItem(Blocks.DELUSIVE_NYLIUM);
            blockItem(Blocks.ILLUSIVE_NYLIUM);
            blockItem(Blocks.IMMATERIAL_NYLIUM);
            blockItem(Blocks.SPECTRAL_NYLIUM);
            blockItem(Blocks.SURREAL_NYLIUM);
            blockItem(Blocks.GEOMETRIC_LOG);
            blockItem(Blocks.GEOMETRIC_PLANKS);

            cosmoSpine(Items.RED_COSMO_SPINE);
            cosmoSpine(Items.GREEN_COSMO_SPINE);
            cosmoSpine(Items.BLUE_COSMO_SPINE);
            cosmoSpine(Items.YELLOW_COSMO_SPINE);
            cosmoSpine(Items.PINK_COSMO_SPINE);
            cosmoSpine(Items.PURPLE_COSMO_SPINE);
            cosmoSpine(Items.ALPHA_COSMO_SPINE);
            cosmoSpine(Items.ANTI_COSMO_SPINE);

            egg(Items.COSMO_SPAWN_EGG);
            egg(Items.FLARE_SPAWN_EGG);
            egg(Items.FLUTTERFLY_SPAWN_EGG);
            egg(Items.FROLICKER_SPAWN_EGG);
            egg(Items.COSMIC_CREEPER_SPAWN_EGG);
            egg(Items.UTTERFLY_SPAWN_EGG);
            egg(Items.ANTI_COSMO_SPAWN_EGG);
            egg(Items.SNIPE_SPAWN_EGG);
            egg(Items.EERIE_CRETIN_SPAWN_EGG);
            egg(Items.LEET_SPAWN_EGG);
            egg(Items.TEST_SPAWN_EGG);

            perspective(Items.MOB_TAB_ICON);
            perspective(Items.ITEM_TAB_ICON);
            perspective(Items.BLOCK_TAB_ICON);

            item(Items.TOURNIQUET);

            blockCustom(Blocks.PINKTAILS);
            blockCustom(Blocks.SPLITLEAF);
            blockCustom(Blocks.SNAKEROOT);
            blockCustom(Blocks.TALL_SNAKEROOT);
            blockCustom(Blocks.GEOMETRIC_SAPLING);
        }

        @Override
        public ItemModels getInstance()
        {
            return this;
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

    static class Recipes extends RecipeProvider implements IConditionBuilder, RecipeProviderTools
    {
        public Recipes(PackOutput output)
        {
            super(output);
        }

        @Override
        public void buildRecipes(@NotNull Consumer<FinishedRecipe> writer)
        {
            planks(writer, Blocks.GEOMETRIC_PLANKS, Blocks.GEOMETRIC_LOG);
        }
    }

    static class Languages extends LanguageProvider implements LanguageProviderTools<Languages>
    {
        public Languages(PackOutput output)
        {
            super(output, Tourniqueted.MODID, Locale.US.toString().toLowerCase());
        }

        @Override
        public void addTranslations()
        {
            tab(Tabs.ITEMS);
            tab(Tabs.BLOCKS);
            tab(Tabs.MOBS);

            item(Items.COSMO_SPAWN_EGG);
            item(Items.SNIPE_SPAWN_EGG);
            item(Items.FLARE_SPAWN_EGG);
            item(Items.COSMIC_CREEPER_SPAWN_EGG);
            item(Items.FROLICKER_SPAWN_EGG);
            item(Items.FLUTTERFLY_SPAWN_EGG);
            item(Items.UTTERFLY_SPAWN_EGG);
            item(Items.ANTI_COSMO_SPAWN_EGG);
            item(Items.EERIE_CRETIN_SPAWN_EGG);
            item(Items.LEET_SPAWN_EGG);

            item(Items.RED_COSMO_SPINE);
            item(Items.GREEN_COSMO_SPINE);
            item(Items.BLUE_COSMO_SPINE);
            item(Items.YELLOW_COSMO_SPINE);
            item(Items.PINK_COSMO_SPINE);
            item(Items.PURPLE_COSMO_SPINE);
            item(Items.ALPHA_COSMO_SPINE);
            item(Items.ANTI_COSMO_SPINE);
            item(Items.MOB_TAB_ICON);
            item(Items.ITEM_TAB_ICON);
            item(Items.BLOCK_TAB_ICON);
            item(Items.TOURNIQUET);

            block(Blocks.SWIRL);
            block(Blocks.SNOWFLAKE);
            block(Blocks.STARRY);
            block(Blocks.GEOMETRIC);
            block(Blocks.FLARE);
            block(Blocks.MULTICOLOUR);
            block(Blocks.WATERCOLOUR);
            block(Blocks.COMA_STONE);
            block(Blocks.PINKTAILS);
            block(Blocks.SPLITLEAF);
            block(Blocks.SNAKEROOT);
            block(Blocks.TALL_SNAKEROOT);
            block(Blocks.POTTED_CATNIP);
            block(Blocks.POTTED_SPLITLEAF);
            block(Blocks.ILLUSIVE_NYLIUM);
            block(Blocks.DELUSIVE_NYLIUM);
            block(Blocks.IMMATERIAL_NYLIUM);
            block(Blocks.SPECTRAL_NYLIUM);
            block(Blocks.SURREAL_NYLIUM);
            block(Blocks.GEOMETRIC_SAPLING);
            block(Blocks.GEOMETRIC_LOG);
            block(Blocks.GEOMETRIC_PLANKS);

            entity(Entities.COSMO);
            entity(Entities.SNIPE);
            entity(Entities.FLARE);
            entity(Entities.COSMIC_CREEPER);
            entity(Entities.FROLICKER);
            entity(Entities.FLUTTERFLY);
            entity(Entities.UTTERFLY);
            entity(Entities.EXPLOSIVE_HOMMING_ARROW);
            entity(Entities.HOMMING_ARROW);
            entity(Entities.EERIE_CRETIN);
            entity(Entities.LEET);

            sound(Sounds.CONFUSE);
            sound(Sounds.EARTH);
            sound(Sounds.FOG);
            sound(Sounds.GAZE);
            sound(Sounds.NIGHT);
            sound(Sounds.SHOOT);
            sound(Sounds.UTTERFLY_AMBIENT);
            sound(Sounds.FLUTTERFLY_AMBIENT);
            sound(Sounds.SNIPE_AMBIENT);
            sound(Sounds.SNIPE_HURT);
            sound(Sounds.COSMO_HURT);
            sound(Sounds.ENTITY_DEATH);
            sound(Sounds.TING);
            sound(Sounds.PEW);
            sound(Sounds.FIELD);
            sound(Sounds.HIT);
            sound(Sounds.LASER);
            sound(Sounds.PULSE);

            effect(Effects.SYNCOPE);
        }

        @Override
        public Languages getInstance()
        {
            return this;
        }
    }
}
