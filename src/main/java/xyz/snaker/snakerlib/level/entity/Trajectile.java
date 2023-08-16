package xyz.snaker.snakerlib.level.entity;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 26/05/2023
 **/
public abstract class Trajectile extends AbstractArrow
{
    public Trajectile(EntityType<? extends AbstractArrow> type, Level level)
    {
        super(type, level);
    }

    public Trajectile(EntityType<? extends AbstractArrow> type, LivingEntity shooter, Level level)
    {
        super(type, shooter, level);
    }

    @Override
    public void tick()
    {
        if (tickCount > 40) {
            kill();
        }
        super.tick();
    }

    @Override
    protected @NotNull ItemStack getPickupItem()
    {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
