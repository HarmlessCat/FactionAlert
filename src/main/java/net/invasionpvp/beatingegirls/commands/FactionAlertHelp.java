package net.invasionpvp.beatingegirls.commands;

import net.invasionpvp.beatingegirls.FAlert;
import net.prosavage.factionsx.command.engine.CommandInfo;
import net.prosavage.factionsx.command.engine.CommandRequirementsBuilder;
import net.prosavage.factionsx.command.engine.FCommand;
import net.prosavage.factionsx.core.FPlayer;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import static net.invasionpvp.beatingegirls.gui.gui.guiList;

public class FactionAlertHelp extends FCommand {


    public FactionAlertHelp() {
        getAliases().add("alerts");
        getRequiredArgs().add(new Argument("toggle/set/help", 0, new StringArgument()));
        getOptionalArgs().add(new Argument("timer", 1, new IntArgument()));

        this.commandRequirements = new CommandRequirementsBuilder().asPlayer(true).asFactionMember(true).build();
    }

    public String getHelpInfo() {
        return ChatColor.WHITE + "Faction alerts Toggle/Set, /f alerts help";
    }
        //f alerts set <timer>
    public boolean execute(CommandInfo info) {
        YamlConfiguration AlertData = net.invasionpvp.beatingegirls.FAlert.getAlertFileYaml();
        Player p = info.getPlayer();
        if (info.getFPlayer().canDoSpecialAction(net.invasionpvp.beatingegirls.FAlert.getInstance().getSpecialAction())) {
            if (info.getArgs().get(0).equals("toggle")) {
                toggleAlerts(info);
                if (AlertData.getConfigurationSection(info.getFaction().getTag()).getConfigurationSection("Toggle").getBoolean("Toggle")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&lAlerts &7» &fFaction Alerts &a&lENABLED"));
                    net.invasionpvp.beatingegirls.timer.timer.start(info);
                }
                if (!AlertData.getConfigurationSection(info.getFaction().getTag()).getConfigurationSection("Toggle").getBoolean("Toggle")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&lAlerts &7» &fFaction Alerts &c&lDISABLED"));
                    net.invasionpvp.beatingegirls.timer.timer.stop(info);
                }
            } else if (info.getArgs().get(0).equals("enable")) {
                net.invasionpvp.beatingegirls.timer.timer.start(info);
            } else if (info.getArgs().get(0).equals("disable")) {
                net.invasionpvp.beatingegirls.timer.timer.stop(info);
            }  if (info.getArgs().get(0).equals("list")) {
                guiList(info.getFPlayer());
            } else if (info.getArgs().get(0).equals("help")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /f alerts set <timer>s, /f alerts toggle"));
            } else if (info.getArgs().get(0).equals("set")){
                if (info.getArgs().size() == 2) {
                    if (isInteger(info.getArgs().get(1))) {
                        if (Integer.parseInt(info.getArgs().get(1)) <= 5 && Integer.parseInt(info.getArgs().get(1)) >= 1) {
                            if (info.getFPlayer().getFaction().getLeader() == info.getFPlayer()) {
                                AlertData.getConfigurationSection(info.getFaction().getTag()).getConfigurationSection("Toggle").set("Toggle", false);
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&lAlerts &7» &fFaction Alerts &c&lDISABLED"));
                                net.invasionpvp.beatingegirls.FAlert.save();
                                net.invasionpvp.beatingegirls.timer.timer.stop(info);
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&lAlerts &7» &aAlert timer set to: " + info.getArgs().get(1) + " minute(s)" ));
                                AlertData.createSection(info.getFaction().getTag());
                                AlertData.getConfigurationSection(info.getFaction().getTag()).createSection("Timer").set("Timer", Integer.parseInt(info.getArgs().get(1)));
                                AlertData.getConfigurationSection(info.getFaction().getTag()).createSection("Toggle").set("Toggle", false);
                                net.invasionpvp.beatingegirls.FAlert.save();
                            } else {
                                p.sendMessage("you need to be the leader!");
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public void toggleAlerts(CommandInfo info) {
        YamlConfiguration AlertData = net.invasionpvp.beatingegirls.FAlert.getAlertFileYaml();
        String f = info.getFaction().getTag();
        if (AlertData.getConfigurationSection(f).getConfigurationSection("Toggle").getBoolean("Toggle")) {
            AlertData.getConfigurationSection(f).getConfigurationSection("Toggle").set("Toggle", false);
        } else if (!AlertData.getConfigurationSection(f).getConfigurationSection("Toggle").getBoolean("Toggle")) {
            AlertData.getConfigurationSection(f).getConfigurationSection("Toggle").set("Toggle", true);
        }
        net.invasionpvp.beatingegirls.FAlert.save();
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
