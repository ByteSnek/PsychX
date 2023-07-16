package snaker.snakerlib.utility;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.level.Level;

/**
 * Created by SnakerBone on 11/05/2023
 **/
public class ParticleUtil
{
    public static void addParticleDirectly(Level level, Particle particle)
    {
        if (level instanceof ClientLevel) {
            Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
            if (camera.isInitialized()) {
                Minecraft.getInstance().particleEngine.add(particle);
            }
        }
    }
}
