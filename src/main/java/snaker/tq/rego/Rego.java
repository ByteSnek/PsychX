package snaker.tq.rego;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import snaker.snakerlib.SnakerLib;
import snaker.snakerlib.data.SnakerConstants;
import snaker.snakerlib.internal.AsynchronousHashMap;
import snaker.snakerlib.utility.ResourcePath;
import snaker.snakerlib.utility.SketchyStuff;
import snaker.tq.Tourniqueted;
import snaker.tq.level.block.ComatoseNyliumBlock;
import snaker.tq.level.block.ShaderBlock;
import snaker.tq.level.block.ShaderBlockItem;
import snaker.tq.level.block.entity.ShaderBlockEntity;
import snaker.tq.level.effect.Syncope;
import snaker.tq.level.entity.EntityDropHandler;
import snaker.tq.level.entity.boss.AntiCosmo;
import snaker.tq.level.entity.boss.Utterfly;
import snaker.tq.level.entity.creature.Flutterfly;
import snaker.tq.level.entity.creature.Frolicker;
import snaker.tq.level.entity.mob.*;
import snaker.tq.level.entity.projectile.CosmicRay;
import snaker.tq.level.entity.projectile.ExplosiveHommingArrow;
import snaker.tq.level.entity.projectile.HommingArrow;
import snaker.tq.level.item.CosmoSpine;
import snaker.tq.level.item.Tourniquet;
import snaker.tq.level.item.icon.BlockTabIcon;
import snaker.tq.level.item.icon.ItemTabIcon;
import snaker.tq.level.item.icon.MobTabIcon;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by SnakerBone on 12/12/2022
 **/
@Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Rego
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Tourniqueted.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Tourniqueted.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Tourniqueted.MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Tourniqueted.MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Tourniqueted.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Tourniqueted.MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Tourniqueted.MODID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Tourniqueted.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Tourniqueted.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Tourniqueted.MODID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Tourniqueted.MODID);
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(Registries.BIOME, Tourniqueted.MODID);

    public static final RegistryObject<Item> ITEM_RED_COSMO_SPINE = item("red_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> ITEM_GREEN_COSMO_SPINE = item("green_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> ITEM_BLUE_COSMO_SPINE = item("blue_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> ITEM_YELLOW_COSMO_SPINE = item("yellow_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> ITEM_PINK_COSMO_SPINE = item("pink_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> ITEM_PURPLE_COSMO_SPINE = item("purple_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> ITEM_ALPHA_COSMO_SPINE = item("alpha_cosmo_spine", CosmoSpine::new);
    public static final RegistryObject<Item> ITEM_ANTI_COSMO_SPINE = item("anti_cosmo_spine", CosmoSpine::new);

    public static final RegistryObject<Item> ICON_MOB_TAB = item("mob_tab_icon", MobTabIcon::new);
    public static final RegistryObject<Item> ICON_ITEM_TAB = item("item_tab_icon", ItemTabIcon::new);
    public static final RegistryObject<Item> ICON_BLOCK_TAB = item("block_tab_icon", BlockTabIcon::new);

    public static final RegistryObject<Item> ITEM_SWIRL_BLOCK = item("swirl_block", () -> new ShaderBlockItem(Rego.BLOCK_SWIRL.get()));
    public static final RegistryObject<Item> ITEM_SNOWFLAKE_BLOCK = item("snowflake_block", () -> new ShaderBlockItem(Rego.BLOCK_SNOWFLAKE.get()));
    public static final RegistryObject<Item> ITEM_WATERCOLOUR_BLOCK = item("watercolour_block", () -> new ShaderBlockItem(Rego.BLOCK_WATERCOLOUR.get()));
    public static final RegistryObject<Item> ITEM_MULTICOLOUR_BLOCK = item("multicolour_block", () -> new ShaderBlockItem(Rego.BLOCK_MULTICOLOUR.get()));
    public static final RegistryObject<Item> ITEM_FLARE_BLOCK = item("flare_block", () -> new ShaderBlockItem(Rego.BLOCK_FLARE.get()));
    public static final RegistryObject<Item> ITEM_STARRY_BLOCK = item("starry_block", () -> new ShaderBlockItem(Rego.BLOCK_STARRY.get()));

    public static final RegistryObject<Block> BLOCK_SWIRL = blockWithoutItem("swirl_block", () -> new ShaderBlock<>(Rego.BE_SWIRL));
    public static final RegistryObject<Block> BLOCK_SNOWFLAKE = blockWithoutItem("snowflake_block", () -> new ShaderBlock<>(Rego.BE_SNOWFLAKE));
    public static final RegistryObject<Block> BLOCK_WATERCOLOUR = blockWithoutItem("watercolour_block", () -> new ShaderBlock<>(Rego.BE_WATERCOLOUR));
    public static final RegistryObject<Block> BLOCK_MULTICOLOUR = blockWithoutItem("multicolour_block", () -> new ShaderBlock<>(Rego.BE_MULTICOLOUR));
    public static final RegistryObject<Block> BLOCK_FLARE = blockWithoutItem("flare_block", () -> new ShaderBlock<>(Rego.BE_FLARE));
    public static final RegistryObject<Block> BLOCK_STARRY = blockWithoutItem("starry_block", () -> new ShaderBlock<>(Rego.BE_STARRY));

    public static final RegistryObject<Block> BLOCK_CATNIP = blockWithItem("catnip", () -> new FlowerBlock(Rego.EFFECT_SYNCOPE::get, 600, SnakerConstants.BlockProperties.PLANT));
    public static final RegistryObject<Block> BLOCK_SHRUB = blockWithItem("shrub", () -> new FlowerBlock(Rego.EFFECT_SYNCOPE::get, 600, SnakerConstants.BlockProperties.PLANT));
    public static final RegistryObject<Block> BLOCK_POTTED_CATNIP = BLOCKS.register("potted_catnip", () -> new FlowerPotBlock(() -> SketchyStuff.cast(Blocks.FLOWER_POT), BLOCK_CATNIP, SnakerConstants.BlockProperties.NORMAL));
    public static final RegistryObject<Block> BLOCK_POTTED_SHRUB = BLOCKS.register("potted_shrub", () -> new FlowerPotBlock(() -> SketchyStuff.cast(Blocks.FLOWER_POT), BLOCK_SHRUB, SnakerConstants.BlockProperties.NORMAL));

    public static final RegistryObject<Block> BLOCK_COMA_STONE = blockWithItem("coma_stone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.NONE).strength(0.5F).sound(SoundType.NETHER_ORE)));
    public static final RegistryObject<Block> BLOCK_DELUSIVE_NYLIUM = blockWithItem("delusive_nylium", ComatoseNyliumBlock::new);
    public static final RegistryObject<Block> BLOCK_ILLUSIVE_NYLIUM = blockWithItem("illusive_nylium", ComatoseNyliumBlock::new);
    public static final RegistryObject<Block> BLOCK_IMMATERIAL_NYLIUM = blockWithItem("immaterial_nylium", ComatoseNyliumBlock::new);
    public static final RegistryObject<Block> BLOCK_SPECTRAL_NYLIUM = blockWithItem("spectral_nylium", ComatoseNyliumBlock::new);
    public static final RegistryObject<Block> BLOCK_SURREAL_NYLIUM = blockWithItem("surreal_nylium", ComatoseNyliumBlock::new);

    public static final RegistryObject<BlockEntityType<ShaderBlockEntity.Swirl>> BE_SWIRL = BLOCK_ENTITIES.register("swirl_be", () -> BlockEntityType.Builder.of(ShaderBlockEntity.Swirl::new, BLOCK_SWIRL.get()).build(null));
    public static final RegistryObject<BlockEntityType<ShaderBlockEntity.Snowflake>> BE_SNOWFLAKE = BLOCK_ENTITIES.register("snowflake_be", () -> BlockEntityType.Builder.of(ShaderBlockEntity.Snowflake::new, BLOCK_SNOWFLAKE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ShaderBlockEntity.WaterColour>> BE_WATERCOLOUR = BLOCK_ENTITIES.register("watercolour_be", () -> BlockEntityType.Builder.of(ShaderBlockEntity.WaterColour::new, BLOCK_WATERCOLOUR.get()).build(null));
    public static final RegistryObject<BlockEntityType<ShaderBlockEntity.MultiColour>> BE_MULTICOLOUR = BLOCK_ENTITIES.register("multicolour_be", () -> BlockEntityType.Builder.of(ShaderBlockEntity.MultiColour::new, BLOCK_MULTICOLOUR.get()).build(null));
    public static final RegistryObject<BlockEntityType<ShaderBlockEntity.Flare>> BE_FLARE = BLOCK_ENTITIES.register("flare_be", () -> BlockEntityType.Builder.of(ShaderBlockEntity.Flare::new, BLOCK_FLARE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ShaderBlockEntity.Starry>> BE_STARRY = BLOCK_ENTITIES.register("starry_be", () -> BlockEntityType.Builder.of(ShaderBlockEntity.Starry::new, BLOCK_STARRY.get()).build(null));

    public static final RegistryObject<SoundEvent> SOUND_CONFUSE = sound("confuse");
    public static final RegistryObject<SoundEvent> SOUND_EARTH = sound("earth");
    public static final RegistryObject<SoundEvent> SOUND_FOG = sound("fog");
    public static final RegistryObject<SoundEvent> SOUND_GAZE = sound("gaze");
    public static final RegistryObject<SoundEvent> SOUND_NIGHT = sound("night");
    public static final RegistryObject<SoundEvent> SOUND_TING = sound("ting");
    public static final RegistryObject<SoundEvent> SOUND_PEW = sound("pew");
    public static final RegistryObject<SoundEvent> SOUND_FIELD = sound("field");
    public static final RegistryObject<SoundEvent> SOUND_HIT = sound("hit");
    public static final RegistryObject<SoundEvent> SOUND_LASER = sound("laser");
    public static final RegistryObject<SoundEvent> SOUND_PULSE = sound("pulse");
    public static final RegistryObject<SoundEvent> SOUND_COSMO_HURT = sound("cosmo_hurt");
    public static final RegistryObject<SoundEvent> SOUND_SHOOT = sound("utterfly_shoot");
    public static final RegistryObject<SoundEvent> SOUND_UTTERFLY_AMBIENT = sound("utterfly_ambient");
    public static final RegistryObject<SoundEvent> SOUND_FLUTTERFLY_AMBIENT = sound("flutterfly_ambient");
    public static final RegistryObject<SoundEvent> SOUND_SNIPE_HURT = sound("snipe_hurt");
    public static final RegistryObject<SoundEvent> SOUND_SNIPE_AMBIENT = sound("snipe_ambient");
    public static final RegistryObject<SoundEvent> SOUND_ENTITY_DEATH = sound("entity_death");

    public static final RegistryObject<CreativeModeTab> TAB_ITEMS = tab("torniqueted_items", () -> Rego.ICON_ITEM_TAB.get().getDefaultInstance());
    public static final RegistryObject<CreativeModeTab> TAB_BLOCKS = tab("torniqueted_blocks", () -> Rego.ICON_BLOCK_TAB.get().getDefaultInstance());
    public static final RegistryObject<CreativeModeTab> TAB_MOBS = tab("torniqueted_mobs", () -> Rego.ICON_MOB_TAB.get().getDefaultInstance());

    public static final RegistryObject<EntityType<Cosmo>> ENTITY_COSMO = mob("cosmo", Cosmo::new, 1F, 2F);
    public static final RegistryObject<EntityType<Snipe>> ENTITY_SNIPE = mob("snipe", Snipe::new, 1F, 1.5F);
    public static final RegistryObject<EntityType<Flare>> ENTITY_FLARE = mob("flare", Flare::new, 1F, 2F);
    public static final RegistryObject<EntityType<CosmicCreeper>> ENTITY_COSMIC_CREEPER = mob("cosmic_creeper", CosmicCreeper::new, 1F, 2F);
    public static final RegistryObject<EntityType<Utterfly>> ENTITY_UTTERFLY = mob("utterfly", Utterfly::new, 6F, 3F);
    public static final RegistryObject<EntityType<AntiCosmo>> ENTITY_ANTI_COSMO = mob("anti_comso", AntiCosmo::new, 1F, 2F);
    public static final RegistryObject<EntityType<Frolicker>> ENTITY_FROLICKER = creature("frolicker", Frolicker::new, 0.5F, 0.5F);
    public static final RegistryObject<EntityType<Flutterfly>> ENTITY_FLUTTERFLY = creature("flutterfly", Flutterfly::new, 0.5F, 0.5F);
    public static final RegistryObject<EntityType<HommingArrow>> ENTITY_HOMMING_ARROW = misc("homming_arrow", HommingArrow::new, 0.1F, 0.1F);
    public static final RegistryObject<EntityType<ExplosiveHommingArrow>> ENTITY_EXPLOSIVE_HOMMING_ARROW = misc("explosive_homming_arrow", ExplosiveHommingArrow::new, 0.1F, 0.1F);
    public static final RegistryObject<EntityType<CosmicRay>> ENTITY_COSMIC_RAY = misc("cosmic_ray", CosmicRay::new, 0.1F, 0.1F);
    public static final RegistryObject<EntityType<EerieCretin>> ENTITY_EERIE_CRETIN = mob("eerie_cretin", EerieCretin::new, 0.375F, 0.375F);
    public static final RegistryObject<EntityType<Leet>> ENTITY_LEET = mob("leet", Leet::new, 0.375F, 0.375F);
    public static final RegistryObject<EntityType<Test>> ENTITY_TEST = mob("test", Test::new, 0.375F, 0.375F);

    public static final RegistryObject<Item> ITEM_TOURNIQUET = item("tourniquet", Tourniquet::new);

    public static final RegistryObject<Item> ITEM_COSMO_SPAWN_EGG = egg("cosmo_spawn_egg", Rego.ENTITY_COSMO);
    public static final RegistryObject<Item> ITEM_SNIPE_SPAWN_EGG = egg("snipe_spawn_egg", Rego.ENTITY_SNIPE);
    public static final RegistryObject<Item> ITEM_FLARE_SPAWN_EGG = egg("flare_spawn_egg", Rego.ENTITY_FLARE);
    public static final RegistryObject<Item> ITEM_COSMIC_CREEPER_SPAWN_EGG = egg("cosmic_creeper_spawn_egg", Rego.ENTITY_COSMIC_CREEPER);
    public static final RegistryObject<Item> ITEM_FROLICKER_SPAWN_EGG = egg("frolicker_spawn_egg", Rego.ENTITY_FROLICKER);
    public static final RegistryObject<Item> ITEM_FLUTTERFLY_SPAWN_EGG = egg("flutterfly_spawn_egg", Rego.ENTITY_FLUTTERFLY);
    public static final RegistryObject<Item> ITEM_UTTERFLY_SPAWN_EGG = egg("utterfly_spawn_egg", Rego.ENTITY_UTTERFLY);
    public static final RegistryObject<Item> ITEM_ANTI_COSMO_SPAWN_EGG = egg("anti_cosmo_spawn_egg", Rego.ENTITY_ANTI_COSMO);
    public static final RegistryObject<Item> ITEM_EERIE_CRETIN_SPAWN_EGG = egg("eerie_cretin_spawn_egg", Rego.ENTITY_EERIE_CRETIN);
    public static final RegistryObject<Item> ITEM_LEET_SPAWN_EGG = egg("leet_spawn_egg", Rego.ENTITY_LEET);
    public static final RegistryObject<Item> ITEM_TEST_SPAWN_EGG = egg("test_spawn_egg", Rego.ENTITY_TEST);

    public static final RegistryObject<Syncope> EFFECT_SYNCOPE = effect("syncope", Syncope::new);

    private static final Predicate<RegistryObject<Item>> BLACKLISTED_ITEMS = item -> item.get() instanceof BlockItem || item.equals(Rego.ICON_MOB_TAB) || item.equals(Rego.ICON_BLOCK_TAB) || item.equals(Rego.ICON_ITEM_TAB);
    private static final Predicate<RegistryObject<Block>> BLACKLISTED_BLOCKS = block -> block.get() instanceof FlowerPotBlock;
    private static final Predicate<RegistryObject<Item>> WHITELISTED_EGGS = item -> item.get() instanceof ForgeSpawnEggItem;

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTab().equals(TAB_ITEMS.get())) {
            for (RegistryObject<Item> item : ITEMS.getEntries()) {
                if (!BLACKLISTED_ITEMS.test(item)) {
                    if (!WHITELISTED_EGGS.test(item)) {
                        safeAccept(event, item);
                    }
                }
            }
        }

        if (event.getTab().equals(TAB_BLOCKS.get())) {
            for (RegistryObject<Block> block : BLOCKS.getEntries()) {
                if (!BLACKLISTED_BLOCKS.test(block)) {
                    safeAccept(event, block);
                }
            }
        }

        if (event.getTab().equals(TAB_MOBS.get())) {
            for (RegistryObject<Item> item : ITEMS.getEntries()) {
                if (WHITELISTED_EGGS.test(item)) {
                    safeAccept(event, item);
                }
            }
        }
    }

    private static <T extends ItemLike> void safeAccept(BuildCreativeModeTabContentsEvent event, RegistryObject<T> item)
    {
        Map<Boolean, ResourceLocation> map = new AsynchronousHashMap<>();
        ItemStack stack = item.get().asItem().getDefaultInstance();
        boolean valid = stack.getCount() == 1;
        map.put(valid, item.getId());
        if (valid) {
            event.accept(item);
        } else {
            String itemName = map.get(false).toString();
            SnakerLib.LOGGER.warnf("ItemStack '%s' is empty or invalid", itemName);
        }
    }

    private static <T extends Recipe<?>> RegistryObject<RecipeSerializer<T>> serializer(String name, Supplier<RecipeSerializer<T>> serializer)
    {
        return SERIALIZERS.register(name, serializer);
    }

    private static <T extends MobEffect> RegistryObject<T> effect(String name, Supplier<T> type)
    {
        return EFFECTS.register(name, type);
    }

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> menu(String name, IContainerFactory<T> factory)
    {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    private static RegistryObject<CreativeModeTab> tab(String name, Supplier<ItemStack> icon)
    {
        return TABS.register(name.toLowerCase(), () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + name)).withTabsBefore(CreativeModeTabs.SPAWN_EGGS).icon(icon).build());
    }

    private static <T extends Animal> RegistryObject<EntityType<T>> creature(String name, EntityType.EntityFactory<T> entity, float width, float height)
    {
        return ENTITIES.register(name, () -> EntityType.Builder.of(entity, MobCategory.CREATURE)
                .sized(width, height)
                .build(name));
    }

    private static <T extends Mob> RegistryObject<EntityType<T>> mob(String name, EntityType.EntityFactory<T> entity, float width, float height)
    {
        return ENTITIES.register(name, () -> EntityType.Builder.of(entity, MobCategory.MONSTER)
                .sized(width, height)
                .build(name));
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> misc(String name, EntityType.EntityFactory<T> entity, float width, float height)
    {
        return ENTITIES.register(name, () -> EntityType.Builder.of(entity, MobCategory.MISC)
                .sized(width, height)
                .build(name));
    }

    private static RegistryObject<Item> item(String name)
    {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    private static RegistryObject<Item> item(String name, Supplier<Item> item)
    {
        return ITEMS.register(name, item);
    }

    private static RegistryObject<Block> blockWithoutItem(String name, Supplier<Block> block)
    {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> blockWithItem(String name, Supplier<T> block)
    {
        RegistryObject<T> obj = BLOCKS.register(name, block);
        blockItem(name, obj);
        return obj;
    }

    private static <T extends Block> void blockItem(String name, RegistryObject<T> block)
    {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static RegistryObject<Item> egg(String name, Supplier<? extends EntityType<? extends Mob>> mob)
    {
        Supplier<ForgeSpawnEggItem> egg = () -> new ForgeSpawnEggItem(mob, SnakerLib.randomHex(), SnakerLib.randomHex(), new Item.Properties());
        return ITEMS.register(name, egg);
    }

    private static RegistryObject<SoundEvent> sound(String name)
    {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourcePath(name)));
    }

    public static void initialize()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EntityDropHandler.initialize();
        TABS.register(bus);
        ITEMS.register(bus);
        BLOCKS.register(bus);
        MENUS.register(bus);
        PARTICLES.register(bus);
        BLOCK_ENTITIES.register(bus);
        SOUNDS.register(bus);
        ENTITIES.register(bus);
        RECIPES.register(bus);
        SERIALIZERS.register(bus);
        EFFECTS.register(bus);
        BIOMES.register(bus);
    }

    public static class Tags
    {
        public static final TagKey<Biome> COMATOSE_VEGETAL = TagKey.create(ForgeRegistries.Keys.BIOMES, new ResourcePath("comatose_vegetal"));
    }

    public static class Keys
    {
        public static final ResourceKey<Level> COMATOSE = ResourceKey.create(Registries.DIMENSION, new ResourcePath("comatose"));
        public static final ResourceKey<Biome> DELUSIVE = ResourceKey.create(Registries.BIOME, new ResourcePath("delusive"));
        public static final ResourceKey<Biome> ILLUSIVE = ResourceKey.create(Registries.BIOME, new ResourcePath("illusive"));
        public static final ResourceKey<Biome> IMMATERIAL = ResourceKey.create(Registries.BIOME, new ResourcePath("immaterial"));
        public static final ResourceKey<Biome> SPECTRAL = ResourceKey.create(Registries.BIOME, new ResourcePath("spectral"));
        public static final ResourceKey<Biome> SURREAL = ResourceKey.create(Registries.BIOME, new ResourcePath("surreal"));
    }
}//CH31ZJ
//MKS85