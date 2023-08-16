package xyz.snaker.snakerlib.client.shader;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import xyz.snaker.snakerlib.SnakerLib;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ChainedJsonException;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.client.event.RegisterShadersEvent;

import org.jetbrains.annotations.ApiStatus;
import org.joml.*;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.vertex.VertexFormat;

/**
 * Created by SnakerBone on 9/06/2023
 **/
public class Shader extends ShaderInstance
{
    /**
     * The list of runnables enqueued to be executed
     **/
    private final List<Runnable> tasks = new LinkedList<>();

    /**
     * Creates a new shader. Use {@link #create(ResourceProvider, ResourceLocation, VertexFormat)} to bypass exception handling
     *
     * @param provider       The resource provider. Usually obtained through {@link RegisterShadersEvent#getResourceProvider()}
     * @param shaderLocation The location of the shader JSON
     * @param format         The vertex format
     * @throws IOException          If the JSON file fails to load
     * @throws ChainedJsonException If the JSON file is invalid
     * @throws Exception            If anything else occurs
     **/
    public Shader(ResourceProvider provider, ResourceLocation shaderLocation, VertexFormat format) throws Exception
    {
        super(provider, shaderLocation, format);
    }

    /**
     * Creates a new shader without exception handling
     *
     * @param provider The resource provider. Usually obtained through {@link RegisterShadersEvent#getResourceProvider()}
     * @param shader   The location of the shader JSON
     * @param format   The vertex format
     * @return A new shader
     * @throws RuntimeException If anything unexpected occurs
     **/
    public static Shader create(ResourceProvider provider, ResourceLocation shader, VertexFormat format)
    {
        try {
            return new Shader(provider, shader, format);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Enqueue's a task. Used to apply shader uniforms at the correct time
     *
     * @param uniform The shader uniform
     * @param delay   The uniform's time delay
     * @param task    The task to execute
     **/
    public void enqueueTask(Uniform uniform, float delay, Runnable task)
    {
        setTime(uniform, delay);
        enqueueTask(task);
    }

    /**
     * Enqueue's a task. Used to apply shader uniforms at the correct time
     *
     * @param uniform The shader uniform
     * @param task    The task to execute
     **/
    public void enqueueTask(Uniform uniform, Runnable task)
    {
        setTime(uniform);
        enqueueTask(task);
    }

    /**
     * Enqueue's a task. Used to apply shader uniforms at the correct time
     *
     * @param task The task to execute
     **/
    public void enqueueTask(Runnable task)
    {
        this.tasks.add(task);
    }

    /**
     * Sets the time uniform to the specified value
     *
     * @param uniform The uniform
     * @param delay   The delay
     **/
    @ApiStatus.Experimental
    public void setTime(Uniform uniform, float delay)
    {
        uniform.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / delay);
    }

    @ApiStatus.Experimental
    public void setTime(Uniform uniform)
    {
        setTime(uniform, 20F);
    }

    // ==== Float ==== //

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param value   The value
     **/
    @ApiStatus.Experimental
    public void glUniform1f(Uniform uniform, float value)
    {
        glFloat(uniform, value);
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param floats  The value
     **/
    @ApiStatus.Experimental
    public void glUniform2f(Uniform uniform, float[] floats)
    {
        glFloat(uniform, floats[0], floats[1]);
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param floats  The value
     **/
    @ApiStatus.Experimental
    public void glUniform3f(Uniform uniform, float[] floats)
    {
        glFloat(uniform, floats[0], floats[1], floats[2]);
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param floats  The value
     **/
    @ApiStatus.Experimental

    public void glUnifom4f(Uniform uniform, float[] floats)
    {
        glFloat(uniform, floats[0], floats[1], floats[2], floats[3]);
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param floats  The value
     **/
    @ApiStatus.Experimental
    public void glUniform2f(Uniform uniform, Vector2f floats)
    {
        glFloat(uniform, floats.x(), floats.y());
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param floats  The value
     **/
    @ApiStatus.Experimental
    public void glUniform3f(Uniform uniform, Vector3f floats)
    {
        glFloat(uniform, floats.x(), floats.y(), floats.z());
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param floats  The value
     **/
    @ApiStatus.Experimental
    public void glUnifom4f(Uniform uniform, Vector4f floats)
    {
        glFloat(uniform, floats.x(), floats.y(), floats.z(), floats.w());
    }

    // ==== Double ==== //

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param doubles The value
     **/
    @ApiStatus.Experimental
    public void glUniform2d(Uniform uniform, double[] doubles)
    {
        glDouble(uniform, doubles[0], doubles[1]);
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param doubles The value
     **/
    @ApiStatus.Experimental
    public void glUniform3d(Uniform uniform, double[] doubles)
    {
        glDouble(uniform, doubles[0], doubles[1], doubles[2]);
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param floats  The value
     **/
    @ApiStatus.Experimental
    public void glUnifom4d(Uniform uniform, double[] floats)
    {
        glDouble(uniform, floats[0], floats[1], floats[2], floats[3]);
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param doubles The value
     **/
    @ApiStatus.Experimental
    public void glUniform2d(Uniform uniform, Vector2d doubles)
    {
        glDouble(uniform, doubles.x(), doubles.y());
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param doubles The value
     **/
    @ApiStatus.Experimental
    public void glUniform3d(Uniform uniform, Vector3d doubles)
    {
        glDouble(uniform, doubles.x(), doubles.y(), doubles.z());
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param doubles The value
     **/
    @ApiStatus.Experimental
    public void glUnifom4d(Uniform uniform, Vector4d doubles)
    {
        glDouble(uniform, doubles.x(), doubles.y(), doubles.z(), doubles.w());
    }

    // ==== Integer ==== //

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param ints    The value
     **/
    @ApiStatus.Experimental

    public void glUniform2i(Uniform uniform, int[] ints)
    {
        glInteger(uniform, ints[0], ints[1]);
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param ints    The value
     **/
    @ApiStatus.Experimental
    public void glUniform3i(Uniform uniform, int[] ints)
    {
        glInteger(uniform, ints[0], ints[1], ints[2]);
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param ints    The value
     **/
    @ApiStatus.Experimental
    public void glUnifom4i(Uniform uniform, int[] ints)
    {
        glInteger(uniform, ints[0], ints[1], ints[2], ints[3]);
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param ints    The value
     **/
    @ApiStatus.Experimental
    public void glUniform2i(Uniform uniform, Vector2i ints)
    {
        glInteger(uniform, ints.x(), ints.y());
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param ints    The value
     **/
    @ApiStatus.Experimental
    public void glUniform3i(Uniform uniform, Vector3i ints)
    {
        glInteger(uniform, ints.x(), ints.y(), ints.z());
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param ints    The value
     **/
    @ApiStatus.Experimental
    public void glUnifom4i(Uniform uniform, Vector4i ints)
    {
        glInteger(uniform, ints.x(), ints.y(), ints.z(), ints.w());
    }

    // ==== Boolean ==== //

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param value   The value
     **/
    @ApiStatus.Experimental
    public void glUniform1b(Uniform uniform, boolean value)
    {
        glBoolean(uniform, value);
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform  The uniform
     * @param booleans The value
     **/
    @ApiStatus.Experimental
    public void glUniform2b(Uniform uniform, boolean[] booleans)
    {
        glBoolean(uniform, booleans[0], booleans[1]);
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform  The uniform
     * @param booleans The value
     **/
    @ApiStatus.Experimental
    public void glUniform3b(Uniform uniform, boolean[] booleans)
    {
        glBoolean(uniform, booleans[0], booleans[1], booleans[2]);
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform  The uniform
     * @param booleans The value
     **/
    @ApiStatus.Experimental
    public void glUniform4b(Uniform uniform, boolean[] booleans)
    {
        glBoolean(uniform, booleans[0], booleans[1], booleans[2], booleans[3]);
    }

    // ==== Matrix Float ==== //

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param matrix  The value
     **/
    @ApiStatus.Experimental
    public void glMatrix2f(Uniform uniform, Matrix2f matrix)
    {
        glFloat(uniform,
                matrix.m00(), matrix.m01(),
                matrix.m10(), matrix.m11()
        );
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param matrix  The value
     **/
    @ApiStatus.Experimental
    public void glMatrix3f(Uniform uniform, Matrix3f matrix)
    {
        glFloat(uniform,
                matrix.m00(), matrix.m01(), matrix.m02(),
                matrix.m10(), matrix.m11(), matrix.m12(),
                matrix.m20(), matrix.m21(), matrix.m22()
        );
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param matrix  The value
     **/
    @ApiStatus.Experimental
    public void glMatrix4f(Uniform uniform, Matrix4f matrix)
    {
        glFloat(uniform,
                matrix.m00(), matrix.m01(), matrix.m02(), matrix.m03(),
                matrix.m10(), matrix.m11(), matrix.m12(), matrix.m13(),
                matrix.m20(), matrix.m21(), matrix.m22(), matrix.m23(),
                matrix.m30(), matrix.m31(), matrix.m32(), matrix.m33()
        );
    }

    // ==== Matrix Double ==== //

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param matrix  The value
     **/
    @ApiStatus.Experimental
    public void glMatrix2d(Uniform uniform, Matrix2d matrix)
    {
        glDouble(uniform,
                matrix.m00(), matrix.m01(),
                matrix.m10(), matrix.m11()
        );
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param matrix  The value
     **/
    @ApiStatus.Experimental
    public void glMatrix3d(Uniform uniform, Matrix3d matrix)
    {
        glDouble(uniform,
                matrix.m00(), matrix.m01(), matrix.m02(),
                matrix.m10(), matrix.m11(), matrix.m12(),
                matrix.m20(), matrix.m21(), matrix.m22()
        );
    }

    /**
     * Sets the shader uniform to the specified value
     *
     * @param uniform The uniform
     * @param matrix  The value
     **/
    @ApiStatus.Experimental
    public void glMatrix4d(Uniform uniform, Matrix4d matrix)
    {
        glDouble(uniform,
                matrix.m00(), matrix.m01(), matrix.m02(), matrix.m03(),
                matrix.m10(), matrix.m11(), matrix.m12(), matrix.m13(),
                matrix.m20(), matrix.m21(), matrix.m22(), matrix.m23(),
                matrix.m30(), matrix.m31(), matrix.m32(), matrix.m33()
        );
    }

    private void glFloat(Uniform uniform, float... floats)
    {
        for (float f : floats) {
            uniform.set(f);
        }
    }

    private void glDouble(Uniform uniform, double... doubles)
    {
        for (double d : doubles) {
            uniform.set((float) d);
        }
    }

    private void glInteger(Uniform uniform, int... ints)
    {
        for (int i : ints) {
            uniform.set(i);
        }
    }

    private void glBoolean(Uniform uniform, boolean... booleans)
    {
        for (boolean b : booleans) {
            uniform.set(b ? 1 : 0);
        }
    }

    @Override
    public void apply()
    {
        for (Runnable task : tasks) {
            task.run();
        }
        super.apply();
    }
}