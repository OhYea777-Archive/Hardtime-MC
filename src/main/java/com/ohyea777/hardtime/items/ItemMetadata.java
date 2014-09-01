package com.ohyea777.hardtime.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemMetadata {

    public static final String PREFIX = "-=[%%]=-";

    public static void set(ItemStack itemStack, String key, String value) {
        if (!isAir(itemStack)) {
            ItemMeta meta = itemStack.getItemMeta();
            List<String> lore = null;

            if (itemStack.getItemMeta().hasLore()) {
                lore = itemStack.getItemMeta().getLore();
            } else {
                lore = new ArrayList<String>();
            }

            lore.add(PREFIX + key.toLowerCase() + ": " + value);
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
        }
    }

    public static boolean hasMetadata(ItemStack itemStack) {
        if (!isAir(itemStack)) {
            if (itemStack.getItemMeta().hasLore()) {
                for (String lore : itemStack.getItemMeta().getLore()) {
                    if (lore.startsWith(PREFIX) && lore.contains(" "))
                        return true;
                }
            }
        }

        return false;
    }

    public static boolean hasMetadata(ItemStack itemStack, String key) {
        if (!isAir(itemStack)) {
            if (itemStack.getItemMeta().hasLore()) {
                for (String lore : itemStack.getItemMeta().getLore()) {
                    if (lore.startsWith(PREFIX) && lore.contains(" ") && lore.contains(key.toLowerCase()))
                        return true;
                }
            }
        }

        return false;
    }

    public static String get(ItemStack itemStack, String key) {
        if (!isAir(itemStack)) {
            if (itemStack.getItemMeta().hasLore()) {
                for (String lore : itemStack.getItemMeta().getLore()) {
                    if (isMetadata(lore, key))
                        return lore.replace(PREFIX, "").split(": ")[1];
                }
            }
        }

        return "";
    }

    public static boolean isMetadata(String lore, String key) {
        return lore.startsWith(PREFIX) && lore.contains(key.toLowerCase()) && lore.contains(": ") && lore.split(":").length == 2;
    }

    private static boolean isAir(ItemStack itemStack) {
        return itemStack == null || itemStack.getType().equals(Material.AIR);
    }

}
