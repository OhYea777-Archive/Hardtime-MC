package com.ohyea777.hardtime.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SelectItem extends HItem {

    @Override
    public String getName() {
        return "&bSelection Item";
    }

    @Override
    public Material getMaterial() {
        return Material.BLAZE_ROD;
    }

    @Override
    public short getMeta() {
        return 0;
    }

    @Override
    public void onItemUse(Action action, PlayerInteractEvent event) {
        if (action.equals(Action.LEFT_CLICK_BLOCK))
            event.getPlayer().sendMessage("Selection 1: " + locToString(event.getClickedBlock().getLocation()));
        else if (action.equals(Action.RIGHT_CLICK_BLOCK))
            event.getPlayer().sendMessage("Selection 2: " + locToString(event.getClickedBlock().getLocation()));
        else
            event.getPlayer().sendMessage("Must be a clicked block");

        event.setCancelled(true);
    }

    @Override
    public void onItemRightClickEntity(PlayerInteractEntityEvent event) {
        event.getPlayer().sendMessage("Item Right Click Entity");
    }

    @Override
    public void onItemLeftClickEntity(Player player, EntityDamageByEntityEvent event) {
        player.sendMessage("Item Left Click Entity");
    }

    private String locToString(Location location) {
        return location.getWorld().getName() + " " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
    }

}
