package io.anthills.commands.colony.subcommands;

import org.bukkit.entity.Player;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;

import io.anthills.classes.Colony;
import io.anthills.managers.ColonyManager;
import io.anthills.utils.Ants;
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

        Colony colony = ColonyManager.registerColony(name, player.getUniqueId());
        Ants.setColonyID(player, colony.getUniqueId());

        player.sendMessage("Colony created: " + name);
        return 1;
    }
}