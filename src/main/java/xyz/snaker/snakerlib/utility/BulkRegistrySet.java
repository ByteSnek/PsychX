package xyz.snaker.snakerlib.utility;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.internal.MultiMap;

import java.util.List;

/**
 * Created by SnakerBone on 4/08/2023
 **/
public class BulkRegistrySet extends RegistrySetBuilder
{
    private final RegistrySetBuilder builder;
    private final MultiMap<ResourceKey<? extends Registry<?>>, RegistrySetBuilder.RegistryBootstrap<?>> map;
    private final boolean shouldDumpEntries;

    public BulkRegistrySet(RegistrySetBuilder builder, boolean shouldDumpEntries)
    {
        this.builder = builder;
        this.map = new MultiMap<>();
        this.shouldDumpEntries = shouldDumpEntries;
    }

    public static BulkRegistrySet create(boolean shouldDumpEntries)
    {
        boolean dumpEntries = Thread.currentThread().isAlive() && Thread.currentThread().getName().equals("main") && Thread.currentThread().getId() == 1 && shouldDumpEntries;
        return new BulkRegistrySet(new RegistrySetBuilder(), dumpEntries);
    }

    @SafeVarargs
    public final <T> BulkRegistrySet addAll(ResourceKey<? extends Registry<T>> key, RegistrySetBuilder.RegistryBootstrap<T>... bootstraps)
    {
        for (var bootstrap : bootstraps) {
            builder.add(key, bootstrap);
        }
        map.put(key, List.of(bootstraps));
        return this;
    }

    @SafeVarargs
    public final BulkRegistrySet addAllPlacements(RegistrySetBuilder.RegistryBootstrap<PlacedFeature>... bootstraps)
    {
        var key = Registries.PLACED_FEATURE;
        for (var bootstrap : bootstraps) {
            builder.add(key, bootstrap);
            map.map(key, bootstrap);
        }
        return this;
    }

    @SafeVarargs
    public final BulkRegistrySet addAllConfigs(RegistrySetBuilder.RegistryBootstrap<ConfiguredFeature<?, ?>>... bootstraps)
    {
        var key = Registries.CONFIGURED_FEATURE;
        for (var bootstrap : bootstraps) {
            builder.add(key, bootstrap);
            map.map(key, bootstrap);
        }
        return this;
    }

    @SafeVarargs
    public final BulkRegistrySet addAllModifiers(RegistrySetBuilder.RegistryBootstrap<BiomeModifier>... bootstraps)
    {
        var key = ForgeRegistries.Keys.BIOME_MODIFIERS;
        for (var bootstrap : bootstraps) {
            builder.add(key, bootstrap);
            map.map(key, bootstrap);
        }
        return this;
    }

    @Override
    public <T> @NotNull RegistrySetBuilder add(@NotNull ResourceKey<? extends Registry<T>> key, @NotNull RegistryBootstrap<T> builder)
    {
        return this.builder.add(key, builder);
    }

    @Override
    public <T> @NotNull RegistrySetBuilder add(@NotNull ResourceKey<? extends Registry<T>> key, @NotNull Lifecycle lifecycle, @NotNull RegistryBootstrap<T> bootstrap)
    {
        return builder.add(key, lifecycle, bootstrap);
    }

    @Override
    public @NotNull List<? extends ResourceKey<? extends Registry<?>>> getEntryKeys()
    {
        return builder.getEntryKeys();
    }

    @Override
    public HolderLookup.@NotNull Provider build(@NotNull RegistryAccess access)
    {
        return builder.build(access);
    }

    @Override
    public HolderLookup.@NotNull Provider buildPatch(@NotNull RegistryAccess access, HolderLookup.@NotNull Provider provider)
    {
        return builder.buildPatch(access, provider);
    }

    public List<RegistryBootstrap<?>> getEntriesFor(ResourceKey<? extends Registry<?>> key)
    {
        if (map.isEmpty()) {
            ResourceLocation keyLocation = key.location();
            SnakerLib.LOGGER.warnf("No entries found for key '%s'", keyLocation);
            return List.of();
        }
        return map.get(key);
    }

    public void dumpAllEntries()
    {
        for (var keys : map.keySet()) {
            for (var values : map.values()) {
                for (var bootstrap : values) {
                    ResourceLocation keysLocation = keys.location();
                    String bootstrapName = bootstrap.toString();
                    SnakerLib.LOGGER.infof("%s -> %s", keysLocation, bootstrapName);
                }
            }
        }
    }

    public RegistrySetBuilder builder()
    {
        if (shouldDumpEntries) {
            dumpAllEntries();
        }
        return builder;
    }
}
