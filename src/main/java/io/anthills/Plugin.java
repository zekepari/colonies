package io.anthills;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import io.anthills.commands.CommandRegistry;
import io.anthills.listeners.CellMoveListener;
import io.anthills.listeners.CellUpdateListener;
import io.anthills.listeners.NodeBreakListener;
import io.anthills.listeners.NodeSpawnListener;
import io.anthills.listeners.PlayerJoinListener;
import io.anthills.listeners.PlayerMoveListener;
import io.anthills.listeners.PlayerQuitListener;
import io.anthills.listeners.SurfaceListener;
import io.anthills.persistence.PersistenceManager;

public class Plugin extends JavaPlugin {
  private static Plugin instance;
  private static PersistenceManager persistenceManager;
  private int saveTaskId = -1;

  @Override
  public void onEnable() {
    instance = this;

    getLogger().info("Loading persistence...");
    try {
      File dataFolder = getDataFolder();
      if (!dataFolder.exists()) {
        dataFolder.mkdirs();
      }
      String dbPath = new File(dataFolder, "anthills.db").getAbsolutePath();
      String dbUrl = "jdbc:sqlite:" + dbPath;

      persistenceManager = new PersistenceManager(dbUrl);
      persistenceManager.loadAll();
      getLogger().info("Data loaded successfully.");

      saveTaskId = Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
        try {
          persistenceManager.saveAll();
          getLogger().info("Periodic save completed.");
        } catch (Exception e) {
          getLogger().severe("Failed to save data asynchronously: " + e.getMessage());
        }
      }, 6000L, 6000L).getTaskId();
    } catch (Exception e) {
      getLogger().severe("Failed to load data from persistence: " + e.getMessage());
    }

    CommandRegistry.registerCommands();
    getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
    getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
    getServer().getPluginManager().registerEvents(new CellMoveListener(), this);
    getServer().getPluginManager().registerEvents(new CellUpdateListener(), this);
    getServer().getPluginManager().registerEvents(new NodeBreakListener(), this);
    getServer().getPluginManager().registerEvents(new NodeSpawnListener(), this);
    getServer().getPluginManager().registerEvents(new SurfaceListener(), this);

    getLogger().info("Plugin enabled!");
  }

  @Override
  public void onDisable() {
    getLogger().info("Saving persistence...");
    try {
      if (saveTaskId != -1) {
        Bukkit.getScheduler().cancelTask(saveTaskId);
      }
      if (persistenceManager != null) {
        persistenceManager.saveAll();
        persistenceManager.close();
      }
      getLogger().info("Data saved successfully.");
    } catch (Exception e) {
      getLogger().severe("Failed to save data to persistence: " + e.getMessage());
    }

    getLogger().info("Plugin disabled!");
  }

  public static Plugin getInstance() {
    return instance;
  }

  public static PersistenceManager getPersistenceManager() {
    return persistenceManager;
  }
}
