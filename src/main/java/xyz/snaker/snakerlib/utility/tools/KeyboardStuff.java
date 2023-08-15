package xyz.snaker.snakerlib.utility.tools;

import net.minecraft.client.Minecraft;

import org.lwjgl.glfw.GLFW;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class KeyboardStuff
{
    /**
     * Checks if a key is being pressed
     *
     * @param key A {@link GLFW} printable key
     * @return True if the key is currently being pressed
     **/
    public static boolean isKeyDown(int key)
    {
        return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), key) == GLFW.GLFW_PRESS;
    }
}
