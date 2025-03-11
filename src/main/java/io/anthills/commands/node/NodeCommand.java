package io.anthills.commands.node;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import io.anthills.commands.node.subcommands.CreateCommand;
import io.anthills.commands.node.subcommands.ListCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

public class NodeCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands.literal("node")
                .requires(sender -> sender.getSender().hasPermission("anthills.admin"))
                .executes(NodeCommand::usage)
                .then(CreateCommand.createCommand())
                .then(ListCommand.createCommand());
    }

    private static int usage(CommandContext<CommandSourceStack> context) {
        context.getSource().getSender().sendMessage("Usage: /node <subcommand>");
        return Command.SINGLE_SUCCESS;
    }
}