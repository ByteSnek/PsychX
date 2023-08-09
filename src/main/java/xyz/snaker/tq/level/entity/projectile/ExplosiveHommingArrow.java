package xyz.snaker.tq.level.entity.projectile;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import xyz.snaker.snakerlib.level.entity.SnakerProjectile;
import xyz.snaker.tq.level.entity.boss.Utterfly;
import xyz.snaker.tq.rego.Rego;

import java.util.Comparator;
import java.util.List;

/**
 * Created by SnakerBone on 20/02/2023 <br>
 * Credit: ProjectE
 **/
public class ExplosiveHommingArrow extends SnakerProjectile
{
    private static final EntityDataAccessor<Integer> TARGET_ID = SynchedEntityData.defineId(ExplosiveHommingArrow.class, EntityDataSerializers.INT);
    private static final int NULL_TARGET_ID = -1;
    private int newTargetCooldown = 0;

    public ExplosiveHommingArrow(EntityType<ExplosiveHommingArrow> type, Level level)
    {
        super(type, level);
    }

    public ExplosiveHommingArrow(Level level, LivingEntity shooter, double damage)
    {
        super(Rego.ENTITY_EXPLOSIVE_HOMMING_ARROW.get(), shooter, level);
        setBaseDamage(damage);
    }

    @NotNull
    @Override
    public EntityType<?> getType()
    {
        return Rego.ENTITY_EXPLOSIVE_HOMMING_ARROW.get();
    }

    @Override
    public void defineSynchedData()
    {
        super.defineSynchedData();
        entityData.define(TARGET_ID, NULL_TARGET_ID);
    }

    @Override
    protected void doPostHurtEffects(@NotNull LivingEntity living)
    {
        super.doPostHurtEffects(living);
        living.invulnerableTime = 0;
    }

    @Override
    public void tick()
    {
        if (!level().isClientSide && tickCount > 3) {
            if (hasTarget() && (!getTarget().isAlive() || inGround)) {
                entityData.set(TARGET_ID, NULL_TARGET_ID);
            }
            if (!hasTarget() && !inGround && newTargetCooldown <= 0) {
                findNewTarget();
            } else {
                newTargetCooldown--;
            }
        }
        if (tickCount > 3 && hasTarget() && !inGround) {
            double mX = getDeltaMovement().x();
            double mY = getDeltaMovement().y();
            double mZ = getDeltaMovement().z();
            Entity target = getTarget();
            Vec3 arrowLoc = new Vec3(getX(), getY(), getZ());
            Vec3 targetLoc = new Vec3(target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ());
            Vec3 lookVec = targetLoc.subtract(arrowLoc);
            Vec3 arrowMotion = new Vec3(mX, mY, mZ);
            double theta = wrap180Radian(angleBetween(arrowMotion, lookVec));
            theta = clampAbs(theta, Math.PI / 2);
            Vec3 crossProduct = arrowMotion.cross(lookVec).normalize();
            Vec3 adjustedLookVec = transform(crossProduct, theta, arrowMotion);
            shoot(adjustedLookVec.x, adjustedLookVec.y, adjustedLookVec.z, 1.0F, 0);
        }
        super.tick();
    }

    private Vec3 transform(Vec3 axis, double angle, Vec3 normal)
    {
        double m00 = 1;
        double m01 = 0;
        double m02 = 0;
        double m10 = 0;
        double m11 = 1;
        double m12 = 0;
        double m20 = 0;
        double m21 = 0;
        double m22 = 1;
        double mag = Math.sqrt(axis.x * axis.x + axis.y * axis.y + axis.z * axis.z);
        if (mag >= 1.0E-10) {
            mag = 1.0 / mag;
            double ax = axis.x * mag;
            double ay = axis.y * mag;
            double az = axis.z * mag;
            double sinTheta = Math.sin(angle);
            double cosTheta = Math.cos(angle);
            double t = 1.0 - cosTheta;
            double xz = ax * az;
            double xy = ax * ay;
            double yz = ay * az;
            m00 = t * ax * ax + cosTheta;
            m01 = t * xy - sinTheta * az;
            m02 = t * xz + sinTheta * ay;
            m10 = t * xy + sinTheta * az;
            m11 = t * ay * ay + cosTheta;
            m12 = t * yz - sinTheta * ax;
            m20 = t * xz - sinTheta * ay;
            m21 = t * yz + sinTheta * ax;
            m22 = t * az * az + cosTheta;
        }
        return new Vec3(m00 * normal.x + m01 * normal.y + m02 * normal.z, m10 * normal.x + m11 * normal.y + m12 * normal.z, m20 * normal.x + m21 * normal.y + m22 * normal.z);
    }

    private void findNewTarget()
    {
        List<Mob> candidates = level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(8, 8, 8));
        if (!candidates.isEmpty()) {
            candidates.sort(Comparator.comparing(this::distanceToSqr, Double::compare));
            entityData.set(TARGET_ID, candidates.get(0).getId());
        }
        newTargetCooldown = 5;
    }

    private Mob getTarget()
    {
        return (Mob) level().getEntity(entityData.get(TARGET_ID));
    }

    private boolean hasTarget()
    {
        return getTarget() != null && !(getTarget() instanceof Utterfly);
    }

    private double angleBetween(Vec3 a, Vec3 b)
    {
        double vDot = a.dot(b) / (a.length() * b.length());
        if (vDot < -1.0) {
            vDot = -1.0;
        }
        if (vDot > 1.0) {
            vDot = 1.0;
        }
        return Math.acos(vDot);
    }

    private double wrap180Radian(double radian)
    {
        radian %= 2 * Math.PI;
        while (radian >= Math.PI) {
            radian -= 2 * Math.PI;
        }
        while (radian < -Math.PI) {
            radian += 2 * Math.PI;
        }
        return radian;
    }

    private double clampAbs(double param, double maxMagnitude)
    {
        if (Math.abs(param) > maxMagnitude) {
            if (param < 0) {
                param = -Math.abs(maxMagnitude);
            } else {
                param = Math.abs(maxMagnitude);
            }
        }
        return param;
    }

    @Override
    protected void onHit(@NotNull HitResult result)
    {
        super.onHit(result);
        level().explode(this, getX(), getY(), getZ(), 1, Level.ExplosionInteraction.NONE);
    }

    @Override
    public boolean ignoreExplosion()
    {
        return true;
    }

    @Override
    public boolean fireImmune()
    {
        return true;
    }
}