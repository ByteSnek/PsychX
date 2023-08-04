package snaker.snakerlib.utility;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.joml.*;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Created by SnakerBone on 28/04/2023
 **/
public class RenderStuff
{
    private static final String HASH = "#";
    public static final Vector4f STANDARD_COLOURS = new Vector4f(1, 1, 1, 1);
    public static final Vector2i STANDARD_PACKED_COMPONENTS = new Vector2i(LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
    public static final BiFunction<Integer, LivingEntity, Vector2i> ENTITY_PACKED_COMPONENTS = (packedLight, entity) -> new Vector2i(packedLight, RenderStuff.packOverlay(entity));

    public static <T extends LivingEntity, M extends EntityModel<T>> void renderLayer(RenderLayer<T, M> parent, PoseStack stack, VertexConsumer consumer, Vector2i packedComponents, Vector4f rgba)
    {
        parent.getParentModel().renderToBuffer(stack, consumer, packedComponents.x(), packedComponents.y(), rgba.x(), rgba.y(), rgba.z(), rgba.w());
    }

    public static <T extends LivingEntity, M extends EntityModel<T>> void renderLayer(RenderLayer<T, M> parent, PoseStack stack, VertexConsumer consumer, T entity, int packedLight)
    {
        renderLayer(parent, stack, consumer, ENTITY_PACKED_COMPONENTS.apply(packedLight, entity), STANDARD_COLOURS);
    }

    public static <T extends LivingEntity, M extends EntityModel<T>> void renderLayer(RenderLayer<T, M> parent, PoseStack stack, @Nullable MultiBufferSource source, RenderType type, T entity, int packedLight)
    {
        MultiBufferSource bufferSource = Objects.requireNonNullElseGet(source, Minecraft.getInstance().renderBuffers()::bufferSource);
        renderLayer(parent, stack, bufferSource.getBuffer(type), ENTITY_PACKED_COMPONENTS.apply(packedLight, entity), STANDARD_COLOURS);
    }

    public static int packLightLevel(Level level, BlockPos pos)
    {
        return level != null ? LightTexture.pack(level.getBrightness(LightLayer.BLOCK, pos), level.getBrightness(LightLayer.SKY, pos)) : 0;
    }

    public static <T extends LivingEntity> int packOverlay(T entity, int u)
    {
        return LivingEntityRenderer.getOverlayCoords(entity, u);
    }

    public static <T extends LivingEntity> int packOverlay(T entity)
    {
        return packOverlay(entity, 0);
    }

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

    public static float[] hexToVec3(String hexCode)
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

    public static float[] hexToVec4(String hexCode)
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
}