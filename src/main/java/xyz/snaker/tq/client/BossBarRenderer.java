package xyz.snaker.tq.client;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.client.render.processor.SimpleRenderTypeProcessor;
import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.snakerlib.utility.tools.ColourStuff;
import xyz.snaker.snakerlib.utility.tools.RenderStuff;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.entity.boss.Utterfly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;

/**
 * Created by SnakerBone on 30/03/2023
 **/
@Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class BossBarRenderer
{
    private static final Set<Utterfly> UTTERFLIES = new HashSet<>();

    public static void addUtterfly(Utterfly utterfly)
    {
        UTTERFLIES.add(utterfly);
        SnakerLib.LOGGER.warnf("--- Added Utterfly --- \n UUID: %s \n ID: %s", utterfly.getStringUUID(), utterfly.getId());
    }

    public static void removeUtterfly(Utterfly utterfly)
    {
        UTTERFLIES.remove(utterfly);
        SnakerLib.LOGGER.warnf("--- Removed Utterfly --- \n UUID: %s \n ID: %s", utterfly.getStringUUID(), utterfly.getId());
    }

    public static boolean requiresUpdate()
    {
        return UTTERFLIES.isEmpty();
    }

    @SubscribeEvent
    public static void renderEvents(CustomizeGuiOverlayEvent.BossEventProgress event)
    {
        renderUtterflyEvent(event);
    }

    public static void renderUtterflyEvent(CustomizeGuiOverlayEvent.BossEventProgress event)
    {
        if (!UTTERFLIES.isEmpty()) {

            for (Utterfly utterfly : new CopyOnWriteArrayList<>(UTTERFLIES)) {

                if (utterfly.getBossEventId() == event.getBossEvent().getId()) {

                    event.setCanceled(true);

                    String textColour;
                    String samplerName;

                    switch (utterfly.getPhase()) {

                        case 1 -> {
                            textColour = "FFE800";
                            samplerName = "noise_green";
                        }

                        case 2 -> {
                            textColour = "FF8300";
                            samplerName = "noise_orange";
                        }

                        case 3, 4 -> {
                            textColour = "FF0000";
                            samplerName = "noise_red";
                        }

                        default -> {
                            SnakerLib.LOGGER.warnf("Unknown phase: %s", utterfly.getPhase());
                            textColour = "FF00FF";
                            samplerName = "noise_pink";
                        }
                    }

                    render(event, textColour, samplerName);
                }
            }
        }
    }

    public static void render(CustomizeGuiOverlayEvent.BossEventProgress event, String textColour, String samplerName)
    {
        SimpleRenderTypeProcessor processor = RenderStuff.createFreshProcessor();
        RenderType type = processor.create(null, new Pair<>(DefaultVertexFormat.POSITION_TEX, processor.sampler(Shaders::getCrystalized, new ResourcePath("textures/sampler/" + samplerName + ".png"), false, false)));
        Minecraft minecraft = Minecraft.getInstance();
        MultiBufferSource.BufferSource source = minecraft.renderBuffers().bufferSource();

        VertexConsumer consumer = source.getBuffer(type);
        GuiGraphics graphics = event.getGuiGraphics();
        PoseStack stack = graphics.pose();

        LerpingBossEvent info = event.getBossEvent();
        Component name = info.getName();

        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, BossHealthOverlay.GUI_BARS_LOCATION);

        int width = event.getWindow().getGuiScaledWidth();
        int x = event.getX();
        int y = event.getY();

        float textureWidth = minecraft.font.width(name);
        float textureX = width / 2F - textureWidth / 2F;
        float textureY = y - 9F;

        drawBar(graphics, x, y, info);
        drawOverlay(consumer, x, y, (int) (info.getProgress() * 182), 5);

        source.endBatch();
        stack.translate(0, 0, 16);
        graphics.drawString(minecraft.font, name.getVisualOrderText(), textureX, textureY, ColourStuff.hexToInt(textColour), true);
    }


    private static void drawBar(GuiGraphics graphics, int x, int y, BossEvent info)
    {
        drawVertex(graphics, x, y, 0, info.getColor().ordinal() * 5 * 2, 182, 5);
        if (info.getOverlay() != BossEvent.BossBarOverlay.PROGRESS) {
            drawVertex(graphics, x, y, 0, 80 + (info.getOverlay().ordinal() - 1) * 5 * 2, 182, 5);
        }
        int progress = (int) (info.getProgress() * 183);
        if (progress > 0) {
            drawVertex(graphics, x, y, 0, info.getColor().ordinal() * 5 * 2 + 5, 0, 5);
            if (info.getOverlay() != BossEvent.BossBarOverlay.PROGRESS) {
                drawVertex(graphics, x, y, 0, 80 + (info.getOverlay().ordinal() - 1) * 5 * 2 + 5, progress, 5);
            }
        }
    }

    private static void drawOverlay(VertexConsumer consumer, int x, int y, int width, int height)
    {
        drawQuad(consumer, x, x + width, y, y + height, 0, 0F, 1F, 0F, 1F);
    }

    private static void drawQuad(VertexConsumer consumer, int x, int xMax, int y, int yMax, int z, float u, float uMax, float v, float vMax)
    {
        consumer.vertex(x + 1, yMax - 1, z).uv(u, vMax).endVertex();
        consumer.vertex(xMax - 1, yMax - 1, z).uv(uMax, vMax).endVertex();
        consumer.vertex(xMax - 1, y + 1, z).uv(uMax, v).endVertex();
        consumer.vertex(x + 1, y + 1, z).uv(u, v).endVertex();
    }

    private static void drawVertex(GuiGraphics graphics, int x, int y, int u, int v, int width, int height)
    {
        graphics.blit(BossHealthOverlay.GUI_BARS_LOCATION, x, y, 0, (float) u, (float) v, width, height, 256, 256);
    }
}