package io.anthills.commands.colony;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import io.anthills.commands.colony.subcommands.CreateCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

public class ColonyCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands.literal("colony")
                .executes(ColonyCommand::usage)
                .then(CreateCommand.createCommand());
    }

    private static int usage(CommandContext<CommandSourceStack> context) {
        context.getSource().getSender().sendMessage("Usage: /colony <subcommand>");
        return Command.SINGLE_SUCCESS;
    }
}