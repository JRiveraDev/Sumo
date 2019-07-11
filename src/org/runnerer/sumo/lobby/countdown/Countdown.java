package org.runnerer.sumo.lobby.countdown;

import org.bukkit.Bukkit;
import org.runnerer.sumo.GameManager;
import org.runnerer.sumo.Sumo;

public class Countdown
{
    private static int task;
    private static int timer = 16;

    public static void startCountdown()
    {
        task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Sumo.getInstance(), new Runnable() {

            @Override
            public void run()
            {
                timer--;

                if (timer == 15 || (timer >= 10 & timer != 1)) GameManager.instance.announce("The game starts in " + timer + " seconds!");

                if (timer == 1)
                {
                    GameManager.instance.announce("The game starts in " + timer + " second!");
                }

                if (timer == 0)
                {
                    Bukkit.getScheduler().cancelTask(task);
                    GameManager.instance.getGame().start();
                }

            }
        },0L, 20L);
    }
}
