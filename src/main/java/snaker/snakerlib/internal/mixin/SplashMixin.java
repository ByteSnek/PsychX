package snaker.snakerlib.internal.mixin;

import net.minecraft.client.resources.SplashManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import snaker.snakerlib.SnakerLib;
import snaker.snakerlib.data.SnakerConstants;

import java.util.Arrays;
import java.util.List;

/**
 * Created by SnakerBone on 29/07/2023
 **/
@Mixin(SplashManager.class)
public class SplashMixin
{
    @Shadow
    @Final
    public List<String> splashes;

    @Inject(method = "apply(Ljava/util/List;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "HEAD"), cancellable = true)
    public void apply(List<String> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler, CallbackInfo ci)
    {
        ci.cancel();

        if (!splashes.isEmpty()) {
            splashes.clear();
        }

        if (splashes.addAll(Arrays.asList(SnakerConstants.Texts.SPLASHES))) {
            int splashesSize = splashes.size();
            SnakerLib.LOGGER.infof("Successfuly added %s new splashes", splashesSize);
        }
    }
}
