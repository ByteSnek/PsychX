package xyz.snaker.tq.client.fx;

import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.rego.Effects;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Created by SnakerBone on 6/07/2023
 **/
public class SyncopeFX
{
    public static final SyncopeFX INSTANCE = new SyncopeFX();

    private final ResourceLocation postEffect = new ResourcePath("shaders/post/syncope.json");

    private boolean effectActivePreviousTick;

    private SyncopeFX() {}

    @SubscribeEvent
    public void renderTick(TickEvent.PlayerTickEvent event)
    {
        if (event.player == Minecraft.getInstance().player) {
            MobEffectInstance effectInstance = event.player.getEffect(Effects.SYNCOPE.get());
            int duration = effectInstance == null ? 0 : effectInstance.getDuration();
            if (duration > 1) {
                if (!effectActivePreviousTick) {
                    effectActivePreviousTick = true;
                    Minecraft.getInstance().tell(() -> Minecraft.getInstance().gameRenderer.loadEffect(postEffect));
                }
            } else {
                if (effectActivePreviousTick) {
                    effectActivePreviousTick = false;
                    Minecraft.getInstance().tell(() -> Minecraft.getInstance().gameRenderer.shutdownEffect());
                }
            }
        }
    }
}
