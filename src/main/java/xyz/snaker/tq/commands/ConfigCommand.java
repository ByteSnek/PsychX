package xyz.snaker.tq.commands;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.config.Config;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;

/**
 * Created by SnakerBone on 15/10/2023
 **/
public class ConfigCommand
{
    ConfigCommand(CommandDispatcher<CommandSourceStack> dispatcher, String name)
    {
        dispatcher.register(Commands.literal(name)
                .then(Commands.literal("config")
                        .then(Commands.literal("visionConvolveActive")
                                .then(Commands.argument("visionConvolveActive", BoolArgumentType.bool())
                                        .executes(context -> setVisionConvolveActive(context,
                                                BoolArgumentType.getBool(context, "visionConvolveActive"))
                                        )
                                )
                        )
                        .then(Commands.literal("showComaStage")
                                .then(Commands.argument("showComaStage", BoolArgumentType.bool())
                                        .executes(context -> setShowComaStage(context,
                                                BoolArgumentType.getBool(context, "showComaStage"))
                                        )
                                )
                        )
                        .then(Commands.literal("comaStageProgressionOccurrence")
                                .then(Commands.argument("comaStageProgressionOccurrence", IntegerArgumentType.integer(1, 100))
                                        .executes(context -> setComaStageProgressionOccurrence(context,
                                                IntegerArgumentType.getInteger(context, "comaStageProgressionOccurrence"))
                                        )
                                )
                        )
                        .then(Commands.literal("healthRepairKeybindingsActive")
                                .then(Commands.argument("healthRepairKeybindingsActive", BoolArgumentType.bool())
                                        .executes(context -> setHealthRepairKeybindingsActive(context,
                                                BoolArgumentType.getBool(context, "healthRepairKeybindingsActive"))
                                        )
                                )
                        )
                )
        );
    }

    public static ConfigCommand register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        return new ConfigCommand(dispatcher, Tourniqueted.MODID);
    }

    private int setShowComaStage(CommandContext<CommandSourceStack> context, boolean value)
    {
        CommandSourceStack stack = context.getSource();
        CommandSource source = stack.source;

        Config.CLIENT.showComaStage.set(value);
        Config.CLIENT.showComaStage.save();

        stack.sendSuccess(this::success, true);

        return source.acceptsSuccess() ? 1 : 0;
    }

    private int setVisionConvolveActive(CommandContext<CommandSourceStack> context, boolean value)
    {
        CommandSourceStack stack = context.getSource();
        CommandSource source = stack.source;

        Config.COMMON.visionConvolveActive.set(value);
        Config.COMMON.visionConvolveActive.save();

        stack.sendSuccess(this::success, true);

        return source.acceptsSuccess() ? 1 : 0;
    }

    private int setComaStageProgressionOccurrence(CommandContext<CommandSourceStack> context, int value)
    {
        CommandSourceStack stack = context.getSource();
        CommandSource source = stack.source;

        Config.COMMON.comaStageProgressionOccurrence.set(value);
        Config.COMMON.comaStageProgressionOccurrence.save();

        stack.sendSuccess(this::success, true);

        return source.acceptsSuccess() ? 1 : 0;
    }

    private int setHealthRepairKeybindingsActive(CommandContext<CommandSourceStack> context, boolean value)
    {
        CommandSourceStack stack = context.getSource();
        CommandSource source = stack.source;

        Config.COMMON.healthRepairKeybindingsActive.set(value);
        Config.COMMON.healthRepairKeybindingsActive.save();

        stack.sendSuccess(this::success, true);

        return source.acceptsSuccess() ? 1 : 0;
    }

    private Component success()
    {
        return Component.translatable("snakerlib.commands.config_set_success");
    }
}
