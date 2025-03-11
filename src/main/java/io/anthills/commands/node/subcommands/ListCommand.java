package io.anthills.commands.node.subcommands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import io.anthills.managers.GlobalCache;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public class ListCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands.literal("list")
                .executes(ListCommand::execute);
    }

    private static int execute(CommandContext<CommandSourceStack> context) {
        final CommandSender sender = context.getSource().getSender();

        if (GlobalCache.getNodes().isEmpty()) {
            sender.sendMessage("There are no nodes in the global cache.");
        } else {
            GlobalCache.getNodes().values().forEach(node -> {
                Location loc = node.getLocation();
                Component message = MiniMessage.miniMessage().deserialize(
                        "Node: <node_type> (<x>, <y>, <z>)",
                        Placeholder.unparsed("node_type", node.getType().name()),
                        Placeholder.unparsed("x", String.valueOf(loc.getBlockX())),
                        Placeholder.unparsed("y", String.valueOf(loc.getBlockY())),
                        Placeholder.unparsed("z", String.valueOf(loc.getBlockZ())));
                sender.sendMessage(message);
            });
        }

        return Command.SINGLE_SUCCESS;
    }
}