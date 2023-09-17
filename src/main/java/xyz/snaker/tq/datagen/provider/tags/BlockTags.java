package xyz.snaker.tq.datagen.provider.tags;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

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
        addMineableWithAxe(Blocks.GEOMETRIC_LOG, Blocks.FOGGY_LOG, Blocks.GEOMETRIC_PLANKS, Blocks.FOGGY_PLANKS);
        addPlanks(Blocks.GEOMETRIC_PLANKS, Blocks.FOGGY_PLANKS);
        addLogs(Blocks.GEOMETRIC_LOG, Blocks.FOGGY_LOG);
        addGroundRich(Blocks.COMASTONE);
        for (RegistryObject<Block> block : Blocks.REGISTRAR.getEntries()) {
            if (BLOCKS_NEED_TOOL.test(block)) {
                addRequiresTool(BlockTagsProviderTools.ToolTier.STONE, block);
                addMineableWithPickaxe(block);
            }
        }
    }

    @SafeVarargs
    final void addRequiresTool(ToolTier tier, RegistryObject<Block>... blocks)
    {
        toolRequired(tier, List.of(Arrays.stream(blocks).map(RegistryObject::get).toArray(Block[]::new)));
    }

    @SafeVarargs
    final void addGroundRich(RegistryObject<Block>... blocks)
    {
        groundRich(List.of(Arrays.stream(blocks).map(RegistryObject::get).toArray(Block[]::new)));
    }

    @SafeVarargs
    final void addLogs(RegistryObject<Block>... blocks)
    {
        logs(List.of(Arrays.stream(blocks).map(RegistryObject::get).toArray(Block[]::new)));
    }

    @SafeVarargs
    final void addPlanks(RegistryObject<Block>... blocks)
    {
        planks(List.of(Arrays.stream(blocks).map(RegistryObject::get).toArray(Block[]::new)));
    }

    @SafeVarargs
    final void addMineableWithAxe(RegistryObject<Block>... blocks)
    {
        mineableWithAxe(List.of(Arrays.stream(blocks).map(RegistryObject::get).toArray(Block[]::new)));
    }

    @SafeVarargs
    final void addMineableWithPickaxe(RegistryObject<Block>... blocks)
    {
        mineableWithPickaxe(List.of(Arrays.stream(blocks).map(RegistryObject::get).toArray(Block[]::new)));
    }

    @Override
    public BlockTags getInstance()
    {
        return this;
    }
}
