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
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;

/**
 * Created by SnakerBone on 31/08/2023
 **/
public class HurtAllEntitiesCommand
{
    HurtAllEntitiesCommand(CommandDispatcher<CommandSourceStack> dispatcher, String name, String arg)
    {
        dispatcher.register(Commands.literal(name)
                .then(Commands.literal(arg)
                        .then(Commands.argument("DamageAmount", FloatArgumentType.floatArg())
                                .executes(context -> execute(context,
                                        FloatArgumentType.getFloat(context, "DamageAmount"))))));
    }

    public static HurtAllEntitiesCommand register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        return new HurtAllEntitiesCommand(dispatcher, SnakerLib.MODID, "HurtAllEntities");
    }

    private int execute(CommandContext<CommandSourceStack> context, float amount)
    {
        Predicate<Entity> predicate = entity -> !(entity instanceof ServerPlayer);
        ServerPlayer player = context.getSource().getPlayer();

        if (player != null) {
            Level level = player.level();
            List<Entity> entities = level.getEntitiesOfClass(Entity.class, WorldStuff.getWorldBoundingBox(player), predicate);

            boolean immune = false;

            for (Entity entity : entities) {
                if (!entity.isInvulnerable()) {
                    entity.hurt(level.damageSources().generic(), amount);
                } else {
                    immune = true;
                }
            }

            if (!entities.isEmpty() && immune) {
                context.getSource().sendSuccess(() -> Component.literal(String.format("Successfully inflicted %s entities with %s damage. Some entities could not be hurt as they were invulnerable", entities.size(), amount)), true);
                return 1;
            } else if (!entities.isEmpty()) {
                context.getSource().sendSuccess(() -> Component.literal(String.format("Successfully inflicted %s entities with %s damage", entities.size(), amount)), true);
                return 1;
            } else {
                context.getSource().sendFailure(Component.literal("Could not find any entities to hurt"));
                return 0;
            }
        }

        return 0;
    }
}
