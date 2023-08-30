package xyz.snaker.tq.datagen.provider.tags;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nullable;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.block.ShaderBlock;
import xyz.snaker.tq.rego.Blocks;
import xyz.snaker.tq.utility.tools.BlockTagsProviderTools;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class BlockTags extends BlockTagsProvider implements BlockTagsProviderTools<BlockTags>
{
    public static final Predicate<RegistryObject<Block>> BLOCKS_NEED_TOOL = block -> block.get() instanceof ShaderBlock<?> || block.get() == Blocks.COMASTONE.get();

    public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper)
    {
        super(output, provider, Tourniqueted.MODID, helper);
    }

    @Override
    public void addTags(@NotNull HolderLookup.Provider provider)
    {
        mineableWithAxe(List.of(Blocks.GEOMETRIC_LOG.get(), Blocks.GEOMETRIC_PLANKS.get()));
        planks(List.of(Blocks.GEOMETRIC_PLANKS.get()));
        logs(List.of(Blocks.GEOMETRIC_LOG.get()));
        groundRich(List.of(Blocks.COMASTONE.get()));
        for (RegistryObject<Block> block : Blocks.REGISTRAR.getEntries()) {
            if (BLOCKS_NEED_TOOL.test(block)) {
                toolRequired(BlockTagsProviderTools.ToolTier.STONE, List.of(block.get()));
                mineableWithPickaxe(List.of(block.get()));
            }
        }
    }

    @Override
    public BlockTags getInstance()
    {
        return this;
    }
}
