package io.anthills.commands.node.subcommands;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import io.anthills.classes.Node;
import io.anthills.managers.data.GlobalCache;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;

public class DeleteCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands.literal("delete").executes(DeleteCommand::execute);
    }

    private static int execute(CommandContext<CommandSourceStack> context) {
        final CommandSender sender = context.getSource().getSender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command.");
            return Command.SINGLE_SUCCESS;
        }

        Block targetBlock = player.getTargetBlockExact(10);
        if (targetBlock == null) {
            sender.sendMessage("No block in sight within range.");
            return Command.SINGLE_SUCCESS;
        }

        Location location = targetBlock.getLocation();
        Node node = GlobalCache.getNode(location);
        if (node == null) {
            sender.sendMessage("No node exists at that location.");
            return Command.SINGLE_SUCCESS;
        }

        node.delete();
        GlobalCache.unregisterNode(location);

        sender.sendMessage(Component.text("Deleted node at ("
                + location.getBlockX() + ", "
                + location.getBlockY() + ", "
                + location.getBlockZ() + ")"));
        return Command.SINGLE_SUCCESS;
    }
}
