package xyz.snaker.snakerlib.client.model.shape.cube;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.internal.AsynchronousHashMap;
import xyz.snaker.snakerlib.internal.Buffer;
import xyz.snaker.snakerlib.utility.tools.StringStuff;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

/**
 * Created by SnakerBone on 2/08/2023
 **/
public class CubeListBuffer
{
    private final Buffer<CubeListBuilder> buffer;
    private final Map<CubeListBuilder, String> names;
    private final Map<CubeListBuilder, PartPose> poses;
    private final PartDefinition base;

    private CubeListBuffer(MeshDefinition mesh)
    {
        this.buffer = new Buffer<>();
        this.names = new AsynchronousHashMap<>();
        this.poses = new AsynchronousHashMap<>();
        this.base = mesh.getRoot();
    }

    public static CubeListBuffer create(MeshDefinition mesh)
    {
        return new CubeListBuffer(mesh);
    }

    public CubeListBuffer add(CubeListBuilder builder, String name, PartPose pose)
    {
        if (addToBuffer(builder, name, pose)) {
            return this;
        } else {
            throw new RuntimeException("Invalid buffer input(s)");
        }
    }

    public CubeListBuilder get(int index)
    {
        return buffer.get(index);
    }

    public List<CubeDefinition> getCubesAt(int index)
    {
        return buffer.get(index).getCubes();
    }

    public CubeDefinition getCubeAt(int bufferIndex, int cubeIndex)
    {
        return buffer.get(bufferIndex).getCubes().get(cubeIndex);
    }

    private boolean addToBuffer(CubeListBuilder builder, String name, PartPose pose)
    {
        if (builder == null || StringStuff.isValidString(name, true) || pose == null) {
            return false;
        } else {
            names.put(builder, name);
            poses.put(builder, pose);
            return buffer.add(builder);
        }
    }

    public boolean applyChildrenInBuffer()
    {
        if (buffer.isEmpty()) {
            SnakerLib.LOGGER.warn("Nothing in buffer");
            return false;
        } else {
            for (CubeListBuilder builder : buffer) {
                base.addOrReplaceChild(names.get(builder), builder, poses.get(builder));
            }
            return true;
        }
    }

    @Override
    public String toString()
    {
        return String.format("CubeListBuilders: %s, Children: %s, Poses: %s", Arrays.toString(buffer.toArray()), Arrays.toString(names.values().toArray()), Arrays.toString(poses.values().toArray()));
    }
}
