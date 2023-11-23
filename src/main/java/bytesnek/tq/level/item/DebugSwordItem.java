package bytesnek.tq.level.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;

import org.jetbrains.annotations.NotNull;

import codechicken.lib.item.SimpleItemTier;

/**
 * Created by SnakerBone on 23/11/2023
 **/
public class DebugSwordItem extends SwordItem
{
    private static final Tier TIER = SimpleItemTier.builder()
            .from(Tiers.NETHERITE)
            .attackDamage(Integer.MAX_VALUE)
            .efficiency(Integer.MAX_VALUE)
            .maxUses(0)
            .build();

    public DebugSwordItem()
    {
        super(TIER, 0, 0, new Properties().durability(0).stacksTo(1));
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack)
    {
        return true;
    }
}
