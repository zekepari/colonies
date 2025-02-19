package io.anthills;

import org.bukkit.plugin.java.JavaPlugin;

import io.anthills.commands.CommandRegistry;
import io.anthills.listeners.CellChangeListener;
import io.anthills.listeners.ColonyClaimListener;
import io.anthills.listeners.PheroCellTierChangeListener;
import io.anthills.listeners.PlayerJoinListener;
import io.anthills.listeners.PlayerMoveListener;

public class Plugin extends JavaPlugin {
  private static Plugin instance;

  @Override
  public void onEnable() {
    instance = this;

    CommandRegistry.registerCommands();

    getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
    getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    getServer().getPluginManager().registerEvents(new CellChangeListener(), this);
    getServer().getPluginManager().registerEvents(new ColonyClaimListener(), this);
    getServer().getPluginManager().registerEvents(new PheroCellTierChangeListener(), this);
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
