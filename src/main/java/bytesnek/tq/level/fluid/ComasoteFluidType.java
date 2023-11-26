package bytesnek.tq.level.fluid;

import javax.annotation.Nullable;
import java.util.function.Consumer;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import bytesnek.snakerlib.utility.fluid.FluidTypeProperties;
import bytesnek.tq.Tourniqueted;

/**
 * Created by SnakerBone on 26/09/2023
 **/
public class ComasoteFluidType extends FluidType
{
    public ComasoteFluidType()
    {
        super(FluidTypeProperties.LAVA.apply("block.tq.comasote"));
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
    {
        consumer.accept(new IClientFluidTypeExtensions()
        {
            @Override
            public ResourceLocation getStillTexture()
            {
                return new ResourceLocation(Tourniqueted.MODID, "block/comasote_still");
            }

            @Override
            public ResourceLocation getFlowingTexture()
            {
                return new ResourceLocation(Tourniqueted.MODID, "block/comasote_flow");
            }

            @Override
            public @Nullable ResourceLocation getOverlayTexture()
            {
                return null;
            }

            @Override
            public int getTintColor()
            {
                return FastColor.ARGB32.color(255, 255, 0, 255);
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor)
            {
                return new Vector3f(1, 0, 1);
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape)
            {
                RenderSystem.setShaderFogStart(1);
                RenderSystem.setShaderFogEnd(6);
            }
        });
    }
}