package bytesnek.tq.level.entity.projectile;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 20/02/2023
 **/
public class CosmicRay extends Projectile
{
    public CosmicRay(EntityType<? extends Projectile> type, Level level)
    {
        super(type, level);
    }

    @Override
    public boolean fireImmune()
    {
        return true;
    }

    @Override
    protected void defineSynchedData()
    {

    }

    @Override
    public void tick()
    {
        super.tick();

        setNoGravity(true);
    }

    @Override
    public void onHitEntity(EntityHitResult result)
    {
        DamageSource source = level().damageSources().generic();
        result.getEntity().hurt(source, 8);
        kill();
    }

    @Override
    public void onHitBlock(@NotNull BlockHitResult result)
    {
        kill();
    }
}