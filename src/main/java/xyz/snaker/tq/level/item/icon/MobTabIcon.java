package xyz.snaker.tq.level.item.icon;

import java.util.function.Consumer;

import xyz.snaker.snakerlib.level.item.SnakerBaseItem;
import xyz.snaker.tq.client.render.icon.MobTabIconRenderer;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

/**
 * Created by SnakerBone on 27/05/2023
 **/
public class MobTabIcon extends SnakerBaseItem
{
    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer)
    {
        consumer.accept(new IClientItemExtensions()
        {
            public MobTabIconRenderer getRenderer()
            {
                return new MobTabIconRenderer();
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                return getRenderer();
            }
        });
    }
}
