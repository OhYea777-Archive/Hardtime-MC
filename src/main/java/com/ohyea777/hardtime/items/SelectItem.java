package com.ohyea777.hardtime.items;

import com.ohyea777.hardtime.utils.ConfigUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

public class SelectItem extends HItem {

    private Map<Player, Location> selOne;
    private Map<Player, Location> selTwo;

    public SelectItem() {
        selOne = new HashMap<Player, Location>();
        selTwo = new HashMap<Player, Location>();
    }

    @Override
    public String getName() {
        return "&6Selection Item";
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
    public boolean usesPermissions() {
        return true;
    }

    @Override
    public String getPermission() {
        return "ht.item.selectionitem";
    }

    @Override
    public void onItemUse(Action action, PlayerInteractEvent event) {
        if (action.equals(Action.LEFT_CLICK_BLOCK)) {
            event.getPlayer().sendMessage(ConfigUtils.INSTANCE.getStringReplace("%prefix% Selection 1: " + locToString(event.getClickedBlock().getLocation()), "%prefix%", "prefix", true));
            selOne.put(event.getPlayer(), event.getClickedBlock().getLocation());
        } else if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
            event.getPlayer().sendMessage(ConfigUtils.INSTANCE.getStringReplace("%prefix% Selection 2: " + locToString(event.getClickedBlock().getLocation()), "%prefix%", "prefix", true));
            selTwo.put(event.getPlayer(), event.getClickedBlock().getLocation());
        } else
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

    public Location[] clearSelections(Player player) {
        return new Location[] { clearSelectionOne(player), clearSelectionTwo(player) };
    }

    public Location clearSelectionOne(Player player) {
        return selOne.containsKey(player) ? selOne.remove(player) : null;
    }

    public Location clearSelectionTwo(Player player) {
        return selTwo.containsKey(player) ? selOne.remove(player) : null;
    }

    public SelectionResult getSelectionResult(Player player) {
        SelectionResult result = SelectionResult.NO_SELECTION;

        if (selOne.containsKey(player))
            result = SelectionResult.ONE_ONLY;

        if (selTwo.containsKey(player))
            result = result == SelectionResult.ONE_ONLY ? SelectionResult.COMPLETE : SelectionResult.TWO_ONLY;

        return result;
    }

    public enum SelectionResult {
        NO_SELECTION, ONE_ONLY, TWO_ONLY, COMPLETE;
    }

}
