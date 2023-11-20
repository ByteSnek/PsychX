package bytesnek.tq.level.display.tab;

import java.util.List;
import java.util.function.Consumer;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import org.jetbrains.annotations.NotNull;

import bytesnek.tq.client.renderer.icon.EntityTabDisplayRenderer;
import bytesnek.tq.level.display.entity.EntityDisplay;
import bytesnek.tq.rego.Entities;
import bytesnek.tq.utility.NoTexture;

/**
 * Created by SnakerBone on 29/10/2023
 **/
@NoTexture
public class EntityTabDisplay extends EntityDisplay
{
    @Override
    public List<LivingEntity> getDisplays(Level level)
    {
        return List.of(
                makeClientboundEntity(Entities.COSMIC_CREEPER, level),
                makeClientboundEntity(Entities.COSMIC_CREEPERITE, level),
                makeClientboundEntity(Entities.COSMO, level),
                makeClientboundEntity(Entities.FLARE, level),
                makeClientboundEntity(Entities.FLUTTERFLY, level),
                makeClientboundEntity(Entities.FROLICKER, level),
                makeClientboundEntity(Entities.SNIPE, level),
                makeClientboundEntity(Entities.UTTERFLY, level)
        );
    }

    @Override
    public void setupRenderer(@NotNull Consumer<IClientItemExtensions> consumer)
    {
        consumer.accept(new IClientItemExtensions()
        {
            public EntityTabDisplayRenderer getRenderer()
            {
                return new EntityTabDisplayRenderer(EntityTabDisplay.this);
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                return getRenderer();
            }
        });
    }
}
