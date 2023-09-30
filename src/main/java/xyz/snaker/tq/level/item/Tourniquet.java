package xyz.snaker.tq.level.item;

import xyz.snaker.snakerlib.data.DefaultItemProperties;
import xyz.snaker.snakerlib.math.Maths;
import xyz.snaker.tq.level.world.dimension.Comatose;
import xyz.snaker.tq.rego.Levels;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 7/07/2023
 **/
public class Tourniquet extends Item
{
    public Tourniquet()
    {
        super(DefaultItemProperties.SPECIAL);
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity entity, @NotNull ItemStack stack, int remainingUseDuration)
    {
        int useDuration = getUseDuration(stack);
        if (entity instanceof Player player) {
            if (Maths.diffEquals(useDuration, remainingUseDuration, 50)) {
                Level playerLevel = player.level();
                synchronized (playerLevel) {
                    if (playerLevel instanceof ServerLevel serverLevel) {
                        MinecraftServer server = serverLevel.getServer();
                        ResourceKey<Level> key = playerLevel.dimension() == Levels.COMATOSE ? Level.OVERWORLD : Levels.COMATOSE;
                        ServerLevel dest = server.getLevel(key);
                        if (dest != null && player.canChangeDimensions()) {
                            player.stopUsingItem();
                            stack.hurtAndBreak(serverLevel.random.nextInt(stack.getMaxDamage() / 2), player, p -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                            if (key == Levels.COMATOSE) {
                                player.changeDimension(dest, Comatose.getTeleporter().apply(player.getOnPos()));
                            } else {
                                player.changeDimension(dest, Comatose.getTeleporter().apply(player.getOnPos()));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canBeDepleted()
    {
        return true;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand)
    {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack)
    {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack)
    {
        return 72000;
    }
}
