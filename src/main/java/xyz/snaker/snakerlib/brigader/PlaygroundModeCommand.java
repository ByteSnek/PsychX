package xyz.snaker.snakerlib.brigader;

import java.util.List;
import java.util.function.Predicate;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.utility.tools.WorldStuff;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

/**
 * Created by SnakerBone on 22/08/2023
 **/
public class PlaygroundModeCommand
{
    private boolean enabled;

    PlaygroundModeCommand(CommandDispatcher<CommandSourceStack> dispatcher, String name, String arg)
    {
        dispatcher.register(Commands.literal(name)
                .then(Commands.literal(arg)
                        .executes(this::execute)));
    }

    public static PlaygroundModeCommand register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        return new PlaygroundModeCommand(dispatcher, SnakerLib.MODID, "PlaygroundMode");
    }

    private int execute(CommandContext<CommandSourceStack> context)
    {
        Predicate<Entity> predicate = entity -> !(entity instanceof ServerPlayer);
        ServerPlayer player = context.getSource().getPlayer();

        if (player != null) {
            MinecraftServer server = player.getServer();
            Level level = player.level();

            if (server != null) {
                GameRules rules = server.getGameRules();
                List<Entity> entities = level.getEntitiesOfClass(Entity.class, WorldStuff.getWorldBoundingBox(player), predicate);

                for (Entity entity : entities) {
                    entity.discard();
                }

                enabled = !enabled;

                boolean active = !enabled;

                rules.getRule(GameRules.RULE_DAYLIGHT).set(active, server);
                rules.getRule(GameRules.RULE_DOMOBSPAWNING).set(active, server);
                rules.getRule(GameRules.RULE_WEATHER_CYCLE).set(active, server);

                if (!player.getPersistentData().contains("PlaygroundMode")) {
                    player.getPersistentData().putBoolean("PlaygroundMode", active);
                }

                context.getSource().sendSuccess(() -> Component.literal(String.format("%s Playground Mode", enabled ? "Enabled" : "Disabled")), true);

                return 1;
            }
        }

        return 0;
    }
}
