package bytesnek.tq.datagen.provider.tags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import org.jetbrains.annotations.NotNull;

import bytesnek.snakerlib.resources.ResourceLocations;
import bytesnek.tq.Tourniqueted;
import bytesnek.tq.level.block.ShaderBlock;
import bytesnek.tq.rego.Blocks;
import bytesnek.tq.utility.RegistryMapper;

import static net.minecraft.tags.BlockTags.*;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class BlockTags extends BlockTagsProvider implements RegistryMapper
{
    public static final Predicate<Block> NEEDS_PICKAXE = block -> block instanceof ShaderBlock<?> || block == Blocks.COMASTONE.get();
    public static final Predicate<Block> NEEDS_AXE = block -> ResourceLocations.BLOCK.getPath(block).contains("log") || ResourceLocations.BLOCK.getPath(block).contains("planks") || ResourceLocations.BLOCK.getPath(block).contains("wood") || ResourceLocations.BLOCK.getPath(block).contains("stem");

    public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper)
    {
        super(output, provider, Tourniqueted.MODID, helper);
    }

    @Override
    public void addTags(@NotNull HolderLookup.Provider provider)
    {
        tag(PLANKS).add(Blocks.ILLUSIVE_PLANKS.get(), Blocks.DELUSIVE_PLANKS.get());
        tag(LOGS).add(Blocks.ILLUSIVE_LOG.get(), Blocks.DELUSIVE_LOG.get());
        tag(LEAVES).add(Blocks.DELUSIVE_LEAVES.get(), Blocks.ILLUSIVE_LEAVES.get());
        tag(Blocks.Tags.GROUNDRICH).add(Blocks.COMASTONE.get());

        map(Blocks.REGISTER, RegistryMapper.NEGATE_LIQUID_BLOCK, Block[]::new).forEach(block ->
        {
            if (NEEDS_AXE.test(block)) {
                tag(MINEABLE_WITH_AXE).add(block);
                return;
            }

            if (NEEDS_PICKAXE.test(block)) {
                tag(NEEDS_STONE_TOOL).add(block);
                tag(MINEABLE_WITH_PICKAXE).add(block);
            }
        });
    }
}
