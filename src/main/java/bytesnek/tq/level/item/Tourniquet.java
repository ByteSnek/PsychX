package bytesnek.tq.level.item;

import xyz.snaker.snakerlib.math.Maths;
import xyz.snaker.snakerlib.utility.item.ItemProperties;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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

import bytesnek.tq.level.world.dimension.Comatose;
import bytesnek.tq.rego.Levels;

/**
 * Created by SnakerBone on 7/07/2023
 **/
public class Tourniquet extends Item
{
    private int remainingUseDuration;

    public Tourniquet()
    {
        super(ItemProperties.SPECIAL.defaultDurability(1));
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity entity, @NotNull ItemStack stack, int remainingUseDuration)
    {
        this.remainingUseDuration = remainingUseDuration;

        if (entity instanceof Player player) {
            if (shouldTeleportPlayer(stack)) {
                Level playerLevel = player.level();

                synchronized (playerLevel) {
                    if (playerLevel instanceof ServerLevel serverLevel) {
                        MinecraftServer server = serverLevel.getServer();
                        ResourceKey<Level> key = playerLevel.dimension() == Levels.COMATOSE ? getOrDelegateWakeUpDest(player) : Levels.COMATOSE;
                        ServerLevel dest = server.getLevel(key);

                        writeWakeUpDest(player);

                        if (dest != null && player.canChangeDimensions()) {
                            stack.hurtAndBreak(Integer.MAX_VALUE, player, p -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                            player.stopUsingItem();
                            player.changeDimension(dest, Comatose.getTeleporter().apply(player.getOnPos()));
                        }
                    }
                }
            }
        }
    }

    public ResourceKey<Level> getOrDelegateWakeUpDest(Player player)
    {
        CompoundTag tag = player.getPersistentData();

        if (tag.contains("PlayerWakeUpDestination")) {
            return ResourceKey.create(Registries.DIMENSION, new ResourceLocation(tag.getString("PlayerWakeUpDestination")));
        }

        return Level.OVERWORLD;
    }

    public void writeWakeUpDest(Player player)
    {
        ResourceKey<Level> key = player.level().dimension();
        if (key == Levels.COMATOSE) {
            return;
        }
        String dest = key.location().toString();
        player.getPersistentData().putString("PlayerWakeUpDestination", dest);
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

    public boolean shouldTeleportPlayer(ItemStack stack)
    {
        int useDuration = getUseDuration(stack);
        int ticksRequiredForTeleport = getTicksRequiredForTeleport();

        return Maths.diffEquals(useDuration, remainingUseDuration, ticksRequiredForTeleport);
    }

    public int getTicksRequiredForTeleport()
    {
        return 50;
    }

    public int getRemainingUseDuration()
    {
        return remainingUseDuration;
    }
}
