package io.anthills.commands.colony.subcommands;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;

import io.anthills.classes.Ant;
import io.anthills.classes.Colony;
import io.anthills.managers.GlobalCache;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

public class CreateCommand {
    public LiteralCommandNode<CommandSourceStack> getCommandNode() {
        return Commands.literal("create")
                .then(Commands.argument("name", StringArgumentType.greedyString())
                        .executes(this::execute))
                .build();
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        final Player player = (Player) context.getSource().getSender();
        final String name = StringArgumentType.getString(context, "name");

        Ant ant = GlobalCache.getAnt(player.getUniqueId());

        if (ant.getColony() != null) {
            player.sendMessage("You are already in a colony.");
            return 1;
        }

        Colony colony = new Colony(UUID.randomUUID(), name, ant);
        GlobalCache.registerColony(colony);

        player.sendMessage("Colony created: " + name);
        return 1;
    }
}