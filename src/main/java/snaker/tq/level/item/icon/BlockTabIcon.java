package snaker.tq.level.item.icon;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.level.item.SnakerBaseItem;
import snaker.tq.client.render.icon.BlockTabIconRenderer;

import java.util.function.Consumer;

/**
 * Created by SnakerBone on 12/06/2023
 **/
public class BlockTabIcon extends SnakerBaseItem
{
    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer)
    {
        consumer.accept(new IClientItemExtensions()
        {
            public BlockTabIconRenderer getRenderer()
            {
                return new BlockTabIconRenderer();
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                return getRenderer();
            }
        });
    }
}
