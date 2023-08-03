package snaker.snakerlib.utility;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import snaker.snakerlib.math.Maths;

import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

/**
 * Created by SnakerBone on 5/03/2023
 **/
public class AttackType
{
    public static void attackEntitiesInArea(ItemStack stack, Player attacker, AOE aoe, @Nullable Float damage, int area)
    {
        Level level = attacker.level();
        if (!level.isClientSide) {
            double x = attacker.getX(), y = attacker.getY(), z = attacker.getZ();
            AABB aabb = new AABB(x, y, z, x, y, z).inflate(area);
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, aabb);
            DamageSource source = level.damageSources().playerAttack(attacker);
            BigInteger amount = switch (aoe) {
                case NORMAL -> BigInteger.valueOf(stack.getMaxDamage());
                case ADDITORIAL -> Maths.additorial(entities.size());
                case FACTORIAL -> Maths.factorial(entities.size());
            };
            for (LivingEntity entity : entities) {
                float dmg = Objects.requireNonNullElseGet(damage, amount::floatValue);
                if (entity != attacker) {
                    entity.hurt(source, dmg);
                }
            }
        }
    }

    public static void attackEntitiesInArea(ItemStack stack, Player attacker, AOE aoe, @Nullable Float amount, @Nullable DamageSource source, int area)
    {
        Level level = attacker.level();
        if (!level.isClientSide) {
            double x = attacker.getX(), y = attacker.getY(), z = attacker.getZ();
            AABB aabb = new AABB(x, y, z, x, y, z).inflate(area);
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, aabb);
            DamageSource attackSource = level.damageSources().playerAttack(attacker);
            BigInteger attackDamage = switch (aoe) {
                case NORMAL -> BigInteger.valueOf(stack.getMaxDamage());
                case ADDITORIAL -> Maths.additorial(entities.size());
                case FACTORIAL -> Maths.factorial(entities.size());
            };
            for (LivingEntity entity : entities) {
                float finalDamage = Objects.requireNonNullElseGet(amount, attackDamage::floatValue);
                DamageSource finalSource = Objects.requireNonNullElse(source, attackSource);
                if (entity != attacker) {
                    entity.hurt(finalSource, finalDamage);
                }
            }
        }
    }

    public static void discardEntitiesInArea(Player attacker, int area)
    {
        Level level = attacker.level();
        if (!level.isClientSide) {
            double x = attacker.getX(), y = attacker.getY(), z = attacker.getZ();
            AABB aabb = new AABB(x, y, z, x, y, z).inflate(area);
            List<Entity> entities = level.getEntitiesOfClass(Entity.class, aabb);
            for (Entity entity : entities) {
                if (entity != attacker) {
                    entity.discard();
                }
            }
        }
    }
}
