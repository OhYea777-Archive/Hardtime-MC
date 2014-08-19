package com.ohyea777.hardtime.items;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class HItem implements IItem {

    @Override
    public String getLocalisedName() {
        return ChatColor.translateAlternateColorCodes('&', getName());
    }

    @Override
    public ItemType getItemType() {
        return ItemType.DEBUG;
    }

    @Override
    public ItemStack createItem() {
        ItemStack itemStack = new ItemStack(getMaterial(), 1, getMeta());
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(getLocalisedName());
        meta.setLore(new ArrayList<String>(Arrays.asList(strip(getLocalisedName()))));
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    @Override
    public boolean usesPermissions() {
        return false;
    }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public boolean hasPermission(Player player) {
        return !usesPermissions() || getPermission().isEmpty() || player.hasPermission(getPermission());
    }

    private String strip(String str) {
        return ChatColor.stripColor(str);
    }

}
