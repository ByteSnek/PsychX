package xyz.snaker.tq.client.render.icon;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.client.render.CyclicalIconRenderer;
import xyz.snaker.snakerlib.math.Maths;
import xyz.snaker.tq.level.entity.boss.AntiCosmo;
import xyz.snaker.tq.level.entity.boss.Utterfly;
import xyz.snaker.tq.level.entity.creature.Flutterfly;
import xyz.snaker.tq.level.entity.creature.Frolicker;
import xyz.snaker.tq.level.entity.mob.*;
import xyz.snaker.tq.rego.Entities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

/**
 * Created by SnakerBone on 27/05/2023
 **/
public class MobTabIconRenderer extends CyclicalIconRenderer
{
    private static LivingEntity entityToRender;

    public MobTabIconRenderer()
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(@NotNull ItemStack itemStack, @NotNull ItemDisplayContext type, @NotNull PoseStack stack, @NotNull MultiBufferSource buf, int light, int overlay)
    {
        MouseHandler mouseHelper = Minecraft.getInstance().mouseHandler;
        ClientLevel level = Minecraft.getInstance().level;

        if (level != null) {
            LivingEntity[] entities =
                    {
                            new Cosmo(Entities.COSMO.get(), level),
                            new Flare(Entities.FLARE.get(), level),
                            new Snipe(Entities.SNIPE.get(), level),
                            new CosmicCreeper(Entities.COSMIC_CREEPER.get(), level),
                            new Utterfly(Entities.UTTERFLY.get(), level),
                            new Flutterfly(Entities.FLUTTERFLY.get(), level),
                            new Frolicker(Entities.FROLICKER.get(), level),
                            new AntiCosmo(Entities.ANTI_COSMO.get(), level),
                            new EerieCretin(Entities.EERIE_CRETIN.get(), level),
                            new Leet(Entities.LEET.get(), level)
                    };

            float mouseX = (float) ((mouseHelper.xpos() * Minecraft.getInstance().getWindow().getGuiScaledWidth()) / Minecraft.getInstance().getWindow().getScreenWidth());
            float mouseY = (float) ((mouseHelper.ypos() * Minecraft.getInstance().getWindow().getGuiScaledHeight()) / Minecraft.getInstance().getWindow().getScreenHeight());
            float scale = 0.5F;

            int tickCount = (int) SnakerLib.getClientTickCount();
            int index = ((tickCount + 60) / 120) % entities.length;

            /*
                Index values
                ------------
                0 = Cosmo
                1 = Flare
                2 = Snipe
                3 = Cosmic Creeper
                4 = Utterfly
                5 = Flutterfly
                6 = Frolicker
                7 = Anti Cosmo
                8 = Eerie Cretin
                9 = Leet
             */

            switch (index) {
                case 2, 3 -> scale = 0.7F;
                case 4 -> {
                    scale = 0.25F;
                    stack.translate(0F, 0.3F, 0F);
                }
                case 5 -> scale = 1.05F;
                case 6 -> {
                    scale = 1.05F;
                    stack.translate(0F, 0.3F, 0F);
                }
                case 9 -> {
                    scale = 0.125F;
                    stack.translate(0F, 0.3F, 0F);
                }
            }

            setEntityToRender(entities[index]);

            stack.translate(0.5F, 0, 0);
            stack.mulPose(Axis.XP.rotationDegrees(180));
            stack.mulPose(Axis.YP.rotationDegrees(180));

            if (type != ItemDisplayContext.GUI) {
                mouseX = 0;
                mouseY = 0;
            }

            Pig fallback = new Pig(EntityType.PIG, level);
            LivingEntity entity = entityToRender == null ? fallback : entityToRender;

            try {
                if (!entity.equals(fallback)) {
                    renderEntityOnScreen(stack, scale, 0, -45, 0, mouseX, mouseY, entity);
                } else {
                    SnakerLib.LOGGER.error("Could not render mob tab icon! Ignoring and rendering fallback");
                    renderEntityOnScreen(stack, scale, 0, -45, 0, mouseX, mouseY, entity);
                }
            } catch (Exception e) {
                if (notifyError) {
                    String errorMessage = e.getMessage();
                    String className = entity.getClass().getSimpleName();
                    SnakerLib.LOGGER.warnf("Could not render %s: %s", className, errorMessage);
                    notifyError = false;
                }
            }
        }
    }

    public static void setEntityToRender(LivingEntity entity)
    {
        entityToRender = entity;
    }

    public static void renderEntityOnScreen(PoseStack stack, float scale, float xRot, float yRot, float zRot, float mouseX, float mouseY, LivingEntity entity)
    {
        float mX = Maths.atan(-mouseX / 40);
        float mY = Maths.atan(mouseY / 40);

        stack.scale(scale, scale, scale);

        entity.setOnGround(false);

        Quaternionf zp = Axis.ZP.rotationDegrees(180), xp;

        float partialTick = Minecraft.getInstance().getFrameTime();
        float renderTick = Minecraft.getInstance().isPaused() ? 0 : partialTick;
        int playerTick = Minecraft.getInstance().player == null ? 0 : Minecraft.getInstance().player.tickCount;
        float yaw = mX * 45;

        entity.setYRot(yaw);
        entity.tickCount = playerTick;

        entity.yBodyRot = yaw;
        entity.yBodyRotO = yaw;
        entity.yHeadRot = yaw;
        entity.yHeadRotO = yaw;

        xp = Axis.XP.rotationDegrees(mY * 20);
        zp.mul(xp);

        stack.mulPose(zp);

        stack.mulPose(Axis.XP.rotationDegrees(-xRot));
        stack.mulPose(Axis.YP.rotationDegrees(yRot));
        stack.mulPose(Axis.ZP.rotationDegrees(zRot));

        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();

        xp.conjugate();

        dispatcher.overrideCameraOrientation(xp);
        dispatcher.setRenderShadow(false);

        MultiBufferSource.BufferSource source = Minecraft.getInstance().renderBuffers().bufferSource();

        dispatcher.render(entity, 0, 0, 0, 0, renderTick, stack, source, 15728880);
        source.endBatch();
        dispatcher.setRenderShadow(true);

        entity.setYRot(0);
        entity.setXRot(0);

        entity.yBodyRot = 0;
        entity.yHeadRotO = 0;
        entity.yHeadRot = 0;

        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
    }
}
