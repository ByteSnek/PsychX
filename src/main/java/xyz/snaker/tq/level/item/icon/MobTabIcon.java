package xyz.snaker.tq.level.item.icon;

import java.util.function.Consumer;

import xyz.snaker.snakerlib.data.DefaultItemProperties;
import xyz.snaker.tq.client.render.icon.MobTabIconRenderer;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 27/05/2023
 **/
public class MobTabIcon extends Item
{
    public MobTabIcon(Properties properties)
    {
        super(properties);
    }

    public MobTabIcon()
    {
        super(DefaultItemProperties.EMPTY);
    }

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
