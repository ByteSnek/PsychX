package xyz.snaker.tq.level.item;

import java.util.function.Consumer;

import xyz.snaker.snakerlib.level.item.SnakerBaseItem;
import xyz.snaker.tq.client.render.item.CosmoSpineRenderer;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

/**
 * Created by SnakerBone on 11/03/2023
 **/
public class CosmoSpine extends SnakerBaseItem
{
    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer)
    {
        super.initializeClient(consumer);

        consumer.accept(new IClientItemExtensions()
        {
            public BlockEntityWithoutLevelRenderer getRenderer()
            {
                return new CosmoSpineRenderer(CosmoSpineRenderer.TYPE.get(CosmoSpine.this));
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                return getRenderer();
            }
        });
    }
}
