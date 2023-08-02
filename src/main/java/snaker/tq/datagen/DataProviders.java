package snaker.tq.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;
import snaker.snakerlib.utility.SnakerUtil;
import snaker.tq.Tourniqueted;
import snaker.tq.rego.Rego;

import java.util.Locale;

/**
 * Created by SnakerBone on 21/03/2023
 **/
public class DataProviders
{
    public static class BlockStates extends BlockStateProvider
    {
        public BlockStates(PackOutput output, ExistingFileHelper helper)
        {
            super(output, Tourniqueted.MODID, helper);
        }

        private void dream(RegistryObject<Block> block)
        {
            String name = block.getId().getPath();
            ModelFile file = models().withExistingParent(name, modLoc("dream"));
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

        @Override
        protected void registerStatesAndModels()
        {
            dream(Rego.BLOCK_SWIRL);
            dream(Rego.BLOCK_SNOWFLAKE);
            dream(Rego.BLOCK_WATERCOLOUR);
            dream(Rego.BLOCK_MULTICOLOUR);
            dream(Rego.BLOCK_FLARE);
            dream(Rego.BLOCK_STARRY);

            nylium(Rego.BLOCK_ILLUSIVE_NYLIUM);
            nylium(Rego.BLOCK_DELUSIVE_NYLIUM);
            nylium(Rego.BLOCK_IMMATERIAL_NYLIUM);
            nylium(Rego.BLOCK_SPECTRAL_NYLIUM);
            nylium(Rego.BLOCK_SURREAL_NYLIUM);

            cube(Rego.BLOCK_COMA_STONE);

            plant(Rego.BLOCK_CATNIP, Rego.BLOCK_POTTED_CATNIP);
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

        private <T extends ItemLike> void block(RegistryObject<T> block)
        {
            withExistingParent(block.getId().getPath(), mcLoc("item/generated")).texture("layer0", modLoc("block/" + block.getId().getPath()));
        }

        @Override
        protected void registerModels()
        {
            blockItem(Rego.BLOCK_SWIRL);
            blockItem(Rego.BLOCK_SNOWFLAKE);
            blockItem(Rego.BLOCK_WATERCOLOUR);
            blockItem(Rego.BLOCK_MULTICOLOUR);
            blockItem(Rego.BLOCK_FLARE);
            blockItem(Rego.BLOCK_STARRY);
            blockItem(Rego.BLOCK_COMA_STONE);
            blockItem(Rego.BLOCK_DELUSIVE_NYLIUM);
            blockItem(Rego.BLOCK_ILLUSIVE_NYLIUM);
            blockItem(Rego.BLOCK_IMMATERIAL_NYLIUM);
            blockItem(Rego.BLOCK_SPECTRAL_NYLIUM);
            blockItem(Rego.BLOCK_SURREAL_NYLIUM);

            cosmoSpine(Rego.ITEM_RED_COSMO_SPINE);
            cosmoSpine(Rego.ITEM_GREEN_COSMO_SPINE);
            cosmoSpine(Rego.ITEM_BLUE_COSMO_SPINE);
            cosmoSpine(Rego.ITEM_YELLOW_COSMO_SPINE);
            cosmoSpine(Rego.ITEM_PINK_COSMO_SPINE);
            cosmoSpine(Rego.ITEM_PURPLE_COSMO_SPINE);
            cosmoSpine(Rego.ITEM_ALPHA_COSMO_SPINE);
            cosmoSpine(Rego.ITEM_ANTI_COSMO_SPINE);

            egg(Rego.ITEM_COSMO_SPAWN_EGG);
            egg(Rego.ITEM_FLARE_SPAWN_EGG);
            egg(Rego.ITEM_FLUTTERFLY_SPAWN_EGG);
            egg(Rego.ITEM_FROLICKER_SPAWN_EGG);
            egg(Rego.ITEM_COSMIC_CREEPER_SPAWN_EGG);
            egg(Rego.ITEM_UTTERFLY_SPAWN_EGG);
            egg(Rego.ITEM_ANTI_COSMO_SPAWN_EGG);
            egg(Rego.ITEM_SNIPE_SPAWN_EGG);
            egg(Rego.ITEM_EERIE_CRETIN_SPAWN_EGG);
            egg(Rego.ITEM_LEET_SPAWN_EGG);
            egg(Rego.ITEM_TEST_SPAWN_EGG);

            perspective(Rego.ICON_MOB_TAB);
            perspective(Rego.ICON_ITEM_TAB);
            perspective(Rego.ICON_BLOCK_TAB);

            item(Rego.ITEM_TOURNIQUET);

            block(Rego.BLOCK_CATNIP);
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
            add("itemGroup." + name, SnakerUtil.translate(name));
        }

        private <T extends EntityType<?>> void entity(RegistryObject<T> entity)
        {
            String name = entity.getId().getPath();

            if (name.equals("cosmo")) {
                add("entity." + Tourniqueted.MODID + "." + name, SnakerUtil.translate(name));
                add("entity." + Tourniqueted.MODID + "." + "alpha_" + name, SnakerUtil.translate("alpha_" + name));
                return;
            }

            add("entity." + Tourniqueted.MODID + "." + name, SnakerUtil.translate(name));
        }

        private <T extends Item> void item(RegistryObject<T> item)
        {
            String name = item.getId().getPath();
            add("item." + Tourniqueted.MODID + "." + name, SnakerUtil.translate(name));
        }

        private <T extends Block> void block(RegistryObject<T> block)
        {
            String name = block.getId().getPath();
            add("block." + Tourniqueted.MODID + "." + name, SnakerUtil.translate(name));
        }

        private void sound(RegistryObject<SoundEvent> sound)
        {
            String name = sound.getId().getPath();
            add("sounds." + Tourniqueted.MODID + "." + name, SnakerUtil.translate(name));
        }

        private <T extends MobEffect> void effect(RegistryObject<T> effect)
        {
            String name = effect.getId().getPath();
            add("effect." + Tourniqueted.MODID + "." + name, SnakerUtil.translate(name));
        }

        @Override
        protected void addTranslations()
        {
            tab(Rego.TAB_ITEMS);
            tab(Rego.TAB_BLOCKS);
            tab(Rego.TAB_MOBS);

            item(Rego.ITEM_COSMO_SPAWN_EGG);
            item(Rego.ITEM_SNIPE_SPAWN_EGG);
            item(Rego.ITEM_FLARE_SPAWN_EGG);
            item(Rego.ITEM_COSMIC_CREEPER_SPAWN_EGG);
            item(Rego.ITEM_FROLICKER_SPAWN_EGG);
            item(Rego.ITEM_FLUTTERFLY_SPAWN_EGG);
            item(Rego.ITEM_UTTERFLY_SPAWN_EGG);
            item(Rego.ITEM_ANTI_COSMO_SPAWN_EGG);
            item(Rego.ITEM_EERIE_CRETIN_SPAWN_EGG);
            item(Rego.ITEM_LEET_SPAWN_EGG);

            item(Rego.ITEM_RED_COSMO_SPINE);
            item(Rego.ITEM_GREEN_COSMO_SPINE);
            item(Rego.ITEM_BLUE_COSMO_SPINE);
            item(Rego.ITEM_YELLOW_COSMO_SPINE);
            item(Rego.ITEM_PINK_COSMO_SPINE);
            item(Rego.ITEM_PURPLE_COSMO_SPINE);
            item(Rego.ITEM_ALPHA_COSMO_SPINE);
            item(Rego.ITEM_ANTI_COSMO_SPINE);
            item(Rego.ICON_MOB_TAB);
            item(Rego.ICON_ITEM_TAB);
            item(Rego.ICON_BLOCK_TAB);
            item(Rego.ITEM_TOURNIQUET);

            block(Rego.BLOCK_SWIRL);
            block(Rego.BLOCK_SNOWFLAKE);
            block(Rego.BLOCK_STARRY);
            block(Rego.BLOCK_FLARE);
            block(Rego.BLOCK_MULTICOLOUR);
            block(Rego.BLOCK_WATERCOLOUR);
            block(Rego.BLOCK_COMA_STONE);
            block(Rego.BLOCK_CATNIP);
            block(Rego.BLOCK_POTTED_CATNIP);
            block(Rego.BLOCK_ILLUSIVE_NYLIUM);
            block(Rego.BLOCK_DELUSIVE_NYLIUM);
            block(Rego.BLOCK_IMMATERIAL_NYLIUM);
            block(Rego.BLOCK_SPECTRAL_NYLIUM);
            block(Rego.BLOCK_SURREAL_NYLIUM);

            entity(Rego.ENTITY_COSMO);
            entity(Rego.ENTITY_SNIPE);
            entity(Rego.ENTITY_FLARE);
            entity(Rego.ENTITY_COSMIC_CREEPER);
            entity(Rego.ENTITY_FROLICKER);
            entity(Rego.ENTITY_FLUTTERFLY);
            entity(Rego.ENTITY_UTTERFLY);
            entity(Rego.ENTITY_EXPLOSIVE_HOMMING_ARROW);
            entity(Rego.ENTITY_HOMMING_ARROW);
            entity(Rego.ENTITY_EERIE_CRETIN);
            entity(Rego.ENTITY_LEET);

            sound(Rego.SOUND_CONFUSE);
            sound(Rego.SOUND_EARTH);
            sound(Rego.SOUND_FOG);
            sound(Rego.SOUND_GAZE);
            sound(Rego.SOUND_NIGHT);
            sound(Rego.SOUND_SHOOT);
            sound(Rego.SOUND_UTTERFLY_AMBIENT);
            sound(Rego.SOUND_FLUTTERFLY_AMBIENT);
            sound(Rego.SOUND_SNIPE_AMBIENT);
            sound(Rego.SOUND_SNIPE_HURT);
            sound(Rego.SOUND_COSMO_HURT);
            sound(Rego.SOUND_ENTITY_DEATH);
            sound(Rego.SOUND_TING);
            sound(Rego.SOUND_PEW);
            sound(Rego.SOUND_FIELD);
            sound(Rego.SOUND_HIT);
            sound(Rego.SOUND_LASER);
            sound(Rego.SOUND_PULSE);

            effect(Rego.EFFECT_SYNCOPE);
        }
    }
}
