package xyz.snaker.tq.datagen;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.datagen.provider.*;
import xyz.snaker.tq.datagen.provider.loot.BlockLootTables;
import xyz.snaker.tq.datagen.provider.loot.EntityLootTables;
import xyz.snaker.tq.datagen.provider.tags.*;
import xyz.snaker.tq.rego.LootTables;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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

        List<LootTableProvider.SubProviderEntry> entries = List.of(new LootTableProvider.SubProviderEntry(BlockLootTables::new, LootContextParamSets.BLOCK), new LootTableProvider.SubProviderEntry(EntityLootTables::new, LootContextParamSets.ENTITY));
        Set<ResourceLocation> tables = LootTables.all();
        BlockTags blockTags = new BlockTags(output, provider, helper);

        generator.addProvider(true, blockTags);
        generator.addProvider(true, new Languages(output));
        generator.addProvider(true, new BlockStates(output, helper));
        generator.addProvider(true, new ItemModels(output, helper));
        generator.addProvider(true, new DatapackEntries(output, provider));
        generator.addProvider(true, new Recipes(output));
        generator.addProvider(true, new LootModifiers(output));
        generator.addProvider(true, new FluidTags(output, provider, helper));
        generator.addProvider(true, new ItemTags(output, provider, blockTags.contentsGetter(), helper));
        generator.addProvider(true, new LootTableProvider(output, tables, entries));
        generator.addProvider(true, new FlatLevelPresetTags(output, provider, helper));
        generator.addProvider(true, new BiomeTags(output, provider, helper));
    }
}
