package io.anthills.commands.node.subcommands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import io.anthills.classes.Node;
import io.anthills.enums.NodeType;
import io.anthills.managers.data.GlobalCache;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

public class CreateCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("create")
                .executes(CreateCommand::usage);

        for (NodeType type : NodeType.values()) {
            builder.then(Commands.literal(type.name().toLowerCase())
                    .executes(ctx -> execute(ctx, type)));
        }
        return builder;
    }

    private static int usage(CommandContext<CommandSourceStack> context) {
        context.getSource().getSender().sendMessage("Usage: /node create <" + getValidTypes() + ">");
        return Command.SINGLE_SUCCESS;
    }

    private static String getValidTypes() {
        StringBuilder sb = new StringBuilder();
        for (NodeType type : NodeType.values())
            sb.append(type.name().toLowerCase()).append("|");
        if (sb.length() > 0)
            sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private static int execute(CommandContext<CommandSourceStack> context, NodeType nodeType) {
        final CommandSender sender = context.getSource().getSender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command.");
            return Command.SINGLE_SUCCESS;
        }

        Location location = player.getLocation().getBlock().getLocation();
        if (GlobalCache.getNode(location) != null) {
            sender.sendMessage("Node already exists at your location.");
            return Command.SINGLE_SUCCESS;
        }

        Node node = new Node(location, nodeType);
        GlobalCache.registerNode(node);

        if (!node.getLocation().getBlock().isPassable()) {
            Location tpLocation = player.getLocation().add(0, 1, 0);
            player.teleportAsync(tpLocation);
        }

        player.sendMessage("Created node of type " + nodeType.name() + " at your location.");
        return Command.SINGLE_SUCCESS;
    }
}