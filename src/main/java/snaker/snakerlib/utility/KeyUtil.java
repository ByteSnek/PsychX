package snaker.snakerlib.utility;

import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import snaker.snakerlib.SnakerLib;

/**
 * Created by SnakerBone on 11/07/2023
 **/
public class KeyUtil
{
    public static ResourceKey<ConfiguredFeature<?, ?>> registerConfiguredFeatureKey(String name)
    {
        return register(Registries.CONFIGURED_FEATURE, name);
    }

    public static ResourceKey<PlacedFeature> registerPlacedFeatureKey(String name)
    {
        return register(Registries.PLACED_FEATURE, name + "_placed");
    }

    public static ResourceKey<TrimMaterial> registerTrimMaterialKey(String name)
    {
        return register(Registries.TRIM_MATERIAL, name);
    }

    public static ResourceKey<PoiType> registerPOITypeKey(String name)
    {
        return register(Registries.POINT_OF_INTEREST_TYPE, name);
    }

    public static ResourceKey<DamageType> registerDamageType(String name)
    {
        return register(Registries.DAMAGE_TYPE, name);
    }

    public static ResourceKey<StructureProcessorList> registerStructureProcessorKey(String name)
    {
        return register(Registries.PROCESSOR_LIST, name);
    }

    public static ResourceKey<Biome> registerBiome(String name)
    {
        return register(Registries.BIOME, name);
    }

    public static <T> ResourceKey<T> register(ResourceKey<Registry<T>> registry, String name)
    {
        return ResourceKey.create(registry, location(name));
    }

    public static ResourceLocation container(String name)
    {
        return texture("gui/container/" + name);
    }

    public static ResourceLocation entity(String path)
    {
        return location("entity/" + path);
    }

    public static ResourceLocation entityTexture(String path)
    {
        return texture("entity/" + path);
    }

    public static ResourceLocation emptySlot(String name)
    {
        return location("id/empty_slot_" + name);
    }

    public static ResourceLocation texture(String path)
    {
        return texture(SnakerLib.MODID, path);
    }

    public static ResourceLocation texture(String modId, String path)
    {
        return parseLocation(modId + ":" + "textures/" + path + ".png");
    }

    public static ResourceLocation location(String name)
    {
        return parseLocation(SnakerLib.MODID + ":" + name);
    }

    public static ResourceLocation parseLocation(String name)
    {
        return new ResourceLocation(name);
    }

    public static ResourceLocation fromJson(JsonObject json, String key)
    {
        return new ResourceLocation(GsonHelper.getAsString(json, key));
    }
}
