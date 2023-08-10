package xyz.snaker.snakerlib.client.shader;

import java.util.function.Supplier;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

/**
 * Created by SnakerBone on 12/05/2023
 *
 * @deprecated use the better alternative {@link Shader}
 **/
@Deprecated // Pointless. Unused since 1.16.5
public class SimpleShader
{
    private final boolean enableBlend, depthMask;
    private final int shaderTexture;

    public SimpleShader(boolean enableBlend, boolean depthMask, int shaderTexture)
    {
        this.enableBlend = enableBlend;
        this.depthMask = depthMask;
        this.shaderTexture = shaderTexture;
    }

    public SimpleShader(boolean enableBlend, boolean depthMask)
    {
        this(enableBlend, depthMask, 0);
    }

    public void createStandardParticle(GlStateManager.SourceFactor blendSrcFactor, GlStateManager.DestFactor blendDestFactor, Supplier<ShaderInstance> primaryShader, Supplier<ShaderInstance> secondaryShader, ResourceLocation shaderTexLoc)
    {
        RenderSystem.depthMask(depthMask);
        if (enableBlend) {
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(blendSrcFactor, blendDestFactor);
        } else {
            RenderSystem.disableBlend();
        }
        RenderSystem.setShader(primaryShader);
        RenderSystem.setShader(secondaryShader);
        RenderSystem.setShaderTexture(shaderTexture, shaderTexLoc);
    }

    public void createStandardParticle(GlStateManager.SourceFactor blendSrcFactor, GlStateManager.DestFactor blendDestFactor, Supplier<ShaderInstance> shader, ResourceLocation shaderTexLoc)
    {
        createStandardParticle(blendSrcFactor, blendDestFactor, GameRenderer::getPositionColorTexLightmapShader, shader, shaderTexLoc);
    }
}
