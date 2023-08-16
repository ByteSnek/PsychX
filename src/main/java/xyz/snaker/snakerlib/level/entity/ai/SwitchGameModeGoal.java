package xyz.snaker.snakerlib.level.entity.ai;

import java.util.List;

import xyz.snaker.snakerlib.config.SnakerConfig;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

/**
 * Created by SnakerBone on 28/02/2023
 **/
public class SwitchGameModeGoal extends Goal
{
    /**
     * The mob using this goal
     **/
    private final Mob owner;

    public SwitchGameModeGoal(Mob owner)
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
