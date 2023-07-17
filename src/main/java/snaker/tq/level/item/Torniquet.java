package snaker.tq.level.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.level.item.SnakerBaseItem;
import snaker.snakerlib.math.Mh;
import snaker.tq.rego.Rego;

/**
 * Created by SnakerBone on 7/07/2023
 **/
public class Torniquet extends SnakerBaseItem
{
    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity entity, @NotNull ItemStack stack, int remainingUseDuration)
    {
        int useDuration = getUseDuration(stack);
        if (!level.isClientSide) {
            if (remainingUseDuration != useDuration) {
                entity.addEffect(new MobEffectInstance(Rego.EFFECT_SYNCOPE.get(), Mh.secondsToTicks(5)));
            }
        }
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
