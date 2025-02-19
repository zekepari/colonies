package io.anthills.commands.colony;

import org.bukkit.entity.Player;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;

import io.anthills.commands.colony.subcommands.CreateCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

public class ColonyCommand {
    public LiteralCommandNode<CommandSourceStack> getCommandNode() {
        return Commands.literal("colony")
                .requires(source -> source instanceof Player)
                .executes(this::execute)
                .then(new CreateCommand().getCommandNode())
                .build();
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        context.getSource().getSender().sendMessage("Usage: /colony <subcommand>");
        return 1;
    }
}