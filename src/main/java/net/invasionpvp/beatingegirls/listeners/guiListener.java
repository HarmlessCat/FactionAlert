package net.invasionpvp.beatingegirls.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class guiListener implements Listener {

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        if (!(e.getClickedInventory() == null)) {
            if (ChatColor.translateAlternateColorCodes('&', e.getClickedInventory().getTitle()).equals(ChatColor.WHITE + "Check list")) {
                if (e.getCurrentItem() != null) {
                    e.setCancelled(true);
                }
            }
        }
    }
}

