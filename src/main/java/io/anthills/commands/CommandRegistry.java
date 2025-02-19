package io.anthills.commands;

import java.util.List;

import com.mojang.brigadier.tree.LiteralCommandNode;

import io.anthills.Plugin;
import io.anthills.commands.colony.ColonyCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

public class CommandRegistry {
    public static void registerCommands() {
        LiteralCommandNode<CommandSourceStack> colonyCommand = ColonyCommand.createCommand().build();

        Plugin.getInstance().getLifecycleManager()
                .registerEventHandler(LifecycleEvents.COMMANDS, events -> {
                    Commands commands = events.registrar();

                    commands.register(colonyCommand, List.of("c"));
                });
    }
}
