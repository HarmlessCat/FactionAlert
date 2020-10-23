package net.invasionpvp.beatingegirls.AlertAction;

import com.cryptomorin.xseries.XMaterial;
import net.prosavage.factionsx.util.SerializableItem;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class item {
    public interface ActionItems {

        SerializableItem ALERTS = new SerializableItem(XMaterial.REDSTONE_LAMP, ChatColor.translateAlternateColorCodes('&',"&aUse Alerts"),
                Arrays.asList(ChatColor.translateAlternateColorCodes('&',"&7Click to &atoggle status."),
                        ChatColor.translateAlternateColorCodes('&',"&7Currently: {status}")), 1);
    }
}
