package io.anthills.commands.colony.subcommands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import io.anthills.classes.Ant;
import io.anthills.classes.Colony;
import io.anthills.events.ColonyCreateEvent;
import io.anthills.managers.data.GlobalCache;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

public class CreateCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands.literal("create")
                .executes(CreateCommand::usage)
                .then(Commands.argument("name", StringArgumentType.greedyString())
                        .executes(CreateCommand::execute));
    }

    private static int usage(CommandContext<CommandSourceStack> context) {
        context.getSource().getSender().sendMessage("Usage: /colony create <name>");
        return Command.SINGLE_SUCCESS;
    }

    private static int execute(CommandContext<CommandSourceStack> context) {
        final String name = StringArgumentType.getString(context, "name");
        final CommandSender sender = context.getSource().getSender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command.");
            return Command.SINGLE_SUCCESS;
        }

        Ant ant = GlobalCache.getAnt(player.getUniqueId());

        if (ant.getColony() != null) {
            player.sendMessage("You are already in a colony.");
            return Command.SINGLE_SUCCESS;
        }

        Colony colony = new Colony(UUID.randomUUID(), name);
        colony.setQueen(ant);
        ant.setColony(colony);
        GlobalCache.registerColony(colony);
        Bukkit.getPluginManager().callEvent(new ColonyCreateEvent(colony));

        player.sendMessage("Colony created: " + name);
        return Command.SINGLE_SUCCESS;
    }
}