package bytesnek.tq.datagen.provider.loot;

import java.util.Set;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import org.jetbrains.annotations.NotNull;

import bytesnek.tq.rego.Blocks;
import bytesnek.tq.utility.RegistryMapper;

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
        map(Blocks.REGISTER, Block[]::new).forEach(block ->
        {
            if (block == Blocks.COMASOTE.get()) {
                return;
            }

            dropSelf(block);
        });
    }

    @Override
    public @NotNull Iterable<Block> getKnownBlocks()
    {
        return Blocks.REGISTER.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
