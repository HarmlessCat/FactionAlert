package net.invasionpvp.beatingegirls.listeners;

import net.prosavage.factionsx.event.FactionCreateEvent;
import net.prosavage.factionsx.event.FactionDisbandEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class onDisband implements Listener {

    @EventHandler
    public void factionDisband(FactionDisbandEvent event) {
        YamlConfiguration AlertData = net.invasionpvp.beatingegirls.FAlert.getAlertFileYaml();
        AlertData.set(event.getFaction().getTag(), null);
        net.invasionpvp.beatingegirls.FAlert.save();
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&d&lFactions &7» &c"+ event.getFaction().getTag() +" &f has been disbanded by &d"+ event.getFPlayer().getPlayer().getName()+"&f!"));
        }
    }
    @EventHandler
    public void factionCreateEvent(FactionCreateEvent info) {
        YamlConfiguration AlertData = net.invasionpvp.beatingegirls.FAlert.getAlertFileYaml();
        AlertData.createSection(info.getFaction().getTag());
        AlertData.getConfigurationSection(info.getFaction().getTag()).createSection("Timer").set("Timer", 1);
        AlertData.getConfigurationSection(info.getFaction().getTag()).createSection("Toggle").set("Toggle", false);
        net.invasionpvp.beatingegirls.FAlert.save();
    }

}
