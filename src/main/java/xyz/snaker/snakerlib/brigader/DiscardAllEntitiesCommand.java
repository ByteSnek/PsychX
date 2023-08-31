package xyz.snaker.snakerlib.brigader;

import java.util.List;
import java.util.function.Predicate;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.utility.tools.WorldStuff;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

/**
 * Created by SnakerBone on 31/08/2023
 **/
public class DiscardAllEntitiesCommand
{
    DiscardAllEntitiesCommand(CommandDispatcher<CommandSourceStack> dispatcher, String name, String arg)
    {
        dispatcher.register(Commands.literal(name)
                .then(Commands.literal(arg)
                        .executes(this::execute)));
    }

    public static DiscardAllEntitiesCommand register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        return new DiscardAllEntitiesCommand(dispatcher, SnakerLib.MODID, "DiscardAllEntities");
    }

    private int execute(CommandContext<CommandSourceStack> context)
    {
        Predicate<Entity> predicate = entity -> !(entity instanceof ServerPlayer);
        ServerPlayer player = context.getSource().getPlayer();

        if (player != null) {
            Level level = player.level();
            List<Entity> entities = level.getEntitiesOfClass(Entity.class, WorldStuff.getWorldBoundingBox(player), predicate);

            for (Entity entity : entities) {
                entity.discard();
            }

            if (!entities.isEmpty()) {
                context.getSource().sendSuccess(() -> Component.literal(String.format("Successfully discarded %s entities", entities.size())), true);
                return 1;
            } else {
                context.getSource().sendFailure(Component.literal("Could not find any entities to discard"));
                return 0;
            }
        }

        return 0;
    }
}
