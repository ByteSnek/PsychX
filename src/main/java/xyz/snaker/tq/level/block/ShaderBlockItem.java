package xyz.snaker.tq.level.block;

import java.util.function.Consumer;

import xyz.snaker.snakerlib.data.SnakerConstants;
import xyz.snaker.tq.client.render.block.ShaderBlockItemRenderer;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.RegistryObject;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 28/04/2023
 **/
public class ShaderBlockItem extends BlockItem
{
    public ShaderBlockItem(RegistryObject<Block> block)
    {
        super(block.get(), SnakerConstants.ItemProperties.EMPTY);
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer)
    {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions()
        {
            public BlockEntityWithoutLevelRenderer getRenderer()
            {
                return new ShaderBlockItemRenderer(ShaderBlockItemRenderer.TYPE.get(ShaderBlockItem.this));
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                return getRenderer();
            }
        });
    }
}