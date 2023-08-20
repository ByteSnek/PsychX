package xyz.snaker.tq.datagen;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import xyz.snaker.snakerlib.data.gen.SimpleLootTableProvider;
import xyz.snaker.tq.Tourniqueted;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.mojang.datafixers.util.Pair;

/**
 * Created by SnakerBone on 21/03/2023
 **/
@Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        PackOutput output = generator.getPackOutput();

        Pair<Supplier<LootTableSubProvider>, LootContextParamSet> blocks = Pair.of(DataProviders.BlockLootTables::new, LootContextParamSets.BLOCK);

        generator.addProvider(event.includeClient(), new DataProviders.Languages(output));
        generator.addProvider(event.includeServer(), new DataProviders.BlockStates(output, helper));
        generator.addProvider(event.includeServer(), new DataProviders.ItemModels(output, helper));
        generator.addProvider(event.includeServer(), new DataProviders.DatapackEntries(output, provider));
        generator.addProvider(event.includeServer(), new DataProviders.BiomeTags(output, provider, helper));
        generator.addProvider(event.includeServer(), new DataProviders.BlockTags(output, provider, helper));
        generator.addProvider(event.includeServer(), new DataProviders.Recipes(output));
        generator.addProvider(event.includeServer(), new SimpleLootTableProvider(output, blocks).provider());
    }
}
