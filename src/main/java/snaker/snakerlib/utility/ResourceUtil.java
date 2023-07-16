package snaker.snakerlib.utility;

import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.StatType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

/**
 * Created by SnakerBone on 19/05/2023
 * <p>
 * Used for getting the ResourceLocation / namespace / path / registry name of most game objects
 **/
public class ResourceUtil
{
    public static ResourceLocation getResourceLocation(ItemStack key)
    {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(key.getItem()));
    }

    public static String getPath(ItemStack key)
    {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(key.getItem())).getPath();
    }

    public static String getNamespace(ItemStack key)
    {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(key.getItem())).getNamespace();
    }

    public static String getRegistryName(ItemStack key)
    {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(key.getItem())).toString();
    }

    public static ItemStack getItemStack(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(value)).getDefaultInstance();
    }

    // ========== Item ========== //

    public static ResourceLocation getResourceLocation(Item Key)
    {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(Key));
    }

    public static String getPath(Item key)
    {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(key)).getPath();
    }

    public static String getNamespace(Item key)
    {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(key)).getNamespace();
    }

    public static String getRegistryName(Item key)
    {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(key)).toString();
    }

    public static Item getItem(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(value));
    }

    // ========== Block ========== //

    public static ResourceLocation getResourceLocation(Block key)
    {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(key));
    }

    public static String getPath(Block key)
    {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(key)).getPath();
    }

    public static String getNamespace(Block key)
    {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(key)).getNamespace();
    }

    public static String getRegistryName(Block key)
    {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(key)).toString();
    }

    public static Block getBlock(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(value));
    }

    // ========== Fluid ========== //

    public static ResourceLocation getResourceLocation(Fluid key)
    {
        return Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(key));
    }

    public static String getPath(Fluid key)
    {
        return Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(key)).getPath();
    }

    public static String getNamespace(Fluid key)
    {
        return Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(key)).getNamespace();
    }

    public static String getRegistryName(Fluid key)
    {
        return Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(key)).toString();
    }

    public static Fluid getFluid(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(value));
    }

    // ========== MobEffect ========== //

    public static ResourceLocation getResourceLocation(MobEffect key)
    {
        return Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getKey(key));
    }

    public static String getPath(MobEffect key)
    {
        return Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getKey(key)).getPath();
    }

    public static String getNamespace(MobEffect key)
    {
        return Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getKey(key)).getNamespace();
    }

    public static String getRegistryName(MobEffect key)
    {
        return Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getKey(key)).toString();
    }

    public static MobEffect getMobEffect(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(value));
    }

    // ========== SoundEvent ========== //

    public static ResourceLocation getResourceLocation(SoundEvent key)
    {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getKey(key));
    }

    public static String getPath(SoundEvent key)
    {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getKey(key)).getPath();
    }

    public static String getNamespace(SoundEvent key)
    {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getKey(key)).getNamespace();
    }

    public static String getRegistryName(SoundEvent key)
    {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getKey(key)).toString();
    }

    public static SoundEvent getSoundEvent(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(value));
    }

    // ========== Potion ========== //

    public static ResourceLocation getResourceLocation(Potion key)
    {
        return Objects.requireNonNull(ForgeRegistries.POTIONS.getKey(key));
    }

    public static String getPath(Potion key)
    {
        return Objects.requireNonNull(ForgeRegistries.POTIONS.getKey(key)).getPath();
    }

    public static String getNamespace(Potion key)
    {
        return Objects.requireNonNull(ForgeRegistries.POTIONS.getKey(key)).getNamespace();
    }

    public static String getRegistryName(Potion key)
    {
        return Objects.requireNonNull(ForgeRegistries.POTIONS.getKey(key)).toString();
    }

    public static Potion getPotion(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.POTIONS.getValue(value));
    }

    // ========== Enchantment ========== //

    public static ResourceLocation getResourceLocation(Enchantment key)
    {
        return Objects.requireNonNull(ForgeRegistries.ENCHANTMENTS.getKey(key));
    }

    public static String getPath(Enchantment key)
    {
        return Objects.requireNonNull(ForgeRegistries.ENCHANTMENTS.getKey(key)).getPath();
    }

    public static String getNamespace(Enchantment key)
    {
        return Objects.requireNonNull(ForgeRegistries.ENCHANTMENTS.getKey(key)).getNamespace();
    }

    public static String getRegistryName(Enchantment key)
    {
        return Objects.requireNonNull(ForgeRegistries.ENCHANTMENTS.getKey(key)).toString();
    }

    public static Enchantment getEnchantment(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.ENCHANTMENTS.getValue(value));
    }

    // ========== PaintingVariant ========== //

    public static ResourceLocation getResourceLocation(PaintingVariant key)
    {
        return Objects.requireNonNull(ForgeRegistries.PAINTING_VARIANTS.getKey(key));
    }

    public static String getPath(PaintingVariant key)
    {
        return Objects.requireNonNull(ForgeRegistries.PAINTING_VARIANTS.getKey(key)).getPath();
    }

    public static String getNamespace(PaintingVariant key)
    {
        return Objects.requireNonNull(ForgeRegistries.PAINTING_VARIANTS.getKey(key)).getNamespace();
    }

    public static String getRegistryName(PaintingVariant key)
    {
        return Objects.requireNonNull(ForgeRegistries.PAINTING_VARIANTS.getKey(key)).toString();
    }

    public static PaintingVariant getPaintingVariant(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.PAINTING_VARIANTS.getValue(value));
    }

    // ========== Attribute ========== //

    public static ResourceLocation getResourceLocation(Attribute key)
    {
        return Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getKey(key));
    }

    public static String getPath(Attribute key)
    {
        return Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getKey(key)).getPath();
    }

    public static String getNamespace(Attribute key)
    {
        return Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(Attribute key)
    {
        return Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getKey(key)).toString();
    }

    public static Attribute getAttribute(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(value));
    }

    // ========== VillagerProfession ========== //

    public static ResourceLocation getResourceLocation(VillagerProfession key)
    {
        return Objects.requireNonNull(ForgeRegistries.VILLAGER_PROFESSIONS.getKey(key));
    }

    public static String getPath(VillagerProfession key)
    {
        return Objects.requireNonNull(ForgeRegistries.VILLAGER_PROFESSIONS.getKey(key)).getPath();
    }

    public static String getNamespace(VillagerProfession key)
    {
        return Objects.requireNonNull(ForgeRegistries.VILLAGER_PROFESSIONS.getKey(key)).getNamespace();
    }

    public static String getRegistryName(VillagerProfession key)
    {
        return Objects.requireNonNull(ForgeRegistries.VILLAGER_PROFESSIONS.getKey(key)).toString();
    }

    public static VillagerProfession getVillagerProfession(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.VILLAGER_PROFESSIONS.getValue(value));
    }

    // ========== Schedule ========== //

    public static ResourceLocation getResourceLocation(Schedule key)
    {
        return Objects.requireNonNull(ForgeRegistries.SCHEDULES.getKey(key));
    }

    public static String getPath(Schedule key)
    {
        return Objects.requireNonNull(ForgeRegistries.SCHEDULES.getKey(key)).getPath();
    }

    public static String getNamespace(Schedule key)
    {
        return Objects.requireNonNull(ForgeRegistries.SCHEDULES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(Schedule key)
    {
        return Objects.requireNonNull(ForgeRegistries.SCHEDULES.getKey(key)).toString();
    }

    public static Schedule getSchedule(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.SCHEDULES.getValue(value));
    }

    // ========== Activity ========== //

    public static ResourceLocation getResourceLocation(Activity key)
    {
        return Objects.requireNonNull(ForgeRegistries.ACTIVITIES.getKey(key));
    }

    public static String getPath(Activity key)
    {
        return Objects.requireNonNull(ForgeRegistries.ACTIVITIES.getKey(key)).getPath();
    }

    public static String getNamespace(Activity key)
    {
        return Objects.requireNonNull(ForgeRegistries.ACTIVITIES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(Activity key)
    {
        return Objects.requireNonNull(ForgeRegistries.ACTIVITIES.getKey(key)).toString();
    }

    public static Activity getActivity(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.ACTIVITIES.getValue(value));
    }

    // ========== ChunkStatus ========== //

    public static ResourceLocation getResourceLocation(ChunkStatus key)
    {
        return Objects.requireNonNull(ForgeRegistries.CHUNK_STATUS.getKey(key));
    }

    public static String getPath(ChunkStatus key)
    {
        return Objects.requireNonNull(ForgeRegistries.CHUNK_STATUS.getKey(key)).getPath();
    }

    public static String getNamespace(ChunkStatus key)
    {
        return Objects.requireNonNull(ForgeRegistries.CHUNK_STATUS.getKey(key)).getNamespace();
    }

    public static String getRegistryName(ChunkStatus key)
    {
        return Objects.requireNonNull(ForgeRegistries.CHUNK_STATUS.getKey(key)).toString();
    }

    public static ChunkStatus getChunkStatus(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.CHUNK_STATUS.getValue(value));
    }

    // ========== Biome ========== //

    public static ResourceLocation getResourceLocation(Biome key)
    {
        return Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(key));
    }

    public static String getPath(Biome key)
    {
        return Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(key)).getPath();
    }

    public static String getNamespace(Biome key)
    {
        return Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(Biome key)
    {
        return Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(key)).toString();
    }

    public static Biome getBiome(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.BIOMES.getValue(value));
    }

    // ========== PoiType ========== //

    public static ResourceLocation getResourceLocation(PoiType key)
    {
        return Objects.requireNonNull(ForgeRegistries.POI_TYPES.getKey(key));
    }

    public static String getPath(PoiType key)
    {
        return Objects.requireNonNull(ForgeRegistries.POI_TYPES.getKey(key)).getPath();
    }

    public static String getNamespace(PoiType key)
    {
        return Objects.requireNonNull(ForgeRegistries.POI_TYPES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(PoiType key)
    {
        return Objects.requireNonNull(ForgeRegistries.POI_TYPES.getKey(key)).toString();
    }

    public static PoiType getPoiType(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.POI_TYPES.getValue(value));
    }

    // ========== EntityType ========== //

    public static ResourceLocation getResourceLocation(EntityType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(key));
    }

    public static String getPath(EntityType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(key)).getPath();
    }

    public static String getNamespace(EntityType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(EntityType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(key)).toString();
    }

    public static EntityType<?> getEntityType(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getValue(value));
    }

    // ========== BlockEntityType ========== //

    public static ResourceLocation getResourceLocation(BlockEntityType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(key));
    }

    public static String getPath(BlockEntityType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(key)).getPath();
    }

    public static String getNamespace(BlockEntityType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(BlockEntityType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(key)).toString();
    }

    public static BlockEntityType<?> getBlockEntityType(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.BLOCK_ENTITY_TYPES.getValue(value));
    }

    // ========== ParticleType ========== //

    public static ResourceLocation getResourceLocation(ParticleType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.PARTICLE_TYPES.getKey(key));
    }

    public static String getPath(ParticleType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.PARTICLE_TYPES.getKey(key)).getPath();
    }

    public static String getNamespace(ParticleType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.PARTICLE_TYPES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(ParticleType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.PARTICLE_TYPES.getKey(key)).toString();
    }

    public static ParticleType<?> getParticleType(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.PARTICLE_TYPES.getValue(value));
    }

    // ========== MenuType ========== //

    public static ResourceLocation getResourceLocation(MenuType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.MENU_TYPES.getKey(key));
    }

    public static String getPath(MenuType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.MENU_TYPES.getKey(key)).getPath();
    }

    public static String getNamespace(MenuType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.MENU_TYPES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(MenuType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.MENU_TYPES.getKey(key)).toString();
    }

    public static MenuType<?> getMenuType(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.MENU_TYPES.getValue(value));
    }

    // ========== RecipeType ========== //

    public static ResourceLocation getResourceLocation(RecipeType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.RECIPE_TYPES.getKey(key));
    }

    public static String getPath(RecipeType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.RECIPE_TYPES.getKey(key)).getPath();
    }

    public static String getNamespace(RecipeType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.RECIPE_TYPES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(RecipeType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.RECIPE_TYPES.getKey(key)).toString();
    }

    public static RecipeType<?> getRecipeType(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.RECIPE_TYPES.getValue(value));
    }

    // ========== RecipeSerializer ========== //

    public static ResourceLocation getResourceLocation(RecipeSerializer<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.RECIPE_SERIALIZERS.getKey(key));
    }

    public static String getPath(RecipeSerializer<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.RECIPE_SERIALIZERS.getKey(key)).getPath();
    }

    public static String getNamespace(RecipeSerializer<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.RECIPE_SERIALIZERS.getKey(key)).getNamespace();
    }

    public static String getRegistryName(RecipeSerializer<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.RECIPE_SERIALIZERS.getKey(key)).toString();
    }

    public static RecipeSerializer<?> getRecipeSerializer(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.RECIPE_SERIALIZERS.getValue(value));
    }

    // ========== StatType ========== //

    public static ResourceLocation getResourceLocation(StatType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.STAT_TYPES.getKey(key));
    }

    public static String getPath(StatType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.STAT_TYPES.getKey(key)).getPath();
    }

    public static String getNamespace(StatType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.STAT_TYPES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(StatType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.STAT_TYPES.getKey(key)).toString();
    }

    public static StatType<?> getStatType(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.STAT_TYPES.getValue(value));
    }

    // ========== MemoryModuleType ========== //

    public static ResourceLocation getResourceLocation(MemoryModuleType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.MEMORY_MODULE_TYPES.getKey(key));
    }

    public static String getPath(MemoryModuleType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.MEMORY_MODULE_TYPES.getKey(key)).getPath();
    }

    public static String getNamespace(MemoryModuleType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.MEMORY_MODULE_TYPES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(MemoryModuleType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.MEMORY_MODULE_TYPES.getKey(key)).toString();
    }

    public static MemoryModuleType<?> getMemoryModuleType(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.MEMORY_MODULE_TYPES.getValue(value));
    }

    // ========== SensorType ========== //

    public static ResourceLocation getResourceLocation(SensorType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.SENSOR_TYPES.getKey(key));
    }

    public static String getPath(SensorType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.SENSOR_TYPES.getKey(key)).getPath();
    }

    public static String getNamespace(SensorType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.SENSOR_TYPES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(SensorType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.SENSOR_TYPES.getKey(key)).toString();
    }

    public static SensorType<?> getSensorType(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.SENSOR_TYPES.getValue(value));
    }

    // ========== WorldCarver ========== //

    public static ResourceLocation getResourceLocation(WorldCarver<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.WORLD_CARVERS.getKey(key));
    }

    public static String getPath(WorldCarver<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.WORLD_CARVERS.getKey(key)).getPath();
    }

    public static String getNamespace(WorldCarver<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.WORLD_CARVERS.getKey(key)).getNamespace();
    }

    public static String getRegistryName(WorldCarver<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.WORLD_CARVERS.getKey(key)).toString();
    }

    public static WorldCarver<?> getWorldCarver(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.WORLD_CARVERS.getValue(value));
    }

    // ========== Feature ========== //

    public static ResourceLocation getResourceLocation(Feature<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.FEATURES.getKey(key));
    }

    public static String getPath(Feature<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.FEATURES.getKey(key)).getPath();
    }

    public static String getNamespace(Feature<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.FEATURES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(Feature<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.FEATURES.getKey(key)).toString();
    }

    public static Feature<?> getFeature(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.FEATURES.getValue(value));
    }

    // ========== BlockStateProviderType ========== //

    public static ResourceLocation getResourceLocation(BlockStateProviderType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES.getKey(key));
    }

    public static String getPath(BlockStateProviderType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES.getKey(key)).getPath();
    }

    public static String getNamespace(BlockStateProviderType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(BlockStateProviderType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES.getKey(key)).toString();
    }

    public static BlockStateProviderType<?> getBlockStateProviderType(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES.getValue(value));
    }

    // ========== FoliagePlacerType ========== //

    public static ResourceLocation getResourceLocation(FoliagePlacerType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.FOLIAGE_PLACER_TYPES.getKey(key));
    }

    public static String getPath(FoliagePlacerType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.FOLIAGE_PLACER_TYPES.getKey(key)).getPath();
    }

    public static String getNamespace(FoliagePlacerType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.FOLIAGE_PLACER_TYPES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(FoliagePlacerType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.FOLIAGE_PLACER_TYPES.getKey(key)).toString();
    }

    public static FoliagePlacerType<?> getFoliagePlacerType(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.FOLIAGE_PLACER_TYPES.getValue(value));
    }

    // ========== TreeDecoratorType ========== //

    public static ResourceLocation getResourceLocation(TreeDecoratorType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.TREE_DECORATOR_TYPES.getKey(key));
    }

    public static String getPath(TreeDecoratorType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.TREE_DECORATOR_TYPES.getKey(key)).getPath();
    }

    public static String getNamespace(TreeDecoratorType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.TREE_DECORATOR_TYPES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(TreeDecoratorType<?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.TREE_DECORATOR_TYPES.getKey(key)).toString();
    }

    public static TreeDecoratorType<?> getTreeDecoratorType(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.TREE_DECORATOR_TYPES.getValue(value));
    }

    // ========== ArgumentTypeInfo ========== //

    public static ResourceLocation getResourceLocation(ArgumentTypeInfo<?, ?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.COMMAND_ARGUMENT_TYPES.getKey(key));
    }

    public static String getPath(ArgumentTypeInfo<?, ?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.COMMAND_ARGUMENT_TYPES.getKey(key)).getPath();
    }

    public static String getNamespace(ArgumentTypeInfo<?, ?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.COMMAND_ARGUMENT_TYPES.getKey(key)).getNamespace();
    }

    public static String getRegistryName(ArgumentTypeInfo<?, ?> key)
    {
        return Objects.requireNonNull(ForgeRegistries.COMMAND_ARGUMENT_TYPES.getKey(key)).toString();
    }

    public static ArgumentTypeInfo<?, ?> getArgumentTypeInfo(ResourceLocation value)
    {
        return Objects.requireNonNull(ForgeRegistries.COMMAND_ARGUMENT_TYPES.getValue(value));
    }
}