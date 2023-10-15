package xyz.snaker.tq.commands;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.config.TqConfig;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;

/**
 * Created by SnakerBone on 15/10/2023
 **/
public class ModifyConfigCommand
{
    ModifyConfigCommand(CommandDispatcher<CommandSourceStack> dispatcher, String name)
    {
        dispatcher.register(Commands.literal(name)
                .then(Commands.literal("config")
                        .then(Commands.literal("visionConvolveActive")
                                .then(Commands.argument("visionConvolveActive", BoolArgumentType.bool())
                                        .executes(context -> setVisionConvolveActive(context,
                                                BoolArgumentType.getBool(context, "visionConvolveActive")))
                                )
                        )
                        .then(Commands.literal("showComaStage")
                                .then(Commands.argument("showComaStage", BoolArgumentType.bool())
                                        .executes(context -> setShowComaStage(context,
                                                BoolArgumentType.getBool(context, "showComaStage")))
                                )
                        )
                )
        );
    }

    public static ModifyConfigCommand register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        return new ModifyConfigCommand(dispatcher, Tourniqueted.MODID);
    }

    private int setShowComaStage(CommandContext<CommandSourceStack> context, boolean value)
    {
        TqConfig.CLIENT.showComaStage.set(value);
        TqConfig.CLIENT.showComaStage.save();
        context.getSource().sendSuccess(() -> Component.translatable("commands.tq.config_set_success"), true);
        return 0;
    }

    private int setVisionConvolveActive(CommandContext<CommandSourceStack> context, boolean value)
    {
        TqConfig.COMMON.visionConvolveActive.set(value);
        TqConfig.COMMON.visionConvolveActive.save();
        context.getSource().sendSuccess(() -> Component.translatable("commands.tq.config_set_success"), true);
        return 0;
    }
}
