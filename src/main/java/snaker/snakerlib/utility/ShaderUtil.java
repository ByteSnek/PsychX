package snaker.snakerlib.utility;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.joml.Matrix3d;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * Created by SnakerBone on 28/04/2023
 **/
public class ShaderUtil
{
    private static final String HASH = "#";

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