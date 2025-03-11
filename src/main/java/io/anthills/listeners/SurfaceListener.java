package io.anthills.listeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.world.StructureGrowEvent;

import io.anthills.classes.CellPosition;
import io.anthills.classes.Node;
import io.anthills.events.NodeBreakEvent;
import io.anthills.managers.GlobalCache;

public class SurfaceListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Node node = GlobalCache.getNode(event.getBlock().getLocation());

        if (node != null) {
            Bukkit.getPluginManager().callEvent(new NodeBreakEvent(node, player, block));
            return;
        }

        if (player.hasPermission("anthills.admin"))
            return;
        CellPosition cellPosition = new CellPosition(event.getBlock().getLocation());
        event.setCancelled(cellPosition.isSurface());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("anthills.admin"))
            return;
        CellPosition cellPosition = new CellPosition(event.getBlock().getLocation());
        event.setCancelled(cellPosition.isSurface());
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        Player player = event.getPlayer();
        Node node = GlobalCache.getNode(event.getBlock().getLocation());

        if (node != null || player.hasPermission("anthills.admin"))
            return;
        CellPosition cellPosition = new CellPosition(event.getBlock().getLocation());
        event.setCancelled(cellPosition.isSurface());
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().removeIf(block -> {
            CellPosition cellPosition = new CellPosition(block.getLocation());
            return cellPosition.isSurface();
        });
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        event.blockList().removeIf(block -> {
            CellPosition cellPosition = new CellPosition(block.getLocation());
            return cellPosition.isSurface();
        });
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        Block highestBlock = event.getBlocks().stream().max((b1, b2) -> b1.getY() - b2.getY()).orElse(null);
        if (highestBlock == null)
            return;

        CellPosition cellPosition = new CellPosition(highestBlock.getLocation());
        event.setCancelled(cellPosition.isSurface());
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        Block highestBlock = event.getBlocks().stream().max((b1, b2) -> b1.getY() - b2.getY()).orElse(null);
        if (highestBlock == null)
            return;

        CellPosition cellPosition = new CellPosition(highestBlock.getLocation());
        event.setCancelled(cellPosition.isSurface());
    }

    @EventHandler
    public void onLeafDecay(LeavesDecayEvent event) {
        CellPosition cellPosition = new CellPosition(event.getBlock().getLocation());
        event.setCancelled(cellPosition.isSurface());
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        CellPosition cellPosition = new CellPosition(event.getBlock().getLocation());
        event.setCancelled(cellPosition.isSurface());
    }

    @EventHandler
    public void onBlockFade(BlockFadeEvent event) {
        CellPosition cellPosition = new CellPosition(event.getBlock().getLocation());
        event.setCancelled(cellPosition.isSurface());
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        CellPosition cellPosition = new CellPosition(event.getBlock().getLocation());
        event.setCancelled(cellPosition.isSurface());
    }

    @EventHandler
    public void onBlockSpread(BlockSpreadEvent event) {
        CellPosition cellPosition = new CellPosition(event.getBlock().getLocation());
        event.setCancelled(cellPosition.isSurface());
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        CellPosition cellPosition = new CellPosition(event.getBlock().getLocation());
        event.setCancelled(cellPosition.isSurface());
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) {
            CellPosition cellPosition = new CellPosition(event.getLocation());
            event.setCancelled(cellPosition.isSurface());
        }
    }

    @EventHandler
    public void onHangingPlace(HangingPlaceEvent event) {
        CellPosition cellPosition = new CellPosition(event.getEntity().getLocation());
        event.setCancelled(cellPosition.isSurface());
    }

    @EventHandler
    public void onHangingBreak(HangingBreakEvent event) {
        CellPosition cellPosition = new CellPosition(event.getEntity().getLocation());
        event.setCancelled(cellPosition.isSurface());
    }

    @EventHandler
    public void onStructureGrow(StructureGrowEvent event) {
        CellPosition cellPosition = new CellPosition(event.getLocation());
        event.setCancelled(cellPosition.isSurface());
    }
}
