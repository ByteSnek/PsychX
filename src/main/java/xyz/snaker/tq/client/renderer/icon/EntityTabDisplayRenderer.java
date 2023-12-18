package xyz.snaker.tq.client.renderer.icon;

import java.util.List;

import xyz.snaker.hiss.logger.Logger;
import xyz.snaker.hiss.logger.Loggers;
import xyz.snaker.hiss.math.Maths;
import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.client.render.CyclicalIconRenderer;
import xyz.snaker.tq.level.display.tab.EntityTabDisplay;

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

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

/**
 * Created by SnakerBone on 27/05/2023
 **/
public class EntityTabDisplayRenderer extends CyclicalIconRenderer
{
    static final Logger LOGGER = Loggers.getLogger();

    private final EntityTabDisplay instance;

    public EntityTabDisplayRenderer(EntityTabDisplay instance)
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.instance = instance;
    }

    @Override
    public void renderByItem(@NotNull ItemStack itemStack, @NotNull ItemDisplayContext context, @NotNull PoseStack stack, @NotNull MultiBufferSource buf, int light, int overlay)
    {
        Minecraft minecraft = Minecraft.getInstance();
        MouseHandler handler = minecraft.mouseHandler;
        ClientLevel level = minecraft.level;
        Window window = minecraft.getWindow();

        double handlerX = handler.xpos();
        double handlerY = handler.ypos();

        int scaledWidth = window.getGuiScaledWidth();
        int scaledHeight = window.getGuiScaledHeight();

        int screenWidth = window.getScreenWidth();
        int screenHeight = window.getScreenHeight();

        if (level != null) {
            List<LivingEntity> entities = instance.getDisplays(level);

            float width = (float) handlerX * scaledWidth;
            float height = (float) handlerY * scaledHeight;

            float mouseX = width / screenWidth;
            float mouseY = height / screenHeight;

            float entityScale = 0.5F;

            int tickCount = (int) SnakerLib.getClientTickCount();

            int tickOffset = tickCount + 60;
            int tickDelay = tickOffset / 120;

            int index = tickDelay % entities.size();

            switch (index) {
                case 0 -> entityScale = 0.75F;
                case 1 -> entityScale = 1;
                case 2 -> entityScale = 0.55F;
                case 3 -> entityScale = 0.535F;
                case 4 -> entityScale = 1.15F;
                case 5 -> entityScale = 1.125F;
                case 6 -> entityScale = 0.65F;
                case 7 -> entityScale = 0.25F;
            }

            stack.translate(0.5F, index == 5 || index == 7 ? 0.35 : 0, 0);
            stack.mulPose(Axis.XP.rotationDegrees(180));
            stack.mulPose(Axis.YP.rotationDegrees(180));

            if (context != ItemDisplayContext.GUI) {
                mouseX = 0;
                mouseY = 0;
            }

            LivingEntity entityToRender = entities.get(index);
            Pig fallback = new Pig(EntityType.PIG, level);
            LivingEntity entity = entityToRender == null ? fallback : entityToRender;

            try {
                if (!entity.equals(fallback)) {
                    renderEntityOnScreen(stack, entityScale, 0, -45, 0, mouseX, mouseY, entity);
                } else {
                    LOGGER.warn("Could not render mob tab icon");
                    renderEntityOnScreen(stack, entityScale, 0, -45, 0, mouseX, mouseY, entity);
                }
            } catch (Exception e) {
                String errorMessage = e.getMessage();
                String className = entity.getClass().getSimpleName();
                LOGGER.errorf("Error rendering []: []", className, errorMessage);
            }
        }
    }

    private void renderEntityOnScreen(PoseStack stack, float scale, float xRot, float yRot, float zRot, float mouseX, float mouseY, LivingEntity entity)
    {
        float mX = Maths.atan(-mouseX / 40);
        float mY = Maths.atan(mouseY / 40);

        stack.scale(scale, scale, scale);
        entity.setOnGround(false);

        Quaternionf zp = Axis.ZP.rotationDegrees(180), xp;

        int playerTick = Minecraft.getInstance().player == null ? 0 : Minecraft.getInstance().player.tickCount;

        float partialTick = Minecraft.getInstance().getFrameTime();
        float renderTick = Minecraft.getInstance().isPaused() ? 0 : partialTick;
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
