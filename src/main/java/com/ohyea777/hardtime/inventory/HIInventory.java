package com.ohyea777.hardtime.inventory;

import net.minecraft.server.v1_7_R3.EntityHuman;
import net.minecraft.server.v1_7_R3.IInventory;
import net.minecraft.server.v1_7_R3.ItemStack;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;

public class HIInventory implements IInventory {

    private IInventory inventory;

    public HIInventory(IInventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public int getSize() {
        return inventory.getSize();
    }

    @Override
    public ItemStack getItem(int i) {
        return inventory.getItem(i);
    }

    @Override
    public ItemStack splitStack(int i, int i2) {
        return inventory.splitStack(i, i2);
    }

    @Override
    public ItemStack splitWithoutUpdate(int i) {
        return inventory.splitWithoutUpdate(i);
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        inventory.setItem(i, itemStack);
    }

    @Override
    public String getInventoryName() {
        return inventory.getInventoryName();
    }

    @Override
    public boolean k_() {
        return inventory.k_();
    }

    @Override
    public int getMaxStackSize() {
        return inventory.getMaxStackSize();
    }

    @Override
    public void update() {
        inventory.update();
    }

    @Override
    public boolean a(EntityHuman entityHuman) {
        return inventory.a(entityHuman);
    }

    @Override
    public void startOpen() {
        inventory.startOpen();
    }

    @Override
    public void l_() {
        inventory.l_();
    }

    @Override
    public boolean b(int i, ItemStack itemStack) {
        return inventory.b(i, itemStack);
    }

    @Override
    public ItemStack[] getContents() {
        return inventory.getContents();
    }

    @Override
    public void onOpen(CraftHumanEntity craftHumanEntity) {
        inventory.onOpen(craftHumanEntity);
    }

    @Override
    public void onClose(CraftHumanEntity craftHumanEntity) {
        inventory.onClose(craftHumanEntity);
    }

    @Override
    public List<HumanEntity> getViewers() {
        return inventory.getViewers();
    }

    @Override
    public InventoryHolder getOwner() {
        return inventory.getOwner();
    }

    @Override
    public void setMaxStackSize(int i) {
        inventory.getMaxStackSize();
    }

}
