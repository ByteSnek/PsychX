package xyz.snaker.snakerlib.internal.mixin;

import xyz.snaker.snakerlib.internal.LevelSavingEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;

/**
 * Created by SnakerBone on 25/06/2023
 **/
@Mixin(Minecraft.class)
public class MinecraftMixin
{
    @Inject(method = "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/eventbus/api/IEventBus;post(Lnet/minecraftforge/eventbus/api/Event;)Z"))
    public void clearLevel(Screen screen, CallbackInfo ci)
    {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new LevelSavingEvent());
    }

    @Inject(method = "setLevel", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/eventbus/api/IEventBus;post(Lnet/minecraftforge/eventbus/api/Event;)Z"))
    public void setLevel(ClientLevel pLevelClient, CallbackInfo ci)
    {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new LevelSavingEvent());
    }
}
