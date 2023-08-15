package xyz.snaker.snakerlib.utility.tools;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import javax.annotation.Nullable;

import xyz.snaker.snakerlib.client.render.processor.SimpleRenderTypeProcessor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

import org.joml.*;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;

/**
 * Created by SnakerBone on 28/04/2023
 **/
public class RenderStuff
{
    private static final String HASH = "#";

    /**
     * Standard RGBA colour with the value of 1 (white)
     **/
    public static final Vector4f STANDARD_COLOURS = new Vector4f(1);

    /**
     * Standard ambient lighting texture (15728880) and overlay texture (655360)
     **/
    public static final Vector2i STANDARD_PACKED_COMPONENTS = new Vector2i(LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);

    /**
     * A bi function that returns the default ambient lighting texture and the overlay texture dependant on the entity's UV coordinates
     **/
    public static final BiFunction<Integer, LivingEntity, Vector2i> ENTITY_PACKED_COMPONENTS = (packedLight, entity) -> new Vector2i(packedLight, RenderStuff.packOverlay(entity));

    /**
     * Adds a render layer to an entity
     *
     * @param parent           The render layer parent. Usually an entity renderer class that extends RenderLayer
     * @param stack            The pose stack
     * @param consumer         The builder holding the render type
     * @param packedComponents The entity's ambient lighting settings
     * @param rgba             The entity's RGBA colours
     **/
    public static <T extends LivingEntity, M extends EntityModel<T>> void renderLayer(RenderLayer<T, M> parent, PoseStack stack, VertexConsumer consumer, Vector2i packedComponents, Vector4f rgba)
    {
        parent.getParentModel().renderToBuffer(stack, consumer, packedComponents.x(), packedComponents.y(), rgba.x(), rgba.y(), rgba.z(), rgba.w());
    }

    /**
     * Adds a render layer to an entity
     *
     * @param parent      The render layer parent. Usually an entity renderer class that extends RenderLayer
     * @param stack       The pose stack
     * @param consumer    The builder holding the render type
     * @param entity      The entity to retrieve the UV coords from
     * @param packedLight The entity's ambient lighting texture
     **/
    public static <T extends LivingEntity, M extends EntityModel<T>> void renderLayer(RenderLayer<T, M> parent, PoseStack stack, VertexConsumer consumer, T entity, int packedLight)
    {
        renderLayer(parent, stack, consumer, ENTITY_PACKED_COMPONENTS.apply(packedLight, entity), STANDARD_COLOURS);
    }

    /**
     * Adds a render layer to an entity
     *
     * @param parent      The render layer parent. Usually an entity renderer class that extends RenderLayer
     * @param stack       The pose stack
     * @param source      The multi buffer source
     * @param type        The render type
     * @param entity      The entity to retrieve the UV coords from
     * @param packedLight The entity's ambient lighting texture
     **/
    public static <T extends LivingEntity, M extends EntityModel<T>> void renderLayer(RenderLayer<T, M> parent, PoseStack stack, @Nullable MultiBufferSource source, RenderType type, T entity, int packedLight)
    {
        MultiBufferSource bufferSource = Objects.requireNonNullElseGet(source, Minecraft.getInstance().renderBuffers()::bufferSource);
        renderLayer(parent, stack, bufferSource.getBuffer(type), ENTITY_PACKED_COMPONENTS.apply(packedLight, entity), STANDARD_COLOURS);
    }

    /**
     * Packs the light texture from the light level at the given block position
     *
     * @param level The level
     * @param pos   The block pos
     * @return The light texture
     **/
    public static int packLightLevel(Level level, BlockPos pos)
    {
        return level != null ? LightTexture.pack(level.getBrightness(LightLayer.BLOCK, pos), level.getBrightness(LightLayer.SKY, pos)) : 0;
    }

    /**
     * Packs the light texture from an entity's UV coords
     *
     * @param entity The entity
     * @param u      The U offset
     * @return The overlay texture
     **/
    public static <T extends LivingEntity> int packOverlay(T entity, int u)
    {
        return LivingEntityRenderer.getOverlayCoords(entity, u);
    }

    /**
     * Packs the light texture from an entity's UV coords
     *
     * @param entity The entity
     * @return The overlay texture
     **/
    public static <T extends LivingEntity> int packOverlay(T entity)
    {
        return packOverlay(entity, 0);
    }

    /**
     * Converts a hexidecimal colour code to a Matrix3d
     *
     * @param hexCode The hex code
     * @return The colour as a Matrix3d
     **/
    public static Matrix3d hexToMatrix3d(String hexCode)
    {
        hexCode = hexCode.startsWith(HASH) ? hexCode.substring(1) : hexCode;
        int value = Integer.parseInt(hexCode, 16);
        double x = (value >> 16) & 0xFF;
        double y = (value >> 8) & 0xFF;
        double z = value & 0xFF;
        x /= 255D;
        y /= 255D;
        z /= 255D;
        return new Matrix3d(x, 0.0, 0.0, 0.0, y, 0.0, 0.0, 0.0, z);
    }

    /**
     * Converts a hexidecimal colour code to a Vector3d
     *
     * @param hexCode The hex code
     * @return The colour as a Vector3d
     **/
    public static Vector3d hexToVec3d(String hexCode)
    {
        hexCode = hexCode.startsWith(HASH) ? hexCode.substring(1) : hexCode;
        int value = Integer.parseInt(hexCode, 16);
        double x = (value >> 16) & 0xFF;
        double y = (value >> 8) & 0xFF;
        double z = value & 0xFF;
        x /= 255D;
        y /= 255D;
        z /= 255D;
        return new Vector3d(x, y, z);
    }

    /**
     * Converts a hexidecimal colour code to a Vector3f
     *
     * @param hexCode The hex code
     * @return The colour as a Vector3f
     **/
    public static Vector3f hexToVec3f(String hexCode)
    {
        hexCode = hexCode.startsWith(HASH) ? hexCode.substring(1) : hexCode;
        int value = Integer.parseInt(hexCode, 16);
        float x = (value >> 16) & 0xFF;
        float y = (value >> 8) & 0xFF;
        float z = value & 0xFF;
        x /= 255F;
        y /= 255F;
        z /= 255F;
        return new Vector3f(x, y, z);
    }

    /**
     * Converts a hexidecimal colour code to a Vector4f
     *
     * @param hexCode The hex code
     * @return The colour as a Vector4f
     **/
    public static Vector4f hexToVec4f(String hexCode)
    {
        hexCode = hexCode.startsWith(HASH) ? hexCode.substring(1) : hexCode;
        int value = Integer.parseInt(hexCode, 16);
        float x = (value >> 16) & 0xFF;
        float y = (value >> 8) & 0xFF;
        float z = value & 0xFF;
        float w = (value >> 24) & 0xFF;
        x /= 255F;
        y /= 255F;
        z /= 255F;
        w /= 255F;
        return new Vector4f(x, y, z, w);
    }

    /**
     * Converts a hexidecimal colour code to a float array
     *
     * @param hexCode The hex code
     * @return The RGB as a float array
     **/
    public static float[] hexToRGB(String hexCode)
    {
        hexCode = hexCode.startsWith(HASH) ? hexCode.substring(1) : hexCode;
        int value = Integer.parseInt(hexCode, 16);
        float[] floats = new float[3];
        floats[0] = (value >> 16) & 0xFF;
        floats[1] = (value >> 8) & 0xFF;
        floats[2] = value & 0xFF;
        floats[0] /= 255F;
        floats[1] /= 255F;
        floats[2] /= 255F;
        return floats;
    }

    /**
     * Converts a hexidecimal colour code to a float array
     *
     * @param hexCode The hex code
     * @return The RGBA as a float array
     **/
    public static float[] hexToRGBA(String hexCode)
    {
        hexCode = hexCode.startsWith(HASH) ? hexCode.substring(1) : hexCode;
        int value = Integer.parseInt(hexCode, 16);
        float[] floats = new float[4];
        floats[0] = (value >> 16) & 0xFF;
        floats[1] = (value >> 8) & 0xFF;
        floats[2] = value & 0xFF;
        floats[3] = (value >> 24) & 0xFF;
        floats[0] /= 255F;
        floats[1] /= 255F;
        floats[2] /= 255F;
        floats[3] /= 255F;
        return floats;
    }

    public static SimpleRenderTypeProcessor createFreshProcessor()
    {
        return new SimpleRenderTypeProcessor()
        {
            @Override
            public RenderType.CompositeState normal(Supplier<ShaderInstance> shader)
            {
                return SimpleRenderTypeProcessor.super.normal(shader);
            }

            @Override
            public RenderType.CompositeState entity(Supplier<ShaderInstance> shader)
            {
                return SimpleRenderTypeProcessor.super.entity(shader);
            }

            @Override
            public RenderType.CompositeState translucent(Supplier<ShaderInstance> shader)
            {
                return SimpleRenderTypeProcessor.super.translucent(shader);
            }

            @Override
            public RenderType.CompositeState sampler(Supplier<ShaderInstance> shader, boolean blur, boolean mipmap, ResourceLocation... samplers)
            {
                return SimpleRenderTypeProcessor.super.sampler(shader, blur, mipmap, samplers);
            }

            @Override
            public RenderType.CompositeState sampler(Supplier<ShaderInstance> shader, ResourceLocation sampler, boolean blur, boolean mipmap)
            {
                return SimpleRenderTypeProcessor.super.sampler(shader, sampler, blur, mipmap);
            }

            @Override
            public RenderType create(@org.jetbrains.annotations.Nullable String name, Pair<VertexFormat, RenderType.CompositeState> pair)
            {
                return SimpleRenderTypeProcessor.super.create(name, pair);
            }
        };
    }
}