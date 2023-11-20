package bytesnek.tq.level.display.tab;

import java.util.function.Consumer;

import xyz.snaker.snakerlib.utility.item.ItemProperties;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import org.jetbrains.annotations.NotNull;

import bytesnek.tq.client.renderer.icon.ItemTabDisplayRenderer;
import bytesnek.tq.utility.NoTexture;

/**
 * Created by SnakerBone on 12/06/2023
 **/
@NoTexture
public class ItemTabDisplay extends Item
{
    public ItemTabDisplay(Properties properties)
    {
        super(properties);
    }

    public ItemTabDisplay()
    {
        super(ItemProperties.EMPTY);
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
