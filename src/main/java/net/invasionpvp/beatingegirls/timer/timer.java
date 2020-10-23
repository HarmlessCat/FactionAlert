package net.invasionpvp.beatingegirls.timer;

import net.prosavage.factionsx.FactionsX;
import net.prosavage.factionsx.addonframework.Addon;
import net.prosavage.factionsx.command.engine.CommandInfo;
import net.prosavage.factionsx.core.FPlayer;
import net.prosavage.factionsx.core.Faction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class timer {

    public static HashMap<Faction, Integer> factionWallCheckTime = new HashMap<>();
    public static HashMap<Faction, Long> factionRunnable = new HashMap<>();
    static int finalTimeSinceCheck = 0;
    public static void resetCheckTime() {
        finalTimeSinceCheck = 0;
    }

    static YamlConfiguration Alerts = net.invasionpvp.beatingegirls.FAlert.getAlertFileYaml();

    public static Runnable giveRunnable(final CommandInfo info) {
        Runnable done = new Runnable()
        {
            public void run() {
                sendFactionMsg(info);
            }
        };
        return done;
    }


    public static void start(CommandInfo info) {
        int timerTime = Alerts.getConfigurationSection(info.getFaction().getTag()).getConfigurationSection("Timer").getInt("Timer");

        factionRunnable.put(info.getFaction(), info.getFaction().getId());
        factionWallCheckTime.put(info.getFaction(), 0);

        int FactionTaskId = factionRunnable.get(info.getFaction()).intValue();
        FactionTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(net.invasionpvp.beatingegirls.FAlert.getInstance().getFactionsXInstance(),
                giveRunnable(info),
                0L,
                20*10*timerTime);
    }


    public static void stop(CommandInfo info ) {
        info.getPlayer().sendMessage("" + FactionsX.getInstance().getServer().getScheduler().getActiveWorkers());


    }
    //net.invasionpvp.beatingegirls.FAlert.getInstance().getFactionsXInstance().getServer().getScheduler().cancelTask(factionRunnable.get(info.getFaction()).intValue());
    private static Runnable sendFactionMsg(CommandInfo info) {
        int SetTimer = Integer.parseInt(Alerts.getConfigurationSection(info.getFaction().getTag()).getConfigurationSection("Timer").get("Timer").toString());
        finalTimeSinceCheck = finalTimeSinceCheck + SetTimer;
        for (FPlayer fp : info.getFaction().getOnlineMembers()) {
            fp.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&lAlerts &7» &aWalls haven't been checked in " + finalTimeSinceCheck + " minute(s)"));
        }
        return null;
    }
}
