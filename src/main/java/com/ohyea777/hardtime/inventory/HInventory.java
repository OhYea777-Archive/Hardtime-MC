package com.ohyea777.hardtime.inventory;

import net.minecraft.server.v1_7_R3.IInventory;
import org.bukkit.craftbukkit.v1_7_R3.inventory.CraftInventoryCustom;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

public class HInventory extends CraftInventoryCustom {

    private HIInventory hInventory;

    public HInventory(InventoryHolder owner, InventoryType type) {
        super(owner, type);

        hInventory = new HIInventory(inventory);
    }

    public HInventory(InventoryHolder owner, InventoryType type, String title) {
        super(owner, type, title);

        hInventory = new HIInventory(inventory);
    }

    public HInventory(InventoryHolder owner, int size) {
        super(owner, size);

        hInventory = new HIInventory(inventory);
    }

    public HInventory(InventoryHolder owner, int size, String title) {
        super(owner, size, title);

        hInventory = new HIInventory(inventory);
    }

    @Override
    public IInventory getInventory() {
        return hInventory;
    }

}
