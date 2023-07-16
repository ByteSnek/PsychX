package snaker.snakerlib.level.item;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Created by SnakerBone on 17/03/2023
 **/
public enum SnakerTier implements Tier
{
    FACTORIAL(6, 0, 9, 4, 16, () -> Ingredient.of(Items.AIR));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantability;

    SnakerTier(int level, int uses, float speed, float damage, int enchantability, Supplier<Ingredient> material)
    {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantability = enchantability;
    }

    @Override
    public int getUses()
    {
        return uses;
    }

    @Override
    public float getSpeed()
    {
        return speed;
    }

    @Override
    public float getAttackDamageBonus()
    {
        return damage;
    }

    @Override
    @SuppressWarnings({"deprecation", "RedundantSuppression"})
    public int getLevel()
    {
        return level;
    }

    @Override
    public int getEnchantmentValue()
    {
        return enchantability;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient()
    {
        return Ingredient.EMPTY;
    }
}
