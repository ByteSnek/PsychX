package snaker.tq.level.entity.mob;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.level.entity.SnakerMob;
import snaker.snakerlib.utility.LevelUtil;
import snaker.tq.level.entity.projectile.CosmicRay;
import snaker.tq.rego.Rego;

/**
 * Created by SnakerBone on 15/07/2023
 **/
public class Leet extends SnakerMob implements RangedAttackMob
{
    public Leet(EntityType<Leet> type, Level level)
    {
        super(type, level);
        moveControl = new FlyingMoveControl(this, 10, true);
    }

    public static AttributeSupplier attributes()
    {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.ATTACK_SPEED, 1)
                .add(Attributes.FLYING_SPEED, 0.25)
                .add(Attributes.MOVEMENT_SPEED, 0.25).build();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public SoundEvent getHurtSound(@NotNull DamageSource source)
    {
        return SoundEvents.GENERIC_HURT;
    }

    @Override
    public SoundEvent getDeathSound()
    {
        return Rego.SOUND_ENTITY_DEATH.get();
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source)
    {
        return false;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float flval)
    {
        CosmicRay ray = new CosmicRay(Rego.ENTITY_COSMIC_RAY.get(), level());

        double x = target.getX() - getX();
        double y = target.getY() + target.getEyeHeight() - 1;
        double z = target.getZ() - getZ();

        ray.shoot(x, y - ray.getY() + Math.sqrt(x * x + z * z) * 0.2, z, 1.6F, 12);

        level().addFreshEntity(ray);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos)
    {

    }

    public static <T extends Entity> boolean spawnRules(EntityType<T> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random)
    {
        return LevelUtil.isDimension(level, Rego.Keys.COMATOSE);
    }
}
