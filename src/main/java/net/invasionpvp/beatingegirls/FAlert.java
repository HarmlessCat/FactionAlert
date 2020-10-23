package net.invasionpvp.beatingegirls;

import net.invasionpvp.beatingegirls.AlertAction.AlertAction;
import net.invasionpvp.beatingegirls.AlertAction.item;
import net.invasionpvp.beatingegirls.commands.FactionAlertHelp;
import net.invasionpvp.beatingegirls.commands.checkCommand;
import net.invasionpvp.beatingegirls.listeners.guiListener;
import net.invasionpvp.beatingegirls.listeners.onDisband;
import net.prosavage.factionsx.FactionsX;
import net.prosavage.factionsx.addonframework.Addon;
import net.prosavage.factionsx.command.engine.CommandInfo;
import net.prosavage.factionsx.core.FPlayer;
import net.prosavage.factionsx.manager.SpecialActionManager;
import net.prosavage.factionsx.util.SpecialAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public final class FAlert extends Addon {

    private static Addon addonInstance;
    //ALERT FILE
    private static File Alert;
    private static YamlConfiguration modifyAlert;
    //CHECK FILE
    private static File Check;
    private static YamlConfiguration modifyCheck;

    //AddonInstence
    public static Addon getAddonInstance() {
        return addonInstance;
    }

    public static YamlConfiguration getCheckFileYaml() { return modifyCheck; }
    //YML config
    public static YamlConfiguration getAlertFileYaml() { return modifyAlert; }

    //Main instance
    private static FAlert main;
    SpecialAction alertsAction = new AlertAction("&ause_alerts", item.ActionItems.ALERTS);
    //Main instance
    public SpecialAction getSpecialAction() { return alertsAction; }
    public static FAlert getInstance() { return main; }
    @Override
    public void onEnable() {
        //Main instance
        FAlert.main = this;
        addonInstance = this;
        //SYSOUT
        logColored("Enabling F Alert-Addon!");
        //SYSOUT
        logColored("Adding Alert Help Command");
        //REGISTER COMMAND
        FactionsX.baseCommand.addSubCommand(new FactionAlertHelp());
        FactionsX.baseCommand.addSubCommand(new checkCommand());
        FactionsX.instance.registerListeners(new guiListener());
        //REGISTER LISTENER
        FactionsX.instance.registerListeners(new onDisband());
        SpecialActionManager.INSTANCE.addSpecialAction(alertsAction);

        //DOWNLOADFILES
        initiateFiles();
        initiateCheckTop();
        //SAVE FILES
        save();
    }
    //------------------------------------------INITIATE CHECK TOP
    private void initiateCheckTop() {
        Check = new File(addonInstance.getAddonDataFolder(), "CheckTop.Yml");
        if (!Check.exists()) {
            try {
                Check.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        modifyCheck = YamlConfiguration.loadConfiguration(Check);
    }
    //------------------------------------------INITIATE ALERTS
    private void initiateFiles() {
        Alert = new File(addonInstance.getAddonDataFolder(), "AlertData.Yml");
        if (!Alert.exists()) {
            try {
                Alert.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        modifyAlert = YamlConfiguration.loadConfiguration(Alert);
    }
    public static File getCheckFile() { return Check; }
    public static File getAlertFile() { return Alert; }
    //------------------------------------------ONDISABLE
    @Override
    public void onDisable() {
        logColored("Disabling F Alert-Addon!");
        SpecialActionManager.INSTANCE.removeSpecialAction(alertsAction);
        FactionsX.baseCommand.addSubCommand(new FactionAlertHelp());
        save();
    }
    //------------------------------------------SAVE
    public static void save() {
        try {
            FAlert.getAlertFileYaml().save(FAlert.getAlertFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FAlert.getCheckFileYaml().save(FAlert.getCheckFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Integer timeSinceCheck = 0;
    public void resetCheck() {
        timeSinceCheck = 0;
    }
    //------------------------------------------RUNNABLE
}
