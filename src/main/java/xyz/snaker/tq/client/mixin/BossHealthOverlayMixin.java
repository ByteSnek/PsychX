package xyz.snaker.tq.client.mixin;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.client.render.processor.SimpleRenderTypeProcessor;
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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.ForgeHooksClient;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;

import org.spongepowered.asm.mixin.*;

/**
 * Created by SnakerBone on 18/09/2023
 **/
@Mixin(BossHealthOverlay.class)
public abstract class BossHealthOverlayMixin implements SimpleRenderTypeProcessor
{
    @Unique
    private final EntityType<Utterfly> tourniqueted$utterfly = Entities.UTTERFLY.get();

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    protected abstract void drawBar(GuiGraphics pGuiGraphics, int pX, int pY, BossEvent pBossEvent, int pU, int pV);

    @Shadow
    @Final
    Map<UUID, LerpingBossEvent> events;

    @Overwrite
    @SuppressWarnings({"UnstableApiUsage", "DataFlowIssue"})
    public void render(GuiGraphics graphics)
    {
        Function<String, RenderType> sCreator = samplerName -> create(StringStuff.placeholderWithId(), new Pair<>(DefaultVertexFormat.POSITION_TEX, blitSampler(Shaders::getCrystalized, new ResourcePath("textures/sampler/noise_" + samplerName + ".png"), true, false)));
        if (!events.isEmpty()) {
            int sWidth = graphics.guiWidth();
            int sPosY = 12;
            RenderType sType = sCreator.apply("pink");
            int sTextColour = 16777215;
            for (LerpingBossEvent sEvent : events.values()) {
                int sPosX = sWidth / 2 - 91;
                var sForgeEvent = ForgeHooksClient.onCustomizeBossEventProgress(graphics, minecraft.getWindow(), sEvent, sPosX, sPosY, 10 + minecraft.font.lineHeight);
                if (!sForgeEvent.isCanceled()) {
                    drawBar(graphics, sPosX, sPosY, sEvent);
                    if (tourniqueted$isEvent(sEvent, tourniqueted$utterfly)) {
                        int sRenderDistance = minecraft.options.renderDistance().get();
                        int sSimulationDistance = minecraft.options.simulationDistance().get();
                        int sPackedDistance = sRenderDistance <= 0 || sSimulationDistance <= 0 ? 250 : sRenderDistance * sRenderDistance + sSimulationDistance * sSimulationDistance;
                        List<Utterfly> sUtterflies = minecraft.level.getEntitiesOfClass(Utterfly.class, new AABB(minecraft.player.getOnPos()).inflate(sPackedDistance));
                        for (Utterfly sUtterfly : sUtterflies) {
                            switch (sUtterfly.getPhase()) {
                                case 1 -> {
                                    sTextColour = ColourStuff.hexToInt("FFE800");
                                    sType = sCreator.apply("green");
                                }
                                case 2 -> {
                                    sTextColour = ColourStuff.hexToInt("FF8300");
                                    sType = sCreator.apply("orange");
                                }
                                case 3, 4 -> {
                                    sTextColour = ColourStuff.hexToInt("FF0000");
                                    sType = sCreator.apply("red");
                                }
                                default -> SnakerLib.LOGGER.warnf("Unknown phase: %s", sUtterfly.getPhase());
                            }
                        }
                        tourniqueted$drawRenderOverlay(graphics, sType, sPosX, sPosY, sEvent);
                    }
                    Component sName = sEvent.getName();
                    int sFontWidth = minecraft.font.width(sName);
                    int sFontX = sWidth / 2 - sFontWidth / 2;
                    int sFontY = sPosY - 9;
                    graphics.drawString(minecraft.font, sName, sFontX, sFontY, sTextColour);
                }
                sPosY += sForgeEvent.getIncrement();
                if (sPosY >= graphics.guiHeight() / 3) {
                    break;
                }
            }
        }
    }

    @Overwrite
    public void drawBar(GuiGraphics graphics, int x, int y, BossEvent event)
    {
        if (event instanceof LerpingBossEvent bossEvent) {
            if (tourniqueted$isEvent(bossEvent, tourniqueted$utterfly)) {
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
    private void tourniqueted$drawRenderOverlay(GuiGraphics graphics, RenderType type, int x, int y, LerpingBossEvent bossEvent)
    {
        tourniqueted$drawOverlay(graphics.bufferSource().getBuffer(type), x, y, (int) (bossEvent.getProgress() * 182), 5);
    }

    @Unique
    private void tourniqueted$drawOverlay(VertexConsumer consumer, int x, int y, int width, int height)
    {
        tourniqueted$drawQuad(consumer, x, x + width, y, y + height, 0, 0, 1, 0, 1);
    }

    @Unique
    private void tourniqueted$drawQuad(VertexConsumer consumer, int x, int xMax, int y, int yMax, int z, float u, float uMax, float v, float vMax)
    {
        consumer.vertex(x + 1, yMax - 1, z).uv(u, vMax).endVertex();
        consumer.vertex(xMax - 1, yMax - 1, z).uv(uMax, vMax).endVertex();
        consumer.vertex(xMax - 1, y + 1, z).uv(uMax, v).endVertex();
        consumer.vertex(x + 1, y + 1, z).uv(u, v).endVertex();
    }

    @Unique
    private boolean tourniqueted$isEvent(LerpingBossEvent event, EntityType<?> type)
    {
        String bossEvent = event.getName().getString();
        String entityType = type.getDescription().getString();
        return bossEvent.equals(entityType);
    }
}
