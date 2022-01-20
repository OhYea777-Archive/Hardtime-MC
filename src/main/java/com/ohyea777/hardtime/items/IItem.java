package com.ohyea777.hardtime.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface IItem extends Listener {

    public String getName();

    public String getLocalisedName();

    public ItemType getItemType();

    // Item Details

    public Material getMaterial();

    public short getMeta();

    // Create New Item

    public ItemStack createItem();

    // Item Permissions

    public boolean usesPermissions();

    public String getPermission();

    public boolean hasPermission(Player player);

    // Item Events

    public void onItemUse(Action action, PlayerInteractEvent event);

    public void onItemRightClickEntity(PlayerInteractEntityEvent event);

    public void onItemLeftClickEntity(Player player, EntityDamageByEntityEvent event);

    public enum ItemType {

        TOOL, WEAPON, DEBUG, UNKNOWN;

    }

}
