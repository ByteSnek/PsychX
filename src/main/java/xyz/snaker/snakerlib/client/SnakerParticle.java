package xyz.snaker.snakerlib.client;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Created by SnakerBone on 11/05/2023
 **/
@Deprecated // Unused since 1.16.5 -> 1.18.2 port. Might nuke later
public abstract class SnakerParticle<T extends BlockEntity> extends Particle
{
    protected final T blockEntity;
    protected int aliveFor = 40;
    public boolean shouldRender = true;

    public SnakerParticle(ClientLevel level, T blockEntity)
    {
        super(level, blockEntity.getBlockPos().getX() + 0.5, blockEntity.getBlockPos().getY() + 0.5, blockEntity.getBlockPos().getZ() + 0.5);
        this.blockEntity = blockEntity;
    }

    @Override
    public void tick()
    {

    }

    protected Vector3f[] getRenderVectors(Camera camera, float viewX, float viewY, float viewZ, float scale)
    {
        Quaternionf quaternion = camera.rotation();
        Vector3f[] renderVector = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        for (int amount = 0; amount < 4; amount++) {
            Vector3f vector3f = renderVector[amount];
            vector3f.rotate(quaternion);
            vector3f.mul(scale);
            vector3f.add(viewX, viewY, viewZ);
        }
        return renderVector;
    }
}
