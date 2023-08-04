package snaker.snakerlib.internal.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.PlayTagWithOtherKids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

/**
 * Created by SnakerBone on 4/08/2023
 **/
@Mixin(PlayTagWithOtherKids.class)
public class PlayTagWithOtherKidsMixin
{
    @Inject(method = "checkHowManyChasersEachFriendHas", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/stream/Stream;filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;"))
    private static void checkHowManyChasersEachFriendHas(List<LivingEntity> kids, CallbackInfoReturnable<Map<LivingEntity, Integer>> cir)
    {

    }
}
