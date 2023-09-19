package xyz.snaker.tq.client.mixin;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.snakerlib.utility.tools.ColourStuff;
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
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.AABB;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;

import org.spongepowered.asm.mixin.*;

/**
 * Created by SnakerBone on 18/09/2023
 **/
@Mixin(BossHealthOverlay.class)
public abstract class BossHealthOverlayMixin
{
    @Unique
    private final EntityType<Utterfly> utterfly = Entities.UTTERFLY.get();

    @Shadow
    @Final
    public Minecraft minecraft;

    @Shadow
    public abstract void drawBar(GuiGraphics pGuiGraphics, int pX, int pY, BossEvent pBossEvent, int p_283619_, int p_281636_);

    @Shadow
    @Final
    public Map<UUID, LerpingBossEvent> events;

    @Overwrite
    @SuppressWarnings({"UnstableApiUsage", "DataFlowIssue"})
    public void render(GuiGraphics graphics)
    {
        if (!this.events.isEmpty()) {
            int i = graphics.guiWidth();
            int j = 12;
            String samplerName = null;
            RenderType type = null;
            int textColour = 16777215;
            for (LerpingBossEvent lerpingbossevent : this.events.values()) {
                int k = i / 2 - 91;
                var event = net.minecraftforge.client.ForgeHooksClient.onCustomizeBossEventProgress(graphics, this.minecraft.getWindow(), lerpingbossevent, k, j, 10 + this.minecraft.font.lineHeight);
                if (!event.isCanceled()) {
                    this.drawBar(graphics, k, j, lerpingbossevent);
                    if (isEvent(lerpingbossevent, utterfly)) {
                        List<Utterfly> utterflies = minecraft.level.getEntitiesOfClass(Utterfly.class, new AABB(minecraft.player.getOnPos()).inflate(100));
                        for (Utterfly utterfly : utterflies) {
                            switch (utterfly.getPhase()) {
                                case 1 -> {
                                    textColour = ColourStuff.hexToInt("FFE800");
                                    type = RenderType.create(StringStuff.placeholderWithId(), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, RenderType.TRANSIENT_BUFFER_SIZE, RenderType.CompositeState.builder()
                                            .setShaderState(new RenderStateShard.ShaderStateShard(Shaders::getCrystalized))
                                            .setTextureState(RenderStateShard.MultiTextureStateShard.builder().add(new ResourcePath("textures/sampler/noise_green.png"), true, false).add(new ResourcePath("textures/sampler/noise_green.png"), true, false).build())
                                            .setCullState(RenderStateShard.CULL)
                                            .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                                            .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                                            .createCompositeState(false));
                                }
                                case 2 -> {
                                    textColour = ColourStuff.hexToInt("FF8300");
                                    type = RenderType.create(StringStuff.placeholderWithId(), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, RenderType.TRANSIENT_BUFFER_SIZE, RenderType.CompositeState.builder()
                                            .setShaderState(new RenderStateShard.ShaderStateShard(Shaders::getCrystalized))
                                            .setTextureState(RenderStateShard.MultiTextureStateShard.builder().add(new ResourcePath("textures/sampler/noise_orange.png"), true, false).add(new ResourcePath("textures/sampler/noise_orange.png"), true, false).build())
                                            .setCullState(RenderStateShard.CULL)
                                            .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                                            .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                                            .createCompositeState(false));
                                }
                                case 3, 4 -> {
                                    textColour = ColourStuff.hexToInt("FF0000");
                                    type = RenderType.create(StringStuff.placeholderWithId(), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, RenderType.TRANSIENT_BUFFER_SIZE, RenderType.CompositeState.builder()
                                            .setShaderState(new RenderStateShard.ShaderStateShard(Shaders::getCrystalized))
                                            .setTextureState(RenderStateShard.MultiTextureStateShard.builder().add(new ResourcePath("textures/sampler/noise_red.png"), true, false).add(new ResourcePath("textures/sampler/noise_red.png"), true, false).build())
                                            .setCullState(RenderStateShard.CULL)
                                            .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                                            .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                                            .createCompositeState(false));
                                }
                                default -> {
                                    SnakerLib.LOGGER.warnf("Unknown phase: %s", utterfly.getPhase());
                                    type = RenderType.create(StringStuff.placeholderWithId(), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, RenderType.TRANSIENT_BUFFER_SIZE, RenderType.CompositeState.builder()
                                            .setShaderState(new RenderStateShard.ShaderStateShard(Shaders::getCrystalized))
                                            .setTextureState(RenderStateShard.MultiTextureStateShard.builder().add(new ResourcePath("textures/sampler/noise_pink.png"), true, false).add(new ResourcePath("textures/sampler/noise_pink.png"), true, false).build())
                                            .setCullState(RenderStateShard.CULL)
                                            .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                                            .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                                            .createCompositeState(false));
                                }
                            }
                        }
                        drawRenderOverlay(graphics, type, k, j, lerpingbossevent);
                    }
                    Component component = lerpingbossevent.getName();
                    int l = this.minecraft.font.width(component);
                    int i1 = i / 2 - l / 2;
                    int j1 = j - 9;
                    graphics.drawString(minecraft.font, component, i1, j1, textColour);
                }
                j += event.getIncrement();
                if (j >= graphics.guiHeight() / 3) {
                    break;
                }
            }
        }
    }

    @Overwrite
    public void drawBar(GuiGraphics graphics, int x, int y, BossEvent event)
    {
        if (event instanceof LerpingBossEvent bossEvent) {
            if (isEvent(bossEvent, utterfly)) {
                drawBar(graphics, x, y, event, 182, 0);
            } else {
                drawBar(graphics, x, y, bossEvent, 182, 0);
                int progress = (int) (bossEvent.getProgress() * 183);
                if (progress > 0) {
                    drawBar(graphics, x, y, bossEvent, progress, 5);
                }
            }
        }
    }

    @Unique
    private void drawRenderOverlay(GuiGraphics graphics, RenderType type, int x, int y, LerpingBossEvent bossEvent)
    {
        drawOverlay(graphics.bufferSource().getBuffer(type), x, y, (int) (bossEvent.getProgress() * 182), 5);
    }

    @Unique
    private void drawOverlay(VertexConsumer consumer, int x, int y, int width, int height)
    {
        drawQuad(consumer, x, x + width, y, y + height, 0, 0, 1, 0, 1);
    }

    @Unique
    private void drawQuad(VertexConsumer consumer, int x, int xMax, int y, int yMax, int z, float u, float uMax, float v, float vMax)
    {
        consumer.vertex(x + 1, yMax - 1, z).uv(u, vMax).endVertex();
        consumer.vertex(xMax - 1, yMax - 1, z).uv(uMax, vMax).endVertex();
        consumer.vertex(xMax - 1, y + 1, z).uv(uMax, v).endVertex();
        consumer.vertex(x + 1, y + 1, z).uv(u, v).endVertex();
    }

    @Unique
    private boolean isEvent(LerpingBossEvent event, EntityType<?> type)
    {
        String bossEvent = event.getName().getString();
        String entityType = type.getDescription().getString();
        return bossEvent.equals(entityType);
    }
}
