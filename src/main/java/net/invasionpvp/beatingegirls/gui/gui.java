package net.invasionpvp.beatingegirls.gui;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import net.prosavage.factionsx.FactionsX;
import net.prosavage.factionsx.core.FPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.Collections;

public class gui {

    public static void guiList(FPlayer p) {

        Inventory runeGui = Bukkit.createInventory(null, 54, ChatColor.WHITE + "Check list");


        //ItemStack Iron = new ItemStack(Material.IRON_INGOT, 1);
        //ItemStack Gold = new ItemStack(Material.GOLD_INGOT, 1);
        //ItemStack Diamond = new ItemStack(Material.DIAMOND, 1);
        //ItemMeta FillingsMeta = Fillings.getItemMeta();
        //ItemMeta CommonMeta = Iron.getItemMeta();
        //FillingsMeta.setDisplayName(" ");
        //CommonMeta.setDisplayName(ChatColor.GRAY + "Common Rune");
        //Fillings.setItemMeta(FillingsMeta);
        //Iron.setItemMeta(CommonMeta);
        //Gold.setItemMeta(CommonMeta);
        //Diamond.setItemMeta(CommonMeta);
        YamlConfiguration checks = net.invasionpvp.beatingegirls.FAlert.getCheckFileYaml();
        int[] IntList = {2, 3, 4, 5, 6, 11, 12, 13, 14, 15, 20, 21, 22, 23, 24, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42, 47, 48, 49, 50, 51};
        int[] IntListFillings = {0,1,7,8,9,10,16,17,18,19,25,26,27,28,34,35,36,37,43,44,45,46,52,53};
        int repeats = 0;
        for (FPlayer FP : p.getFaction().getMembers()) {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1 , (short) 3);
            ItemMeta ItemMeta = item.getItemMeta();
            ItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&a&l"+ FP.getName() +"&7 ("+checks.getConfigurationSection(FP.getFaction().getTag()).getConfigurationSection(FP.getName()).get("Checks")+")" ));
            SkullMeta meta = (SkullMeta) ItemMeta;
            meta.setOwner(FP.getName());
            item.setItemMeta(meta);
            runeGui.setItem(IntList[repeats], item);
            repeats++;
        }
        for (int i = 0;i < 24;i++) {
            ItemStack fillings = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
            ItemMeta fillingsMeta = fillings.getItemMeta();
            fillingsMeta.setDisplayName("");
            fillings.setItemMeta(fillingsMeta);
            runeGui.setItem(IntListFillings[i], fillings);
        }
        p.getPlayer().openInventory(runeGui);
    }
}
/*
            ItemStack Fillings = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
            ItemMeta FillingsMeta = Fillings.getItemMeta();
            FillingsMeta.setDisplayName(FP.getName());
            Fillings.setItemMeta(FillingsMeta);
 */