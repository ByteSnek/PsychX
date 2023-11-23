package bytesnek.tq.level.entity.projectile;

import java.lang.reflect.Method;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

import org.jetbrains.annotations.NotNull;

import bytesnek.hiss.sneaky.Sneaky;
import bytesnek.tq.level.entity.crystal.ComaCrystal;
import bytesnek.tq.rego.DamageTypes;
import bytesnek.tq.rego.Entities;

/**
 * Created by SnakerBone on 20/02/2023
 **/
public class CosmicRay extends AbstractArrow
{
    private Entity shooter;

    public CosmicRay(Entity shooter, Level level)
    {
        super(Entities.COSMIC_RAY.get(), shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ(), level);
        this.shooter = shooter;
    }

    public CosmicRay(EntityType<? extends AbstractArrow> type, Level level)
    {
        super(type, level);
    }

    @Override
    public @NotNull SoundEvent getDefaultHitGroundSoundEvent()
    {
        return SoundEvents.EMPTY;
    }

    @Override
    protected @NotNull ItemStack getPickupItem()
    {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fireImmune()
    {
        return true;
    }

    @Override
    public void tick()
    {
        if (tickCount > 40) {
            kill();
        }

        super.tick();

        setNoGravity(true);
    }

    @Override
    public void onHitEntity(@NotNull EntityHitResult result)
    {
        DamageSources sources = level().damageSources();
        RandomSource random = level().random;

        try {
            Method method = DamageSources.class.getDeclaredMethod("source", ResourceKey.class, Entity.class);
            method.setAccessible(true);
            DamageSource source = Sneaky.cast(method.invoke(sources, DamageTypes.COSMIC, this));

            if (shooter instanceof ComaCrystal crystal) {
                result.getEntity().hurt(source, (crystal.getShield().getHealth() / 100) + (random.nextFloat() - random.nextFloat()));
            } else {
                result.getEntity().hurt(source, random.nextInt(1, 10));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        kill();
    }

    @Override
    public void onHitBlock(@NotNull BlockHitResult result)
    {
        kill();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}