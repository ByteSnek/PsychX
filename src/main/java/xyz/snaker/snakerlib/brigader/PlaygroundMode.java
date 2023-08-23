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
public class PlaygroundMode
{
    private boolean enabled;

    private PlaygroundMode(CommandDispatcher<CommandSourceStack> dispatcher, String name, String arg)
    {
        registerCommand(dispatcher, name, arg);
    }

    public static PlaygroundMode register(CommandDispatcher<CommandSourceStack> dispatcher, String name, String arg)
    {
        return new PlaygroundMode(dispatcher, name, arg);
    }

    public static PlaygroundMode register(CommandDispatcher<CommandSourceStack> dispatcher, String arg)
    {
        return register(dispatcher, SnakerLib.MOD.get(), arg);
    }

    public static PlaygroundMode register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        return register(dispatcher, "TogglePlaygroundMode");
    }

    public void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher, String name, String arg)
    {
        dispatcher.register(Commands.literal(name)
                .then(Commands.literal(arg)
                        .executes(this::actuallyExecute)));
    }

    private int actuallyExecute(CommandContext<CommandSourceStack> context)
    {
        int result;
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            SnakerLib.LOGGER.warn("Player is null");
            return 0;
        }
        enabled = !enabled;
        if (executeCommand(player, !enabled)) {
            context.getSource().sendSuccess(() -> Component.literal(String.format("%s Playground Mode", enabled ? "Enabled" : "Disabled")), true);
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }

    private boolean executeCommand(ServerPlayer player, boolean value)
    {
        boolean result;
        MinecraftServer server = player.getServer();
        if (server != null) {
            Predicate<Entity> noPlayer = entity -> !(entity instanceof ServerPlayer);
            GameRules rules = server.getGameRules();
            Level level = player.level();
            List<Entity> entities = level.getEntitiesOfClass(Entity.class, WorldStuff.getWorldBoundingBox(player), noPlayer);
            for (var entity : entities) {
                entity.discard();
            }
            rules.getRule(GameRules.RULE_DAYLIGHT).set(value, server);
            rules.getRule(GameRules.RULE_DOMOBSPAWNING).set(value, server);
            rules.getRule(GameRules.RULE_WEATHER_CYCLE).set(value, server);
            if (shouldWriteData(player)) {
                player.getPersistentData().putBoolean("PlaygroundMode", value);
            }
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    private boolean shouldWriteData(ServerPlayer player)
    {
        return !player.getPersistentData().contains("PlaygroundMode");
    }
}
