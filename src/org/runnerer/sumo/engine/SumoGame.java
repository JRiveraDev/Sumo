package org.runnerer.sumo.engine;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.runnerer.core.common.utils.C;
import org.runnerer.core.update.events.UpdateEvent;
import org.runnerer.sumo.GameManager;
import org.runnerer.sumo.game.Game;
import org.runnerer.sumo.game.GameState;

public class SumoGame extends Game
{

    public SumoGame()
    {
        super("Sumo", new String[] {C.Gray + "You have a porkchop that", C.Gray + "knocks players back far!", C.Gray + "Knock out all players and", C.Gray + "become the victor!" });

        getGameConfig().itemPickup = false;
        getGameConfig().itemDrop = false;
        getGameConfig().damagePvP = true;
        getGameConfig().blockPlace = false;
        getGameConfig().blockBreak = false;
        getGameConfig().damageEvP = false;
        getGameConfig().damageOther = false;
        getGameConfig().damage = true;
    }

    @Override
    public void runScoreboard()
    {
        GameManager.instance.getBoard().add("", 2);
        GameManager.instance.getBoard().add("Fight to the end!", 1);
    }

    @Override
    public void onStart()
    {
        for (Player player : getPlayingPlayers())
        {
            ItemStack itemStack = new ItemStack(Material.PORK);
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName("Mighty Porkchop");

            itemStack.setItemMeta(itemMeta);
            player.getInventory().addItem(itemStack);
        }
    }

    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent e)
    {
        if (getGameState() != GameState.IN_PROGRESS) return;

        if (e.getDamager() instanceof Player)
        {
            Player damager = (Player) e.getDamager();

            Entity p = e.getEntity();

            p.setVelocity(damager.getLocation().getDirection().setY(0).normalize().multiply(5));

        }
    }

    @EventHandler
    public void waterDamage(UpdateEvent event)
    {
        if (getGameState() != GameState.IN_PROGRESS) return;

        for (Player player : getPlayingPlayers())
        {
            if (player.getLocation().getBlock().getTypeId() == 8 || player.getLocation().getBlock().getRelative(BlockFace.UP).getTypeId() == 8 ||
                    player.getLocation().getBlock().getTypeId() == 9 || player.getLocation().getBlock().getRelative(BlockFace.UP).getTypeId() == 9)
            {
                player.damage(2);

                player.getWorld().playSound(player.getLocation(),
                        Sound.SPLASH, 1f,
                        1f);
            }
        }
    }
}
