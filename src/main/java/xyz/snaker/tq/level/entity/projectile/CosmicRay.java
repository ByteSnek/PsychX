package xyz.snaker.tq.level.entity.projectile;

import java.lang.reflect.Method;

import xyz.snaker.hiss.sneaky.Sneaky;
import xyz.snaker.tq.level.entity.crystal.ComaCrystal;
import xyz.snaker.tq.rego.DamageTypes;
import xyz.snaker.tq.rego.Entities;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.network.NetworkHooks;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 20/02/2023
 **/
public class CosmicRay extends AbstractHurtingProjectile
{
    public CosmicRay(Level level, Entity owner, double speedX, double speedY, double speedZ)
    {
        super(Entities.COSMIC_RAY.get(), owner.getX(), owner.getEyeY() - 0.1, owner.getZ(), speedX, speedY, speedZ, level);
    }

    public CosmicRay(EntityType<? extends AbstractHurtingProjectile> type, Level level)
    {
        super(type, level);
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

            if (getOwner() instanceof ComaCrystal) {
                int index = random.nextInt(1, 10);
                float damage = Sneaky.getEarlyMemory(1024, index) / 10_000F;

                result.getEntity().hurt(source, damage);
            } else {
                float damage = random.nextInt(1, 10);

                result.getEntity().hurt(source, damage);
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