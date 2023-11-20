package bytesnek.tq.level.fluid;

import java.util.function.Consumer;

import xyz.snaker.snakerlib.resources.ResourceReference;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
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
    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;
    private final ResourceLocation overlayTexture;

    private final int tintColor;
    private final Vector3f fogColor;

    public ComasoteFluidType(Properties properties)
    {
        super(properties);
        this.stillTexture = new ResourceReference("textures/block/fluid/comasote_still.png");
        this.flowingTexture = new ResourceReference("textures/block/fluid/comasote_flow.png");
        this.overlayTexture = new ResourceReference("textures/block/fluid/comasote_overlay.png");
        this.tintColor = FastColor.ARGB32.color(255, 116, 7, 168);
        this.fogColor = new Vector3f(0.2F, 0, 0.2F);
    }

    public ResourceLocation getStillTexture()
    {
        return stillTexture;
    }

    public ResourceLocation getFlowingTexture()
    {
        return flowingTexture;
    }

    public int getTintColor()
    {
        return tintColor;
    }

    public ResourceLocation getOverlayTexture()
    {
        return overlayTexture;
    }

    public Vector3f getFogColor()
    {
        return fogColor;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
    {
        consumer.accept(new IClientFluidTypeExtensions()
        {
            @Override
            public ResourceLocation getStillTexture()
            {
                return stillTexture;
            }

            @Override
            public ResourceLocation getFlowingTexture()
            {
                return flowingTexture;
            }

            @Override
            public @NotNull ResourceLocation getOverlayTexture()
            {
                return overlayTexture;
            }

            @Override
            public int getTintColor()
            {
                return tintColor;
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level,
                                                    int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor)
            {
                return fogColor;
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick,
                                        float nearDistance, float farDistance, FogShape shape)
            {
                RenderSystem.setShaderFogStart(1f);
                RenderSystem.setShaderFogEnd(6f);
            }
        });
    }
}