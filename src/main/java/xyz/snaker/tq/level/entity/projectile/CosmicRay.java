package xyz.snaker.tq.level.entity.projectile;

import xyz.snaker.snakerlib.level.entity.Trajectile;
import xyz.snaker.tq.rego.Sounds;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 20/02/2023
 **/
public class CosmicRay extends Trajectile
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
        return Sounds.SNIPE_SHOOT.get();
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