package xyz.snaker.tq.datagen.provider.loot;

import java.util.Set;

import xyz.snaker.tq.rego.Blocks;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class BlockLootTables extends BlockLootSubProvider
{
    public BlockLootTables()
    {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate()
    {
        for (RegistryObject<Block> block : Blocks.REGISTRAR.getEntries()) {
            dropSelf(block.get());
        }
    }

    @Override
    public @NotNull Iterable<Block> getKnownBlocks()
    {
        return Blocks.REGISTRAR.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
