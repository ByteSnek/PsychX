package bytesnek.tq.level.item;

import java.util.function.Consumer;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import org.jetbrains.annotations.NotNull;

import bytesnek.snakerlib.utility.item.ItemProperties;
import bytesnek.tq.client.renderer.item.CosmoSpineRenderer;

/**
 * Created by SnakerBone on 11/03/2023
 **/
public class CosmoSpine extends Item
{
    public CosmoSpine(Properties properties)
    {
        super(properties);
    }

    public CosmoSpine()
    {
        super(ItemProperties.EMPTY);
    }

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
