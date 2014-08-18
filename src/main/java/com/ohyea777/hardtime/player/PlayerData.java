package com.ohyea777.hardtime.player;

import com.ohyea777.hardtime.cell.Cell;
import com.ohyea777.hardtime.inventory.HIInventory;
import com.ohyea777.hardtime.utils.SerializationUtils;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class PlayerData {

    private UUID uuid;

    private boolean hasCell;
    private UUID cell;

    private List<String> storage;

    public PlayerData() { }

    public PlayerData(Player player, HIInventory inventory, Cell cell) {
        this.uuid = player.getUniqueId();
        this.storage = SerializationUtils.serializeHInventory(inventory);
        this.hasCell = true;
        this.cell = cell.getCellId();
    }

    public PlayerData(Player player, List<String> storage, Cell cell) {
        this.uuid = player.getUniqueId();
        this.storage = storage;
        this.hasCell = true;
        this.cell = cell.getCellId();
    }

    public boolean hasCell() {
        return hasCell && getCellId() != null;
    }

    public UUID getCellId() {
        return cell;
    }

}
