package bytesnek.tq.rego;

import java.util.function.Supplier;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import bytesnek.hiss.utility.Colours;
import bytesnek.snakerlib.resources.ResourceReference;
import bytesnek.tq.Tourniqueted;
import bytesnek.tq.level.block.ShaderBlockItem;
import bytesnek.tq.level.display.tab.BlockTabDisplay;
import bytesnek.tq.level.display.tab.EntityTabDisplay;
import bytesnek.tq.level.display.tab.ItemTabDisplay;
import bytesnek.tq.level.item.CosmoSpine;
import bytesnek.tq.level.item.DebugSwordItem;
import bytesnek.tq.level.item.EmptyItem;
import bytesnek.tq.level.item.Tourniquet;

import static net.minecraft.world.item.Items.BUCKET;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class Items
{
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Tourniqueted.MODID);

    public static final RegistryObject<Item> RED_COSMO_SPINE = register("red_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> GREEN_COSMO_SPINE = register("green_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> BLUE_COSMO_SPINE = register("blue_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> YELLOW_COSMO_SPINE = register("yellow_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> PINK_COSMO_SPINE = register("pink_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> PURPLE_COSMO_SPINE = register("purple_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> ALPHA_COSMO_SPINE = register("alpha_cosmo_spine", CosmoSpine::new);

    public static final RegistryObject<Item> ENTITY_TAB_DISPLAY = registerTabDisplay("entity", EntityTabDisplay::new);
    public static final RegistryObject<Item> ITEM_TAB_DISPLAY = registerTabDisplay("item", ItemTabDisplay::new);
    public static final RegistryObject<Item> BLOCK_TAB_DISPLAY = registerTabDisplay("block", BlockTabDisplay::new);

    public static final RegistryObject<Item> TOURNIQUET = register("tourniquet", Tourniquet::new);
    public static final RegistryObject<Item> TOURNIQUET_WEBBING = register("tourniquet_webbing", EmptyItem::new);
    public static final RegistryObject<Item> SATURATED_TWINE = register("saturated_twine", EmptyItem::new);
    public static final RegistryObject<Item> WEATHERED_TWINE = register("weathered_twine", EmptyItem::new);
    public static final RegistryObject<Item> FLUTTERFLY_KERATIN = register("flutterfly_keratin", EmptyItem::new);
    public static final RegistryObject<Item> DEBUG_SWORD = register("debug_sword", DebugSwordItem::new);

    public static final RegistryObject<Item> SWIRL_BLOCK = registerShaderBlockItem(Blocks.SWIRL);
    public static final RegistryObject<Item> SNOWFLAKE_BLOCK = registerShaderBlockItem(Blocks.SNOWFLAKE);
    public static final RegistryObject<Item> WATERCOLOUR_BLOCK = registerShaderBlockItem(Blocks.WATERCOLOUR);
    public static final RegistryObject<Item> MULTICOLOUR_BLOCK = registerShaderBlockItem(Blocks.MULTICOLOUR);
    public static final RegistryObject<Item> FLARE_BLOCK = registerShaderBlockItem(Blocks.FLARE);
    public static final RegistryObject<Item> STARRY_BLOCK = registerShaderBlockItem(Blocks.STARRY);
    public static final RegistryObject<Item> GEOMETRIC_BLOCK = registerShaderBlockItem(Blocks.GEOMETRIC);
    public static final RegistryObject<Item> BURNING_BLOCK = registerShaderBlockItem(Blocks.BURNING);
    public static final RegistryObject<Item> FOGGY_BLOCK = registerShaderBlockItem(Blocks.FOGGY);
    public static final RegistryObject<Item> STATIC_BLOCK = registerShaderBlockItem(Blocks.STATIC);

    public static final RegistryObject<ForgeSpawnEggItem> COSMO_SPAWN_EGG = registerSpawnEgg(Entities.COSMO);
    public static final RegistryObject<ForgeSpawnEggItem> SNIPE_SPAWN_EGG = registerSpawnEgg(Entities.SNIPE);
    public static final RegistryObject<ForgeSpawnEggItem> FLARE_SPAWN_EGG = registerSpawnEgg(Entities.FLARE);
    public static final RegistryObject<ForgeSpawnEggItem> COSMIC_CREEPER_SPAWN_EGG = registerSpawnEgg(Entities.COSMIC_CREEPER);
    public static final RegistryObject<ForgeSpawnEggItem> COSMIC_CREEPERITE_SPAWN_EGG = registerSpawnEgg(Entities.COSMIC_CREEPERITE);
    public static final RegistryObject<ForgeSpawnEggItem> FROLICKER_SPAWN_EGG = registerSpawnEgg(Entities.FROLICKER);
    public static final RegistryObject<ForgeSpawnEggItem> FLUTTERFLY_SPAWN_EGG = registerSpawnEgg(Entities.FLUTTERFLY);
    public static final RegistryObject<ForgeSpawnEggItem> CRANKY_FLUTTERFLY_SPAWN_EGG = registerSpawnEgg(Entities.CRANKY_FLUTTERFLY);
    public static final RegistryObject<ForgeSpawnEggItem> UTTERFLY_SPAWN_EGG = registerSpawnEgg(Entities.UTTERFLY);

    public static final RegistryObject<Item> COMASOTE_BUCKET = REGISTER.register("comasote_bucket", () -> new BucketItem(Fluids.COMASOTE, new Item.Properties().craftRemainder(BUCKET).stacksTo(1)));

    static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item)
    {
        return REGISTER.register(name, item);
    }

    static RegistryObject<Item> registerDisplay(String name, Supplier<Item> item)
    {
        return register(name + "_display", item);
    }

    static RegistryObject<Item> registerTabDisplay(String name, Supplier<Item> item)
    {
        return registerDisplay(name + "_tab", item);
    }

    static RegistryObject<Item> registerShaderBlockItem(RegistryObject<Block> block)
    {
        return register(block.getId().getPath(), () -> new ShaderBlockItem(block));
    }

    static RegistryObject<ForgeSpawnEggItem> registerSpawnEgg(RegistryObject<? extends EntityType<? extends Mob>> mob)
    {
        Supplier<ForgeSpawnEggItem> egg = () -> new ForgeSpawnEggItem(mob, Colours.randomHex(), Colours.randomHex(), new Item.Properties());
        return register(mob.getId().getPath() + "_spawn_egg", egg);
    }

    public static class Tags
    {
        public static final TagKey<Item> COSMO_SPINES = tag("cosmo_spines");
        public static final TagKey<Item> DROPPABLE = tag("droppable");

        public static TagKey<Item> tag(String name)
        {
            return ItemTags.create(new ResourceReference(name));
        }
    }
}
