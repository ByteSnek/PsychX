package xyz.snaker.tq.level.entity.projectile;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import xyz.snaker.snakerlib.level.entity.SnakerProjectile;
import xyz.snaker.tq.rego.Rego;

/**
 * Created by SnakerBone on 20/02/2023
 **/
public class CosmicRay extends SnakerProjectile
{
    public CosmicRay(EntityType<CosmicRay> type, Level level)
    {
        super(type, level);
    }

    public CosmicRay(EntityType<CosmicRay> type, LivingEntity shooter, Level level)
    {
        super(type, shooter, level);
    }

    @Override
    public @NotNull SoundEvent getDefaultHitGroundSoundEvent()
    {
        return Rego.SOUND_PEW.get();
    }

    @Override
    public boolean fireImmune()
    {
        return true;
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
        DamageSource source = level().damageSources().arrow(this, null);

        result.getEntity().hurt(source, 8);

        kill();
    }

    @Override
    public void onHitBlock(@NotNull BlockHitResult result)
    {
        kill();
    }
}