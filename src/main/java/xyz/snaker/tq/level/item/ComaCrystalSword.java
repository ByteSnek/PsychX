package xyz.snaker.tq.level.item;

import java.util.function.Consumer;

import xyz.snaker.tq.client.renderer.item.ComaCrystalSwordRenderer;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 23/11/2023
 **/
public class ComaCrystalSword extends SwordItem
{
    private static final Tier TIER = SimpleItemTier.builder()
            .from(Tiers.NETHERITE)
            .attackDamage(Integer.MAX_VALUE)
            .efficiency(Integer.MAX_VALUE)
            .maxUses(0)
            .build();

    public ComaCrystalSword()
    {
        super(TIER, 0, 0, new Properties().durability(0).stacksTo(1));
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer)
    {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions()
        {
            public ComaCrystalSwordRenderer getRenderer()
            {
                return new ComaCrystalSwordRenderer();
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                return getRenderer();
            }
        });
    }
}
