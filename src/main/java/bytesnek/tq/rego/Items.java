package bytesnek.tq.rego;

import java.util.function.Supplier;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import bytesnek.hiss.sneaky.Sneaky;
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
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(BuiltInRegistries.ITEM, Tourniqueted.MODID);

    public static final Supplier<Item> RED_COSMO_SPINE = register("red_cosmo_spine", CosmoSpine::new);
    public static final Supplier<Item> GREEN_COSMO_SPINE = register("green_cosmo_spine", CosmoSpine::new);
    public static final Supplier<Item> BLUE_COSMO_SPINE = register("blue_cosmo_spine", CosmoSpine::new);
    public static final Supplier<Item> YELLOW_COSMO_SPINE = register("yellow_cosmo_spine", CosmoSpine::new);
    public static final Supplier<Item> PINK_COSMO_SPINE = register("pink_cosmo_spine", CosmoSpine::new);
    public static final Supplier<Item> PURPLE_COSMO_SPINE = register("purple_cosmo_spine", CosmoSpine::new);
    public static final Supplier<Item> ALPHA_COSMO_SPINE = register("alpha_cosmo_spine", CosmoSpine::new);

    public static final Supplier<Item> ENTITY_TAB_DISPLAY = registerTabDisplay("entity", EntityTabDisplay::new);
    public static final Supplier<Item> ITEM_TAB_DISPLAY = registerTabDisplay("item", ItemTabDisplay::new);
    public static final Supplier<Item> BLOCK_TAB_DISPLAY = registerTabDisplay("block", BlockTabDisplay::new);

    public static final Supplier<Item> TOURNIQUET = register("tourniquet", Tourniquet::new);
    public static final Supplier<Item> TOURNIQUET_WEBBING = register("tourniquet_webbing", EmptyItem::new);
    public static final Supplier<Item> SATURATED_TWINE = register("saturated_twine", EmptyItem::new);
    public static final Supplier<Item> WEATHERED_TWINE = register("weathered_twine", EmptyItem::new);
    public static final Supplier<Item> FLUTTERFLY_KERATIN = register("flutterfly_keratin", EmptyItem::new);
    public static final Supplier<Item> DEBUG_SWORD = register("debug_sword", DebugSwordItem::new);

    public static final Supplier<Item> SWIRL_BLOCK = registerShaderBlockItem(Blocks.SWIRL);
    public static final Supplier<Item> SNOWFLAKE_BLOCK = registerShaderBlockItem(Blocks.SNOWFLAKE);
    public static final Supplier<Item> WATERCOLOUR_BLOCK = registerShaderBlockItem(Blocks.WATERCOLOUR);
    public static final Supplier<Item> MULTICOLOUR_BLOCK = registerShaderBlockItem(Blocks.MULTICOLOUR);
    public static final Supplier<Item> FLARE_BLOCK = registerShaderBlockItem(Blocks.FLARE);
    public static final Supplier<Item> STARRY_BLOCK = registerShaderBlockItem(Blocks.STARRY);
    public static final Supplier<Item> GEOMETRIC_BLOCK = registerShaderBlockItem(Blocks.GEOMETRIC);
    public static final Supplier<Item> BURNING_BLOCK = registerShaderBlockItem(Blocks.BURNING);
    public static final Supplier<Item> FOGGY_BLOCK = registerShaderBlockItem(Blocks.FOGGY);
    public static final Supplier<Item> STATIC_BLOCK = registerShaderBlockItem(Blocks.STATIC);

    public static final Supplier<DeferredSpawnEggItem> COSMO_SPAWN_EGG = registerSpawnEgg(Entities.COSMO);
    public static final Supplier<DeferredSpawnEggItem> SNIPE_SPAWN_EGG = registerSpawnEgg(Entities.SNIPE);
    public static final Supplier<DeferredSpawnEggItem> FLARE_SPAWN_EGG = registerSpawnEgg(Entities.FLARE);
    public static final Supplier<DeferredSpawnEggItem> COSMIC_CREEPER_SPAWN_EGG = registerSpawnEgg(Entities.COSMIC_CREEPER);
    public static final Supplier<DeferredSpawnEggItem> COSMIC_CREEPERITE_SPAWN_EGG = registerSpawnEgg(Entities.COSMIC_CREEPERITE);
    public static final Supplier<DeferredSpawnEggItem> FROLICKER_SPAWN_EGG = registerSpawnEgg(Entities.FROLICKER);
    public static final Supplier<DeferredSpawnEggItem> FLUTTERFLY_SPAWN_EGG = registerSpawnEgg(Entities.FLUTTERFLY);
    public static final Supplier<DeferredSpawnEggItem> CRANKY_FLUTTERFLY_SPAWN_EGG = registerSpawnEgg(Entities.CRANKY_FLUTTERFLY);
    public static final Supplier<DeferredSpawnEggItem> UTTERFLY_SPAWN_EGG = registerSpawnEgg(Entities.UTTERFLY);

    public static final Supplier<Item> COMASOTE_BUCKET = register("comasote_bucket", () -> new BucketItem(Fluids.COMASOTE, new Item.Properties().craftRemainder(BUCKET).stacksTo(1)));

    static <T extends Item> Supplier<T> register(String name, Supplier<T> item)
    {
        return REGISTER.register(name, item);
    }

    static Supplier<Item> registerDisplay(String name, Supplier<Item> item)
    {
        return register(name + "_display", item);
    }

    static Supplier<Item> registerTabDisplay(String name, Supplier<Item> item)
    {
        return registerDisplay(name + "_tab", item);
    }

    static Supplier<Item> registerShaderBlockItem(Supplier<Block> block)
    {
        DeferredHolder<Block, Block> holder = Sneaky.cast(block);
        return register(holder.getId().getPath(), () -> new ShaderBlockItem(block));
    }

    static Supplier<DeferredSpawnEggItem> registerSpawnEgg(Supplier<? extends EntityType<? extends Mob>> mob)
    {
        DeferredHolder<EntityType<?>, ? extends EntityType<? extends Mob>> holder = Sneaky.cast(mob);
        Supplier<DeferredSpawnEggItem> egg = () -> new DeferredSpawnEggItem(mob, Colours.randomHex(), Colours.randomHex(), new Item.Properties());
        return register(holder.getId().getPath() + "_spawn_egg", egg);
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
