package xyz.snaker.tq.level.item;

import java.util.function.Consumer;

import xyz.snaker.snakerlib.level.item.SnakerBaseItem;
import xyz.snaker.snakerlib.utility.ResourceStuff;
import xyz.snaker.tq.client.RenderTypes;
import xyz.snaker.tq.client.render.item.CosmoSpineRenderer;
import xyz.snaker.tq.rego.Rego;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
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
            public boolean is(Item item)
            {
                return ResourceStuff.getPath(item).equals(ResourceStuff.getPath(CosmoSpine.this));
            }

            public BlockEntityWithoutLevelRenderer getRenderer()
            {
                if (is(Rego.ITEM_RED_COSMO_SPINE.get())) {
                    return new CosmoSpineRenderer(RenderTypes.OBJ_RED_STARS);
                } else if (is(Rego.ITEM_GREEN_COSMO_SPINE.get())) {
                    return new CosmoSpineRenderer(RenderTypes.OBJ_GREEN_STARS);
                } else if (is(Rego.ITEM_BLUE_COSMO_SPINE.get())) {
                    return new CosmoSpineRenderer(RenderTypes.OBJ_BLUE_STARS);
                } else if (is(Rego.ITEM_YELLOW_COSMO_SPINE.get())) {
                    return new CosmoSpineRenderer(RenderTypes.OBJ_YELLOW_STARS);
                } else if (is(Rego.ITEM_PINK_COSMO_SPINE.get())) {
                    return new CosmoSpineRenderer(RenderTypes.OBJ_PINK_STARS);
                } else if (is(Rego.ITEM_PURPLE_COSMO_SPINE.get())) {
                    return new CosmoSpineRenderer(RenderTypes.OBJ_PURPLE_STARS);
                } else if (is(Rego.ITEM_ALPHA_COSMO_SPINE.get())) {
                    return new CosmoSpineRenderer(RenderTypes.OBJ_BLACK_STARS);
                } else if (is(Rego.ITEM_ANTI_COSMO_SPINE.get())) {
                    return new CosmoSpineRenderer(RenderTypes.OBJ_WHITE_STARS);
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
