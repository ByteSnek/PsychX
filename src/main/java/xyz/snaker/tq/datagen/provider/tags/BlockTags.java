package xyz.snaker.tq.datagen.provider.tags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import xyz.snaker.snakerlib.resources.ResourceLocations;
import xyz.snaker.snakerlib.utility.Streams;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.block.ShaderBlock;
import xyz.snaker.tq.rego.Blocks;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import org.jetbrains.annotations.NotNull;

import static net.minecraft.tags.BlockTags.*;
import static xyz.snaker.tq.rego.Blocks.Tags.GROUNDRICH;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class BlockTags extends BlockTagsProvider
{
    public static final Predicate<Block> NEEDS_PICKAXE = block -> block instanceof ShaderBlock<?> || block == Blocks.COMASTONE.get();
    public static final Predicate<Block> NEEDS_AXE = block -> ResourceLocations.getPath(block).contains("log") || ResourceLocations.getPath(block).contains("planks") || ResourceLocations.getPath(block).contains("wood") || ResourceLocations.getPath(block).contains("stem");

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
        tag(GROUNDRICH).add(Blocks.COMASOTE.get());

        Streams.mapDeferredRegistries(Blocks.REGISTER, Block[]::new).forEach(block ->
        {
            if (block instanceof LiquidBlock) {
                return;
            }

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
