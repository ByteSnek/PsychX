package xyz.snaker.tq.level.display.entity;

import java.util.function.Consumer;
import java.util.function.Supplier;

import xyz.snaker.snakerlib.utility.item.ItemProperties;
import xyz.snaker.tq.level.display.DisplayObject;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 27/05/2023
 **/
public abstract class EntityDisplay extends Item implements DisplayObject<LivingEntity>
{
    private transient int stackIndex;

    public EntityDisplay(Properties properties, int stackIndex)
    {
        super(properties);
        this.stackIndex = stackIndex;
    }

    public EntityDisplay(int stackIndex)
    {
        this(ItemProperties.EMPTY, stackIndex);
    }

    public EntityDisplay()
    {
        this(-1);
    }

    public int getStackIndex()
    {
        return stackIndex;
    }

    public void setStackIndex(int stackIndex)
    {
        this.stackIndex = stackIndex;
    }

    public <T extends LivingEntity> T makeClientboundEntity(Supplier<EntityType<T>> type, Level level)
    {
        return level.isClientSide ? type.get().create(level) : null;
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer)
    {
        setupRenderer(consumer);
    }
}
