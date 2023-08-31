package xyz.snaker.tq.rego;

import java.util.function.Supplier;

import xyz.snaker.snakerlib.utility.tools.ColourStuff;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.block.ShaderBlockItem;
import xyz.snaker.tq.level.item.CosmoSpine;
import xyz.snaker.tq.level.item.EmptyItem;
import xyz.snaker.tq.level.item.Tourniquet;
import xyz.snaker.tq.level.item.icon.BlockTabIcon;
import xyz.snaker.tq.level.item.icon.ItemTabIcon;
import xyz.snaker.tq.level.item.icon.MobTabIcon;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class Items
{
    public static final DeferredRegister<Item> REGISTRAR = DeferredRegister.create(ForgeRegistries.ITEMS, Tourniqueted.MODID);

    public static final RegistryObject<Item> RED_COSMO_SPINE = register("red_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> GREEN_COSMO_SPINE = register("green_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> BLUE_COSMO_SPINE = register("blue_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> YELLOW_COSMO_SPINE = register("yellow_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> PINK_COSMO_SPINE = register("pink_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> PURPLE_COSMO_SPINE = register("purple_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> ALPHA_COSMO_SPINE = register("alpha_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> ANTI_COSMO_SPINE = register("anti_cosmo_spine", CosmoSpine::new);

    public static final RegistryObject<Item> MOB_TAB_ICON = register("mob_tab_icon", MobTabIcon::new);
    public static final RegistryObject<Item> ITEM_TAB_ICON = register("item_tab_icon", ItemTabIcon::new);
    public static final RegistryObject<Item> BLOCK_TAB_ICON = register("block_tab_icon", BlockTabIcon::new);

    public static final RegistryObject<Item> TOURNIQUET = register("tourniquet", Tourniquet::new);
    public static final RegistryObject<Item> TOURNIQUET_CLASP = register("tourniquet_clasp", EmptyItem::new);
    public static final RegistryObject<Item> TOURNIQUET_WEBBING = register("tourniquet_webbing", EmptyItem::new);
    public static final RegistryObject<Item> SATURATED_TWINE = register("saturated_twine", EmptyItem::new);
    public static final RegistryObject<Item> WEATHERED_TWINE = register("weathered_twine", EmptyItem::new);

    public static final RegistryObject<Item> SWIRL_BLOCK = registerShaderBlockItem("swirl_block", Blocks.SWIRL);
    public static final RegistryObject<Item> SNOWFLAKE_BLOCK = registerShaderBlockItem("snowflake_block", Blocks.SNOWFLAKE);
    public static final RegistryObject<Item> WATERCOLOUR_BLOCK = registerShaderBlockItem("watercolour_block", Blocks.WATERCOLOUR);
    public static final RegistryObject<Item> MULTICOLOUR_BLOCK = registerShaderBlockItem("multicolour_block", Blocks.MULTICOLOUR);
    public static final RegistryObject<Item> FLARE_BLOCK = registerShaderBlockItem("flare_block", Blocks.FLARE);
    public static final RegistryObject<Item> STARRY_BLOCK = registerShaderBlockItem("starry_block", Blocks.STARRY);
    public static final RegistryObject<Item> GEOMETRIC_BLOCK = registerShaderBlockItem("geometric_block", Blocks.GEOMETRIC);
    public static final RegistryObject<Item> BURNING_BLOCK = registerShaderBlockItem("burning_block", Blocks.BURNING);
    public static final RegistryObject<Item> FOGGY_BLOCK = registerShaderBlockItem("foggy_block", Blocks.FOGGY);

    public static final RegistryObject<Item> COSMO_SPAWN_EGG = registerSpawnEgg("cosmo_spawn_egg", Entities.COSMO);
    public static final RegistryObject<Item> SNIPE_SPAWN_EGG = registerSpawnEgg("snipe_spawn_egg", Entities.SNIPE);
    public static final RegistryObject<Item> FLARE_SPAWN_EGG = registerSpawnEgg("flare_spawn_egg", Entities.FLARE);
    public static final RegistryObject<Item> COSMIC_CREEPER_SPAWN_EGG = registerSpawnEgg("cosmic_creeper_spawn_egg", Entities.COSMIC_CREEPER);
    public static final RegistryObject<Item> FROLICKER_SPAWN_EGG = registerSpawnEgg("frolicker_spawn_egg", Entities.FROLICKER);
    public static final RegistryObject<Item> FLUTTERFLY_SPAWN_EGG = registerSpawnEgg("flutterfly_spawn_egg", Entities.FLUTTERFLY);
    public static final RegistryObject<Item> UTTERFLY_SPAWN_EGG = registerSpawnEgg("utterfly_spawn_egg", Entities.UTTERFLY);
    public static final RegistryObject<Item> LEET_SPAWN_EGG = registerSpawnEgg("leet_spawn_egg", Entities.LEET);

    static RegistryObject<Item> register(String name, Supplier<Item> item)
    {
        return REGISTRAR.register(name, item);
    }

    static RegistryObject<Item> registerSpawnEgg(String name, Supplier<? extends EntityType<? extends Mob>> mob)
    {
        Supplier<ForgeSpawnEggItem> egg = () -> new ForgeSpawnEggItem(mob, ColourStuff.randomHex(), ColourStuff.randomHex(), new Item.Properties());
        return REGISTRAR.register(name, egg);
    }

    static RegistryObject<Item> registerShaderBlockItem(String name, RegistryObject<Block> parent)
    {
        return REGISTRAR.register(name, () -> new ShaderBlockItem(parent));
    }
}
