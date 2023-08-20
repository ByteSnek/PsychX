package xyz.snaker.snakerlib.data.gen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;

import com.mojang.datafixers.util.Pair;

/**
 * Created by SnakerBone on 20/08/2023
 **/
public class SimpleLootTableProvider
{
    public final PackOutput output;
    public final Set<ResourceLocation> tables;
    public final List<LootTableProvider.SubProviderEntry> entries;
    public final Pair<Supplier<LootTableSubProvider>, LootContextParamSet> pair;

    public SimpleLootTableProvider(PackOutput output, Pair<Supplier<LootTableSubProvider>, LootContextParamSet> pair)
    {
        this.output = output;
        this.tables = new HashSet<>();
        this.entries = new ArrayList<>();
        this.pair = pair;
        this.processEntries();
    }

    public void processEntries()
    {
        entries.add(new LootTableProvider.SubProviderEntry(pair.getFirst(), pair.getSecond()));
    }

    public LootTableProvider provider()
    {
        return new LootTableProvider(output, tables, entries);
    }
}
