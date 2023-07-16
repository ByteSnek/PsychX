package snaker.snakerlib.utility;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.awt.*;

/**
 * Created by SnakerBone on 28/04/2023
 **/
public class ShaderUtil
{
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

    public static Vector3f hexToVec3f(String hexCode)
    {
        if (!hexCode.startsWith("#")) {
            hexCode = "#" + hexCode;
        }
        Color colour = Color.decode(hexCode);
        float x = colour.getRed() / 255F;
        float y = colour.getGreen() / 255F;
        float z = colour.getBlue() / 255F;
        return new Vector3f(x, y, z);
    }

    public static Vector4f hexToVec4f(String hexCode)
    {
        if (!hexCode.startsWith("#")) {
            hexCode = "#" + hexCode;
        }
        Color colour = Color.decode(hexCode);
        float x = colour.getRed() / 255F;
        float y = colour.getGreen() / 255F;
        float z = colour.getBlue() / 255F;
        float w = colour.getAlpha();
        return new Vector4f(x, y, z, w);
    }

    public static float[] hexToVec3(String hexCode)
    {
        if (!hexCode.startsWith("#")) {
            hexCode = "#" + hexCode;
        }
        Color colour = Color.decode(hexCode);
        float[] vec = new float[3];
        vec[0] = colour.getRed() / 255F;
        vec[1] = colour.getGreen() / 255F;
        vec[2] = colour.getBlue() / 255F;
        return vec;
    }

    public static float[] hexToVec4(String hexCode)
    {
        if (!hexCode.startsWith("#")) {
            hexCode = "#" + hexCode;
        }
        Color colour = Color.decode(hexCode);
        float[] vec = new float[4];
        vec[0] = colour.getRed() / 255F;
        vec[1] = colour.getGreen() / 255F;
        vec[2] = colour.getBlue() / 255F;
        vec[3] = colour.getAlpha() / 255F;
        return vec;
    }
}