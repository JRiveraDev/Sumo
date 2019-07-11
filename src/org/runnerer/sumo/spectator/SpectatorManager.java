package org.runnerer.sumo.spectator;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.runnerer.sumo.GameManager;

public class SpectatorManager
{

    @EventHandler
    public void spectatorBreak(BlockBreakEvent event)
    {
        if (!SpectatorManager.isSpectator(event.getPlayer())) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void spectatorPlace(BlockPlaceEvent event)
    {
        if (!SpectatorManager.isSpectator(event.getPlayer())) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void spectatorPickup(PlayerPickupItemEvent event)
    {
        if (!SpectatorManager.isSpectator(event.getPlayer())) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void spectatorDrop(PlayerDropItemEvent event)
    {
        if (!SpectatorManager.isSpectator(event.getPlayer())) return;

        event.setCancelled(true);
    }

    public static void setSpectator(Player player)
    {
        if (GameManager.instance.spectatorList.contains(player.getName())) return;

        GameManager.instance.spectatorList.add(player.getName());
    }

    public static void removeSpectator(Player player)
    {
        if (!GameManager.instance.spectatorList.contains(player.getName())) return;

        GameManager.instance.spectatorList.remove(player.getName());
    }

    public static boolean isSpectator(Player player)
    {
        if (GameManager.instance.spectatorList.contains(player.getName())) return true;

        return false;
    }

    public static void startAsSpectator(Player player)
    {

    }
}
