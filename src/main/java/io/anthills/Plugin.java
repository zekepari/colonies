package io.anthills;

import org.bukkit.plugin.java.JavaPlugin;

import io.anthills.commands.CommandRegistry;
import io.anthills.listeners.CellMoveListener;
import io.anthills.listeners.CellUpdateListener;
import io.anthills.listeners.NodeBreakListener;
import io.anthills.listeners.PlayerJoinListener;
import io.anthills.listeners.PlayerMoveListener;
import io.anthills.listeners.PlayerQuitListener;
import io.anthills.listeners.SurfaceListener;

public class Plugin extends JavaPlugin {
  private static Plugin instance;

  @Override
  public void onEnable() {
    instance = this;

    CommandRegistry.registerCommands();
    getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
    getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
    getServer().getPluginManager().registerEvents(new CellMoveListener(), this);
    getServer().getPluginManager().registerEvents(new CellUpdateListener(), this);
    getServer().getPluginManager().registerEvents(new NodeBreakListener(), this);
    getServer().getPluginManager().registerEvents(new SurfaceListener(), this);
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
