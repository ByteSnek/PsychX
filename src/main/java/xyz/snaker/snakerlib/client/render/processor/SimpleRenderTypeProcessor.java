package xyz.snaker.snakerlib.client.render.processor;

import java.util.Objects;

import xyz.snaker.snakerlib.SnakerLib;

import net.minecraft.client.renderer.RenderType;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;

/**
 * Created by SnakerBone on 12/08/2023
 **/
public interface SimpleRenderTypeProcessor extends RenderTypeProcessor
{
    @Override
    default RenderType create(@org.jetbrains.annotations.Nullable String name, Pair<VertexFormat, RenderType.CompositeState> pair)
    {
        return RenderType.create(Objects.requireNonNullElse(name, SnakerLib.placeholderWithId()), pair.getFirst(), VertexFormat.Mode.QUADS, 256, pair.getSecond());
    }
}
