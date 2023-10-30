package xyz.snaker.tq.level.display.tab;

import java.util.function.Consumer;

import xyz.snaker.snakerlib.client.Icon;
import xyz.snaker.snakerlib.data.DefaultItemProperties;
import xyz.snaker.tq.client.render.icon.ItemTabDisplayRenderer;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 12/06/2023
 **/
public class ItemTabDisplay extends Item implements Icon
{
    public ItemTabDisplay(Properties properties)
    {
        super(properties);
    }

    public ItemTabDisplay()
    {
        super(DefaultItemProperties.EMPTY);
    }


    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer)
    {
        consumer.accept(new IClientItemExtensions()
        {
            public ItemTabDisplayRenderer getRenderer()
            {
                return new ItemTabDisplayRenderer();
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                return getRenderer();
            }
        });
    }
}
