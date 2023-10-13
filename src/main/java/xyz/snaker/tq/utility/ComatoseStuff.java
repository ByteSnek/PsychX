package xyz.snaker.tq.utility;

import java.util.function.Function;

import xyz.snaker.tq.config.TqConfig;
import xyz.snaker.tq.level.world.dimension.Comatose;
import xyz.snaker.tq.rego.Levels;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Created by SnakerBone on 10/11/2023
 **/
public class ComatoseStuff
{
    public static void wakeUpPlayer(Player player)
    {
        Level level = player.level();

        if (!isPlayerInComa(player)) {
            return;
        }

        if (level instanceof ServerLevel serverLevel) {
            String wakeUpDest = Level.OVERWORLD.location().toString();
            CompoundTag data = player.getPersistentData();
            Function<BlockPos, Comatose.Teleporter> teleporter = Comatose.getTeleporter();

            if (data.contains("PlayerWakeUpDestination")) {
                wakeUpDest = data.getString("PlayerWakeUpDestination");
            }

            MinecraftServer server = serverLevel.getServer();
            ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(wakeUpDest));
            ServerLevel dest = server.getLevel(key);

            if (dest != null && player.canChangeDimensions()) {
                player.changeDimension(dest, teleporter.apply(player.getOnPos()));
            }
        }
    }

    public static int getComaStageOccurence()
    {
        return TqConfig.COMMON.comaStageProgressionOccurrence.get() * 1000;
    }

    public static boolean isPlayerInComa(Player player)
    {
        return player.level().dimension() == Levels.COMATOSE;
    }

    public static boolean shouldRenderComaStage(Player player)
    {
        return isPlayerInComa(player) && player.getPersistentData().contains("ComaStage") && TqConfig.CLIENT.showComaStage.get();
    }
}
