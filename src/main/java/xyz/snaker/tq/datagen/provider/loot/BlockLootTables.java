package xyz.snaker.tq.datagen.provider.loot;

import java.util.Set;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import org.jetbrains.annotations.NotNull;

import xyz.snaker.tq.rego.Blocks;
import xyz.snaker.tq.utility.RegistryMapper;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class BlockLootTables extends BlockLootSubProvider implements RegistryMapper
{
    public BlockLootTables()
    {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate()
    {
        map(Blocks.REGISTER, RegistryMapper.NEGATE_LIQUID_BLOCK, Block[]::new).forEach(this::dropSelf);
    }

    @Override
    public @NotNull Iterable<Block> getKnownBlocks()
    {
        return map(Blocks.REGISTER, Block[]::new)::iterator;
    }
}
