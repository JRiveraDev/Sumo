package org.runnerer.sumo;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.runnerer.sumo.engine.GameEngine;
import org.runnerer.sumo.engine.SumoGame;
import org.runnerer.sumo.lobby.LobbyManager;

public class Sumo extends JavaPlugin
{
    public static Sumo instance;

    @Override
    public void onEnable()
    {
        instance = this;

        GameManager gameManager = new GameManager();
        registerEngine(new LobbyManager());
        registerEngine(new GameEngine());

        gameManager.setGame(new SumoGame());
    }

    public void registerEngine(Listener listener)
    {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public static Sumo getInstance()
    {
        return instance;
    }
}
