package xyz.snaker.tq.level.item.icon;

import java.util.function.Consumer;

import xyz.snaker.snakerlib.level.item.SnakerBaseItem;
import xyz.snaker.tq.client.render.icon.ItemTabIconRenderer;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

/**
 * Created by SnakerBone on 12/06/2023
 **/
public class ItemTabIcon extends SnakerBaseItem
{
    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer)
    {
        consumer.accept(new IClientItemExtensions()
        {
            public ItemTabIconRenderer getRenderer()
            {
                return new ItemTabIconRenderer();
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                return getRenderer();
            }
        });
    }
}
