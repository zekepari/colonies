package io.anthills;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.plugin.java.JavaPlugin;

import io.anthills.classes.Colony;
import io.anthills.classes.PheroCell;
import io.anthills.commands.CommandRegistry;
import io.anthills.events.CellListener;

public class Plugin extends JavaPlugin {
  private static Plugin instance;

  public static final Set<PheroCell> pheroCells = new HashSet<>();
  public static final Set<Colony> colonies = new HashSet<>();

  @Override
  public void onEnable() {
    instance = this;

    CommandRegistry.registerCommands();

    getServer().getPluginManager().registerEvents(new CellListener(), this);
    getLogger().info("Plugin enabled!");
  }

  @Override
  public void onDisable() {
    getLogger().info("Plugin disabled!");
  }

  public static Plugin getInstance() {
    return instance;
  }
}
