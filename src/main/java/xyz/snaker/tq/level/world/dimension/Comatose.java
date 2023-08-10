package xyz.snaker.tq.level.world.dimension;

import java.util.function.Function;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

/**
 * Created by SnakerBone on 29/07/2023
 **/
public class Comatose
{
    public static Teleporter getTeleporter()
    {
        return Teleporter.INSTANCE;
    }

    public static class Teleporter implements ITeleporter
    {
        private static final Teleporter INSTANCE = new Teleporter(false);
        private final boolean teleportSound;

        private Teleporter(boolean teleportSound)
        {
            this.teleportSound = teleportSound;
        }

        @Override
        public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity)
        {
            PortalInfo info = new PortalInfo(entity.position(), Vec3.ZERO, entity.getYRot(), entity.getXRot());
            if (entity instanceof ServerPlayer player) {
                player.setServerLevel(destWorld);
                destWorld.addDuringPortalTeleport(player);
                entity.setYRot(info.yRot % 360);
                entity.setXRot(info.xRot % 360);
                entity.moveTo(info.pos.x, info.pos.y, info.pos.z);
                return entity;
            } else {
                Entity other = entity.getType().create(destWorld);
                if (other != null) {
                    other.restoreFrom(entity);
                    other.moveTo(info.pos.x, info.pos.y, info.pos.z, info.yRot, other.getXRot());
                    other.setDeltaMovement(info.speed);
                    destWorld.addDuringTeleport(other);
                }
                return other;
            }
        }

        @Override
        public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld)
        {
            return teleportSound;
        }
    }
}
