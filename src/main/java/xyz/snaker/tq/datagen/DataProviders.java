package xyz.snaker.tq.datagen;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;

import xyz.snaker.snakerlib.level.Icon;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.block.ShaderBlock;
import xyz.snaker.tq.level.item.CosmoSpine;
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
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.ForgeSpawnEggItem;
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
    static class BiomeTags extends TagsProvider<Biome> implements BiomeTagProviderTools<BiomeTags>
    {
        public BiomeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper)
        {
            super(output, Registries.BIOME, provider, Tourniqueted.MODID, existingFileHelper);
        }

        @Override
        public void addTags(HolderLookup.@NotNull Provider provider)
        {
            
        }

        @Override
        public BiomeTags getInstance()
        {
            return this;
        }
    }

    static class BlockTags extends BlockTagsProvider implements BlockTagsProviderTools<BlockTags>
    {
        public static final Predicate<RegistryObject<Block>> BLOCKS_NEED_TOOL = block -> block.get() instanceof ShaderBlock<?> || block.get() == Blocks.COMASTONE.get();

        public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper)
        {
            super(output, provider, Tourniqueted.MODID, helper);
        }

        @Override
        public void addTags(@NotNull HolderLookup.Provider provider)
        {
            mineableWithAxe(List.of(Blocks.GEOMETRIC_LOG.get(), Blocks.GEOMETRIC_PLANKS.get()));
            planks(List.of(Blocks.GEOMETRIC_PLANKS.get()));
            logs(List.of(Blocks.GEOMETRIC_LOG.get()));
            groundRich(List.of(Blocks.COMASTONE.get()));
            for (RegistryObject<Block> block : Blocks.REGISTRAR.getEntries()) {
                if (BLOCKS_NEED_TOOL.test(block)) {
                    toolRequired(ToolTier.STONE, List.of(block.get()));
                    mineableWithPickaxe(List.of(block.get()));
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
        public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, Features::configs)
                .add(Registries.PLACED_FEATURE, Features::placements)
                .add(ForgeRegistries.Keys.BIOME_MODIFIERS, Features::modifiers)
                .add(Registries.BIOME, Biomes::biomes);

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
            for (var obj : Blocks.REGISTRAR.getEntries()) {
                var current = obj.get();
                if (current instanceof ShaderBlock<?>) {
                    shader(current);
                } else if (current instanceof BushBlock) {
                    plant(current);
                } else if (current instanceof RotatedPillarBlock) {
                    log(current);
                } else {
                    cube(current);
                }
            }
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
            for (var obj : Items.REGISTRAR.getEntries()) {
                var current = obj.get();
                if (!(current instanceof BlockItem)) {
                    if (current instanceof CosmoSpine) {
                        cosmoSpine(current);
                    } else if (current instanceof ForgeSpawnEggItem) {
                        spawnEgg(current);
                    } else if (current instanceof Icon) {
                        perspective(current);
                    } else {
                        item(current);
                    }
                }
            }
            for (var obj : Blocks.REGISTRAR.getEntries()) {
                var current = obj.get();
                if (current instanceof BushBlock) {
                    blockCustom(current);
                } else {
                    blockItem(current);
                }
            }
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
            for (RegistryObject<Block> block : Blocks.REGISTRAR.getEntries()) {
                dropSelf(block.get());
            }
        }

        @Override
        public @NotNull Iterable<Block> getKnownBlocks()
        {
            return Blocks.REGISTRAR.getEntries().stream().map(RegistryObject::get)::iterator;
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
            for (var obj : Blocks.REGISTRAR.getEntries()) {
                block(obj.get());
            }
            for (var obj : Items.REGISTRAR.getEntries()) {
                item(obj.get());
            }
            for (var obj : Effects.REGISTRAR.getEntries()) {
                effect(obj.get());
            }
            for (var obj : Entities.REGISTRAR.getEntries()) {
                entity(obj.get());
            }
            for (var obj : Sounds.REGISTRAR.getEntries()) {
                sound(obj.get());
            }
            for (var obj : Tabs.REGISTRAR.getEntries()) {
                tab(obj);
            }

            add("tooltip.tq.possible_issue", "If you're a player and you're seeing this then something with the mod may have gone wrong. Please go report this to github.com/SnakerBone/Tourniqueted/issues");
        }

        @Override
        public Languages getInstance()
        {
            return this;
        }
    }
}
