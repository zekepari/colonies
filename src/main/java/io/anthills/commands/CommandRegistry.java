package io.anthills.commands;

import java.util.List;

import io.anthills.Plugin;
import io.anthills.commands.colony.ColonyCommand;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

public class CommandRegistry {
    public static void registerCommands() {
        Plugin.getInstance().getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();

            commands.register(
                    new ColonyCommand().getCommandNode(),
                    List.of("c"));
        });
    }
}
