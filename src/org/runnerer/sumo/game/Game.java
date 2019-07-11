package org.runnerer.sumo.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.runnerer.core.common.utils.C;
import org.runnerer.core.common.utils.F;
import org.runnerer.core.common.utils.UtilServer;
import org.runnerer.sumo.GameManager;
import org.runnerer.sumo.Sumo;
import org.runnerer.sumo.config.GameOptions;
import org.runnerer.sumo.events.GameStateChangeEvent;
import org.runnerer.sumo.spectator.SpectatorManager;

import java.util.ArrayList;

public abstract class Game
{
    private String name;
    private String[] description;

    private GameState gameState;

    private GameOptions gameConfig;

    public Game(String gameName, String[] gameDescription)
    {
        name = gameName;
        description = gameDescription;

        gameConfig = new GameOptions();
    }

    public void setState(GameState state)
    {
        GameStateChangeEvent event = new GameStateChangeEvent(state);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled())
        {
            System.out.print("Cannot change game state!");
            return;
        }

        gameState = state;
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public GameOptions getGameConfig()
    {
        return gameConfig;
    }

    public String getName()
    {
        return name;
    }

    public String[] getDescription()
    {
        return description;
    }

    public Location getSpectatorLocation()
    {
        // CHANGE.
        return null;
    }

    public Location getSpawnLocations()
    {
        // CHANGE.
        return null;
    }

    public void start()
    {
        for (Player player : UtilServer.getPlayers())
        {
            GameManager.instance.clear(player);

            if (GameManager.instance.spectatorList.contains(player))
            {
                player.teleport(getSpectatorLocation());
                player.sendMessage(F.main("Spectator", "You are spectating this game!"));
                player.setAllowFlight(true);
                player.setFlying(true);
                SpectatorManager.startAsSpectator(player);
            } else
            {
                player.teleport(getSpawnLocations());
            }
            player.sendMessage(C.Yellow + C.Bold + getName());

            for (String descriptionString : getDescription())
            {
                player.sendMessage(C.Gray + descriptionString);
            }

            onStart();
        }
    }

    public void end(Player winner)
    {
        for (Player player : UtilServer.getPlayers())
        {
            player.sendMessage("");
            player.sendMessage("");

            if (winner == null)
            {
                player.sendMessage(C.Yellow + C.Bold + "No one" + C.White + C.Bold + " won the game. :(");
            } else
            {
                player.sendMessage(C.Yellow + C.Bold + winner.getName() + C.White + C.Bold + " won the game!");
            }

            player.sendMessage("");
            player.sendMessage("");

            Sumo.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Sumo.getInstance(), new Runnable()
            {
                public void run() {
                    GameManager.instance.clear(player);
                    player.teleport(GameManager.instance.getLobbySpawn());
                }
            }, 200L);
        }
    }
    public int getPlayers()
    {
        return UtilServer.getPlayers().size() - GameManager.instance.spectatorList.size();
    }

    public ArrayList<Player> getPlayingPlayers()
    {
        ArrayList<Player> gamePlayers = new ArrayList<>();
        for (Player player : UtilServer.getPlayers())
        {
            if (GameManager.instance.spectatorList.size() != 0)
            {
                for (String spectators : GameManager.instance.spectatorList)
                {
                    Player spectator = Bukkit.getPlayer(spectators);

                    if (player != spectator)
                    {
                        gamePlayers.add(player);
                    }
                }
            } else
            {
                gamePlayers.add(player);
            }
        }

        if (gamePlayers.size() == 0) return null;

        return gamePlayers;
    }

    public abstract void runScoreboard();

    public abstract void onStart();
}
