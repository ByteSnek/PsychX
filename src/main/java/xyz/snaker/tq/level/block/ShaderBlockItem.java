package xyz.snaker.tq.level.block;

import java.util.function.Consumer;

import xyz.snaker.snakerlib.data.SnakerConstants;
import xyz.snaker.snakerlib.utility.ResourceStuff;
import xyz.snaker.tq.client.RenderTypes;
import xyz.snaker.tq.client.render.block.ShaderBlockItemRenderer;
import xyz.snaker.tq.rego.Rego;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

/**
 * Created by SnakerBone on 28/04/2023
 **/
public class ShaderBlockItem extends BlockItem
{
    public ShaderBlockItem(Block block)
    {
        super(block, SnakerConstants.ItemProperties.EMPTY);
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer)
    {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions()
        {
            public boolean is(Item item)
            {
                return ResourceStuff.getPath(item).equals(ResourceStuff.getPath(ShaderBlockItem.this));
            }

            public BlockEntityWithoutLevelRenderer getRenderer()
            {
                if (is(Rego.ITEM_SWIRL_BLOCK.get())) {
                    return new ShaderBlockItemRenderer(RenderTypes.OBJ_SWIRL);
                } else if (is(Rego.ITEM_SNOWFLAKE_BLOCK.get())) {
                    return new ShaderBlockItemRenderer(RenderTypes.OBJ_SNOWFLAKE);
                } else if (is(Rego.ITEM_WATERCOLOUR_BLOCK.get())) {
                    return new ShaderBlockItemRenderer(RenderTypes.OBJ_WATERCOLOUR);
                } else if (is(Rego.ITEM_MULTICOLOUR_BLOCK.get())) {
                    return new ShaderBlockItemRenderer(RenderTypes.OBJ_MULTICOLOUR);
                } else if (is(Rego.ITEM_FLARE_BLOCK.get())) {
                    return new ShaderBlockItemRenderer(RenderTypes.OBJ_FIRE);
                } else if (is(Rego.ITEM_STARRY_BLOCK.get())) {
                    return new ShaderBlockItemRenderer(RenderTypes.OBJ_BLACK_STARS);
                }
                return null;
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                return getRenderer();
            }
        });
    }
}