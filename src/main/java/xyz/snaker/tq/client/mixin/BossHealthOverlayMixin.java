package xyz.snaker.tq.client.mixin;

import java.util.Iterator;

import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.snakerlib.utility.tools.StringStuff;
import xyz.snaker.tq.client.Shaders;
import xyz.snaker.tq.level.entity.boss.Utterfly;
import xyz.snaker.tq.rego.Entities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Created by SnakerBone on 18/09/2023
 **/
@Mixin(BossHealthOverlay.class)
public class BossHealthOverlayMixin
{
    @Shadow
    @Final
    public Minecraft minecraft;

    @SuppressWarnings("rawtypes")
    @Inject(method = "render", locals = LocalCapture.CAPTURE_FAILEXCEPTION, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/BossHealthOverlay;drawBar(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/world/BossEvent;)V"))
    public void render(GuiGraphics pGuiGraphics, CallbackInfo ci, int i, int j, Iterator var4, LerpingBossEvent lerpingbossevent, int k, CustomizeGuiOverlayEvent.BossEventProgress event)
    {
        EntityType<Utterfly> utterfly = Entities.UTTERFLY.get();
        String eventName = event.getBossEvent().getName().getString();
        String bossName = utterfly.getDescription().getString();
        if (eventName.equals(bossName)) {
            RenderType type = RenderType.create(StringStuff.placeholderWithId(), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, RenderType.TRANSIENT_BUFFER_SIZE, RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(Shaders::getCrystalized))
                    .setTextureState(RenderStateShard.MultiTextureStateShard.builder()
                            .add(new ResourcePath("textures/sampler/noise_green.png"), true, false)
                            .add(new ResourcePath("textures/sampler/noise_green.png"), true, false)
                            .build())
                    .setCullState(RenderStateShard.CULL)
                    .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                    .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                    .createCompositeState(false));
            drawOverlay(pGuiGraphics.bufferSource().getBuffer(type), k, j, (int) (lerpingbossevent.getProgress() * 182), 5);
        }
    }

    @Unique
    private void drawOverlay(VertexConsumer consumer, int x, int y, int width, int height)
    {
        drawQuad(consumer, x, x + width, y, y + height, 0, 0F, 1F, 0F, 1F);
    }

    @Unique
    private void drawQuad(VertexConsumer consumer, int x, int xMax, int y, int yMax, int z, float u, float uMax, float v, float vMax)
    {
        consumer.vertex(x + 1, yMax - 1, z).uv(u, vMax).endVertex();
        consumer.vertex(xMax - 1, yMax - 1, z).uv(uMax, vMax).endVertex();
        consumer.vertex(xMax - 1, y + 1, z).uv(uMax, v).endVertex();
        consumer.vertex(x + 1, y + 1, z).uv(u, v).endVertex();
    }
}
