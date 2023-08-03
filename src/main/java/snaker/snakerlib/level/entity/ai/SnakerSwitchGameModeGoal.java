package snaker.snakerlib.level.entity.ai;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import snaker.snakerlib.config.SnakerConfig;

import java.util.List;

/**
 * Created by SnakerBone on 28/02/2023
 **/
public class SnakerSwitchGameModeGoal extends Goal
{
    private final Mob owner;

    public SnakerSwitchGameModeGoal(Mob owner)
    {
        this.owner = owner;
    }

    @Override
    public void tick()
    {
        Level level = owner.level();
        List<ServerPlayer> players = level.getEntitiesOfClass(ServerPlayer.class, owner.getBoundingBox().inflate(8));
        if (players.stream().anyMatch(ServerPlayer::isCreative)) {
            for (ServerPlayer player : players) {
                player.setGameMode(GameType.SURVIVAL);
                owner.setTarget(player);
            }
        }
    }

    @Override
    public boolean canUse()
    {
        return owner.getLastHurtByMob() instanceof ServerPlayer && SnakerConfig.COMMON.playerVulnerableInCreative.get();
    }
}
