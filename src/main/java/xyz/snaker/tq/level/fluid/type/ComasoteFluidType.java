package xyz.snaker.tq.level.fluid.type;

import javax.annotation.Nullable;
import java.util.function.Consumer;

import xyz.snaker.snakerlib.resources.ResourceReference;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

/**
 * Created by SnakerBone on 26/09/2023
 **/
public class ComasoteFluidType extends FluidType
{
    public ComasoteFluidType(Properties properties)
    {
        super(properties);
    }

    @Override
    public @Nullable BlockPathTypes getBlockPathType(FluidState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, boolean canFluidLog)
    {
        return canFluidLog ? super.getBlockPathType(state, level, pos, mob, true) : null;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
    {
        consumer.accept(new IClientFluidTypeExtensions()
        {
            private static final ResourceLocation OVERLAY = new ResourceReference("textures/block/fluid/under_comasote.png");
            private static final ResourceLocation STILL = new ResourceReference("block/fluid/comasote_still");
            private static final ResourceLocation FLOWING = new ResourceReference("block/fluid/comasote_flow");

            @Override
            public ResourceLocation getStillTexture()
            {
                return STILL;
            }

            @Override
            public ResourceLocation getFlowingTexture()
            {
                return FLOWING;
            }

            @Override
            public @NotNull ResourceLocation getOverlayTexture()
            {
                return OVERLAY;
            }

            @Override
            public ResourceLocation getRenderOverlayTexture(Minecraft minecraft)
            {
                return OVERLAY;
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor)
            {
                return new Vector3f(0.2F, 0, 0.2F);
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape)
            {
                RenderSystem.setShaderFogStart(0);
                RenderSystem.setShaderFogEnd(1.2F);
            }
        });
    }
}
