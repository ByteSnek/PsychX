package xyz.snaker.tq.level.item;

import xyz.snaker.snakerlib.data.DefaultItemProperties;
import xyz.snaker.tq.client.screen.AtlasScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 14/10/2023
 **/
public class TourniquetedAtlas extends Item
{
    public TourniquetedAtlas()
    {
        super(DefaultItemProperties.TOOL);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide) {
            Minecraft.getInstance().setScreen(new AtlasScreen(stack));
        }

        return new InteractionResultHolder<>(InteractionResult.PASS, stack);
    }
}
