package net.invasionpvp.beatingegirls;

import net.prosavage.factionsx.command.engine.CommandInfo;
import org.bukkit.configuration.file.YamlConfiguration;

public class random {

    public static void addCheck(CommandInfo info) {
        YamlConfiguration CheckData = net.invasionpvp.beatingegirls.FAlert.getCheckFileYaml();
        if (CheckData.isConfigurationSection(info.getFaction().getTag())) {
            if (CheckData.getConfigurationSection(info.getFaction().getTag()).isConfigurationSection(info.getPlayer().getDisplayName())) {
                if (CheckData.getConfigurationSection(info.getFaction().getTag()).getConfigurationSection(info.getPlayer().getDisplayName()).isInt("Checks")) {
                    //Integer prevValue = CheckData.getConfigurationSection(info.getFaction().getTag()).getConfigurationSection(info.getPlayer().getDisplayName()).getInt("Checks");
                    CheckData.getConfigurationSection(info.getFaction().getTag()).getConfigurationSection(info.getPlayer().getDisplayName()).set("Checks", (CheckData.getConfigurationSection(info.getFaction().getTag()).getConfigurationSection(info.getPlayer().getDisplayName()).getInt("Checks") + 1));
                    net.invasionpvp.beatingegirls.FAlert.save();
                } else {
                    CheckData.getConfigurationSection(info.getFaction().getTag()).getConfigurationSection(info.getPlayer().getDisplayName()).set("Checks", 0);
                    addCheck(info);
                }
            } else {
                CheckData.getConfigurationSection(info.getFaction().getTag()).createSection(info.getPlayer().getDisplayName());
                addCheck(info);
            }
        } else {
            CheckData.createSection(info.getFaction().getTag());
            addCheck(info);
        }
    }
}
