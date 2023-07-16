package snaker.snakerlib.internal;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraftforge.api.distmarker.Dist;

/**
 * Created by SnakerBone on 8/07/2023
 **/
@SuppressWarnings("JavadocReference")
public enum Accessibility
{
    /**
     * This object is available anywhere.
     **/
    ALL,

    /**
     * This object is not available anywhere.
     **/
    NONE,

    /**
     * This object is only available on {@link Dist#CLIENT}.
     **/
    CLIENT,

    /**
     * This object is only available on {@link Dist#DEDICATED_SERVER}.
     **/
    SERVER,

    /**
     * This object is only available on {@link RenderSystem#renderThread}.
     **/
    RENDER,

    /**
     * This object is only available on {@link RenderSystem#gameThread}.
     **/
    GAME
}
