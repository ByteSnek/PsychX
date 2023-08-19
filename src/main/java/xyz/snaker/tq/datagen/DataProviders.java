package xyz.snaker.tq.datagen;

import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import xyz.snaker.snakerlib.utility.tools.StringStuff;
import xyz.snaker.snakerlib.utility.tools.UnsafeStuff;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.world.feature.Features;
import xyz.snaker.tq.rego.*;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by SnakerBone on 21/03/2023
 **/
public class DataProviders
{
    public static class BiomeTags extends TagsProvider<Biome>
    {
        public BiomeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper)
        {
            super(output, Registries.BIOME, provider, Tourniqueted.MODID, existingFileHelper);
        }

        @Override
        public void addTags(HolderLookup.@NotNull Provider provider)
        {
            add(Keys.COMATOSE_VEGETAL, Keys.DELUSION, Keys.ILLUSIVE, Keys.IMMATERIAL, Keys.SPECTRAL, Keys.SURREAL);
        }

        @SafeVarargs
        private void add(TagKey<Biome> key, ResourceKey<Biome>... biomes)
        {
            tag(key).add(biomes);
        }
    }

    public static class BlockTags extends BlockTagsProvider
    {
        public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper)
        {
            super(output, provider, Tourniqueted.MODID, helper);
        }

        @Override
        public void addTags(@NotNull HolderLookup.Provider provider)
        {
            add(Keys.COMATOSE_NYLIUM, Blocks.COMA_STONE, Blocks.DELUSIVE_NYLIUM, Blocks.ILLUSIVE_NYLIUM, Blocks.IMMATERIAL_NYLIUM, Blocks.SPECTRAL_NYLIUM, Blocks.SURREAL_NYLIUM);
        }

        @SafeVarargs
        private void add(TagKey<Block> key, RegistryObject<Block>... blocks)
        {
            for (RegistryObject<Block> block : blocks) {
                tag(key).add(block.get());
            }
        }
    }

    public static class DatapackEntries extends DatapackBuiltinEntriesProvider
    {
        public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, Features::configs)
                .add(Registries.PLACED_FEATURE, Features::placements)
                .add(ForgeRegistries.Keys.BIOME_MODIFIERS, Features::modifiers);

        public DatapackEntries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
        {
            super(output, registries, BUILDER, Set.of(Tourniqueted.MODID));
        }
    }

    public static class BlockStates extends BlockStateProvider
    {
        public BlockStates(PackOutput output, ExistingFileHelper helper)
        {
            super(output, Tourniqueted.MODID, helper);
        }

        private void shader(RegistryObject<Block> block)
        {
            String name = block.getId().getPath();
            ModelFile file = models().withExistingParent(name, modLoc("shader"));
            simpleBlock(block.get(), file);
        }

        private void nylium(RegistryObject<Block> block)
        {
            String name = block.getId().getPath();
            ModelFile file = models().withExistingParent(name, mcLoc("cube_bottom_top"))
                    .texture("side", "block/" + name + "_side")
                    .texture("top", "block/" + name + "_top")
                    .texture("bottom", "block/coma_stone");

            simpleBlock(block.get(), file);
        }

        private void log(RegistryObject<Block> block)
        {
            logBlock(UnsafeStuff.cast(block.get()));
        }

        private void cube(RegistryObject<Block> block)
        {
            simpleBlock(block.get(), cubeAll(block.get()));
        }

        private void plant(RegistryObject<Block> plant, RegistryObject<Block> potted)
        {
            ResourceLocation plantVariant = plant.getId();
            ResourceLocation pottedVariant = potted.getId();

            String plantName = plantVariant.getPath();
            String pottedName = pottedVariant.getPath();

            simpleBlock(plant.get(), models().cross(plantName, blockTexture(plant.get())).renderType("cutout"));

            simpleBlock(potted.get(), models().withExistingParent(pottedName, mcLoc("flower_pot_cross"))
                    .renderType("cutout")
                    .texture("plant", "block/" + plantName));
        }

        private void plant(RegistryObject<Block> plant)
        {
            ResourceLocation plantVariant = plant.getId();
            String plantName = plantVariant.getPath();

            simpleBlock(plant.get(), models().cross(plantName, blockTexture(plant.get())).renderType("cutout"));
        }

        private void stairs(RegistryObject<Block> stairs, RegistryObject<Block> texture)
        {
            stairsBlock(UnsafeStuff.cast(stairs.get()), blockTexture(texture.get()));
        }

        private void slab(RegistryObject<Block> slab, RegistryObject<Block> texture)
        {
            slabBlock(UnsafeStuff.cast(slab.get()), blockTexture(texture.get()), blockTexture(texture.get()));
        }

        @Override
        public void registerStatesAndModels()
        {
            shader(Blocks.SWIRL);
            shader(Blocks.SNOWFLAKE);
            shader(Blocks.WATERCOLOUR);
            shader(Blocks.MULTICOLOUR);
            shader(Blocks.FLARE);
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
            stairs(Blocks.GEOMETRIC_STAIRS, Blocks.GEOMETRIC_PLANKS);
            slab(Blocks.GEOMETRIC_SLAB, Blocks.GEOMETRIC_PLANKS);

            plant(Blocks.CATNIP, Blocks.POTTED_CATNIP);
            plant(Blocks.SPLITLEAF, Blocks.POTTED_SPLITLEAF);
            plant(Blocks.SNAKEROOT);
            plant(Blocks.TALL_SNAKEROOT);
            plant(Blocks.GEOMETRIC_SAPLING);
        }
    }

    public static class ItemModels extends ItemModelProvider
    {
        public ItemModels(PackOutput output, ExistingFileHelper helper)
        {
            super(output, Tourniqueted.MODID, helper);
        }

        private void egg(RegistryObject<Item> item)
        {
            withExistingParent(item.getId().getPath(), modLoc("egg"));
        }

        private void perspective(RegistryObject<Item> item)
        {
            withExistingParent(item.getId().getPath(), modLoc("perspective"));
        }

        private void cosmoSpine(RegistryObject<Item> item)
        {
            withExistingParent(item.getId().getPath(), modLoc("cosmo_spine"));
        }

        private void blockItem(RegistryObject<Block> block)
        {
            withExistingParent(block.getId().getPath(), modLoc("block/" + block.getId().getPath()));
        }

        private void handheld(RegistryObject<Item> item)
        {
            withExistingParent(item.getId().getPath(), mcLoc("handheld")).texture("layer0", modLoc("item/" + item.getId().getPath()));
        }

        private void palmHeld(RegistryObject<Item> item)
        {
            withExistingParent(item.getId().getPath(), modLoc("palm_held")).texture("layer0", modLoc("item/" + item.getId().getPath()));
        }

        private <T extends ItemLike> void item(RegistryObject<T> item)
        {
            withExistingParent(item.getId().getPath(), mcLoc("item/generated")).texture("layer0", modLoc("item/" + item.getId().getPath()));
        }

        private <T extends ItemLike> void itemForBlock(RegistryObject<T> block)
        {
            withExistingParent(block.getId().getPath(), mcLoc("item/generated")).texture("layer0", modLoc("block/" + block.getId().getPath()));
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

            blockItem(Blocks.GEOMETRIC_SAPLING);
            blockItem(Blocks.GEOMETRIC_LOG);
            blockItem(Blocks.GEOMETRIC_PLANKS);
            blockItem(Blocks.GEOMETRIC_STAIRS);
            blockItem(Blocks.GEOMETRIC_SLAB);

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

            itemForBlock(Blocks.CATNIP);
            itemForBlock(Blocks.SPLITLEAF);
            itemForBlock(Blocks.SNAKEROOT);
            itemForBlock(Blocks.TALL_SNAKEROOT);
            itemForBlock(Blocks.GEOMETRIC_SAPLING);
        }
    }

    public static class Languages extends LanguageProvider
    {
        public Languages(PackOutput output)
        {
            super(output, Tourniqueted.MODID, Locale.US.toString().toLowerCase());
        }

        private void tab(RegistryObject<CreativeModeTab> tab)
        {
            String name = tab.getId().getPath();
            add("itemGroup." + name, StringStuff.i18nt(name));
        }

        private <T extends EntityType<?>> void entity(RegistryObject<T> entity)
        {
            String name = entity.getId().getPath();

            if (name.equals("cosmo")) {
                add("entity." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
                add("entity." + Tourniqueted.MODID + "." + "alpha_" + name, StringStuff.i18nt("alpha_" + name));
                return;
            }

            add("entity." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
        }

        private <T extends Item> void item(RegistryObject<T> item)
        {
            String name = item.getId().getPath();
            add("item." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
        }

        private <T extends Block> void block(RegistryObject<T> block)
        {
            String name = block.getId().getPath();
            add("block." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
        }

        private void sound(RegistryObject<SoundEvent> sound)
        {
            String name = sound.getId().getPath();
            add("sounds." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
        }

        private <T extends MobEffect> void effect(RegistryObject<T> effect)
        {
            String name = effect.getId().getPath();
            add("effect." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
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
            block(Blocks.CATNIP);
            block(Blocks.SPLITLEAF);
            block(Blocks.SNAKEROOT);
            block(Blocks.TALL_SNAKEROOT);
            block(Blocks.GEOMETRIC_SAPLING);
            block(Blocks.GEOMETRIC_LOG);
            block(Blocks.GEOMETRIC_PLANKS);
            block(Blocks.GEOMETRIC_STAIRS);
            block(Blocks.GEOMETRIC_SLAB);
            block(Blocks.POTTED_CATNIP);
            block(Blocks.POTTED_SPLITLEAF);
            block(Blocks.ILLUSIVE_NYLIUM);
            block(Blocks.DELUSIVE_NYLIUM);
            block(Blocks.IMMATERIAL_NYLIUM);
            block(Blocks.SPECTRAL_NYLIUM);
            block(Blocks.SURREAL_NYLIUM);

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
    }
}
