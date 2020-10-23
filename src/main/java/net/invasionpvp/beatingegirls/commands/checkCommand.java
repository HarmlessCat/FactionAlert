package net.invasionpvp.beatingegirls.commands;

import net.prosavage.factionsx.command.engine.CommandInfo;
import net.prosavage.factionsx.command.engine.CommandRequirementsBuilder;
import net.prosavage.factionsx.command.engine.FCommand;
import net.prosavage.factionsx.core.FPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class checkCommand extends FCommand {

    public checkCommand() {
        getAliases().add("check");

        this.commandRequirements = new CommandRequirementsBuilder().asPlayer(true).asFactionMember(true).build();
    }
    public HashMap<Player, Long> cooldown = new HashMap<>();
    @Override
    public boolean execute(CommandInfo info) {
        if (cooldown.containsKey(info.getPlayer()) && cooldown.get(info.getPlayer()) > System.currentTimeMillis()){
            long timeRemain = cooldown.get(info.getPlayer()) - System.currentTimeMillis();
            int intRemain = (int) (timeRemain / 1000);
            info.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lAlerts &7» &cYou cannot use this command for another &l"+ intRemain +" &csecond(s)."));
        } else {
            cooldown.put(info.getPlayer(), System.currentTimeMillis() + 60 * 1000);
            for (FPlayer fp : info.getFPlayer().getFaction().getOnlineMembers()) {
                net.invasionpvp.beatingegirls.timer.timer.resetCheckTime();
                fp.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&lAlerts &7» &a&l" + info.getPlayer().getDisplayName() + " &ahas checked the walls"));
            }
            net.invasionpvp.beatingegirls.random.addCheck(info);
        }
        return false;
    }

    @Override
    public String getHelpInfo() {
        return "Faction alerts Check";
    }
}
