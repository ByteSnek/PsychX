package xyz.snaker.tq.mixin.client;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import xyz.snaker.hiss.logger.Logger;
import xyz.snaker.hiss.logger.Loggers;
import xyz.snaker.hiss.sneaky.Sneaky;
import xyz.snaker.hiss.utility.Colours;
import xyz.snaker.hiss.utility.Strings;
import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.client.render.SRTP;
import xyz.snaker.snakerlib.resources.ResourceReference;
import xyz.snaker.tq.client.Shaders;
import xyz.snaker.tq.level.entity.utterfly.Utterfly;
import xyz.snaker.tq.rego.Entities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.registries.DeferredHolder;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;

import org.spongepowered.asm.mixin.*;

/**
 * Created by SnakerBone on 18/09/2023
 **/
@Mixin(BossHealthOverlay.class)
public abstract class BossHealthOverlayMixin implements SRTP
{
    @Unique
    private static final Logger tourniqueted$LOGGER = Loggers.getLogger();

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Final
    Map<UUID, LerpingBossEvent> events;

    @Shadow
    protected abstract void drawBar(GuiGraphics graphics, int posX, int posY, BossEvent event);

    @Overwrite
    @SuppressWarnings("DataFlowIssue")
    public void render(GuiGraphics graphics)
    {
        Function<String, RenderType> sCreator = samplerName -> create(SnakerLib.placeholderWithId(), new Pair<>(DefaultVertexFormat.POSITION_TEX, blitSampler(Shaders::getCrystalized, new ResourceReference("textures/sampler/noise_" + samplerName + ".png"), true, false)));
        if (!events.isEmpty()) {
            int sWidth = graphics.guiWidth();
            int sPosY = 12;

            RenderType sType = sCreator.apply("pink");

            int sTextColour = 16777215;

            for (LerpingBossEvent sEvent : events.values()) {
                int sPosX = sWidth / 2 - 91;

                var sForgeEvent = ClientHooks.onCustomizeBossEventProgress(graphics, minecraft.getWindow(), sEvent, sPosX, sPosY, 10 + minecraft.font.lineHeight);

                if (!sForgeEvent.isCanceled()) {
                    drawBar(graphics, sPosX, sPosY, sEvent);

                    if (tourniqueted$isEvent(sEvent, Entities.UTTERFLY)) {
                        int sRenderDistance = minecraft.options.renderDistance().get();
                        int sSimulationDistance = minecraft.options.simulationDistance().get();
                        int sPackedDistance = sRenderDistance <= 0 || sSimulationDistance <= 0 ? 250 : sRenderDistance * sRenderDistance + sSimulationDistance * sSimulationDistance;

                        List<Utterfly> sUtterflies = minecraft.level.getEntitiesOfClass(Utterfly.class, new AABB(minecraft.player.getOnPos()).inflate(sPackedDistance));

                        for (Utterfly sUtterfly : sUtterflies) {
                            switch (sUtterfly.getPhase()) {
                                case 1 -> {
                                    sTextColour = Colours.hexToInt("FFE800");
                                    sType = sCreator.apply("green");
                                }
                                case 2 -> {
                                    sTextColour = Colours.hexToInt("FF8300");
                                    sType = sCreator.apply("orange");
                                }
                                case 3, 4 -> {
                                    sTextColour = Colours.hexToInt("FF0000");
                                    sType = sCreator.apply("red");
                                }
                                default -> tourniqueted$LOGGER.warnf("Unknown phase: []", sUtterfly.getPhase());
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
    private <T extends Entity> boolean tourniqueted$isEvent(LerpingBossEvent event, Supplier<EntityType<T>> type)
    {
        DeferredHolder<EntityType<?>, EntityType<?>> holder = Sneaky.cast(type);

        String bossEvent = event.getName().getString();
        String entityType = Strings.i18nt(holder.getId().getPath());

        return bossEvent.equals(entityType);
    }
}
