package xyz.snaker.tq.utility.tools;

import java.util.List;

import xyz.snaker.tq.rego.Blocks;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;

/**
 * Created by SnakerBone on 21/08/2023
 **/
public interface BlockTagsProviderTools<T extends BlockTagsProvider>
{
    T getInstance();

    default void custom(TagKey<Block> key, List<Block> values)
    {
        for (var value : values) {
            getInstance().tag(key).add(value);
        }
    }

    default void planks(List<Block> blocks)
    {
        custom(BlockTags.PLANKS, blocks);
    }

    default void logs(List<Block> blocks)
    {
        custom(BlockTags.LOGS, blocks);
    }

    default void groundRich(List<Block> blocks)
    {
        custom(Blocks.Tags.GROUNDRICH, blocks);
    }

    default void mineableWithAxe(List<Block> blocks)
    {
        custom(BlockTags.MINEABLE_WITH_AXE, blocks);
    }

    default void mineableWithPickaxe(List<Block> blocks)
    {
        custom(BlockTags.MINEABLE_WITH_PICKAXE, blocks);
    }

    default void toolRequired(ToolTier tier, List<Block> blocks)
    {
        TagKey<Block> key;
        switch (tier) {
            case WOOD -> key = Tags.Blocks.NEEDS_WOOD_TOOL;
            case STONE -> key = BlockTags.NEEDS_STONE_TOOL;
            case IRON -> key = BlockTags.NEEDS_IRON_TOOL;
            case GOLD -> key = Tags.Blocks.NEEDS_GOLD_TOOL;
            case DIAMOND -> key = BlockTags.NEEDS_DIAMOND_TOOL;
            case NETHERITE -> key = Tags.Blocks.NEEDS_NETHERITE_TOOL;
            default -> throw new RuntimeException(String.format("'%s' is not a recognized tier", tier));
        }
        custom(key, blocks);
    }

    enum ToolTier
    {
        WOOD(),
        STONE(),
        IRON(),
        GOLD(),
        DIAMOND(),
        NETHERITE()
    }
}
