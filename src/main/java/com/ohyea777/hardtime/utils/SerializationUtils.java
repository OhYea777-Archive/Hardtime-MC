package com.ohyea777.hardtime.utils;

import com.ohyea777.hardtime.inventory.HIInventory;
import com.ohyea777.hardtime.inventory.HInventory;
import net.minecraft.server.v1_7_R3.MojangsonParser;
import net.minecraft.server.v1_7_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R3.inventory.CraftItemStack;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SerializationUtils {

    public static String serialize(ItemStack itemStack) {
        if (itemStack.getType() != Material.AIR)
            return serialize(CraftItemStack.asNMSCopy(itemStack));

        return "AIR";
    }

    public static String serialize(net.minecraft.server.v1_7_R3.ItemStack itemStack) {
        if (itemStack != null) {
            NBTTagCompound tag = new NBTTagCompound();

            itemStack.save(tag);

            return fixString(tag.toString());
        }

        return "AIR";
    }

    public static ItemStack deSerialize(String itemStack) {
        if (itemStack.equalsIgnoreCase("AIR"))
            return new ItemStack(Material.AIR);
        else {
            NBTTagCompound tag = (NBTTagCompound) MojangsonParser.parse(itemStack);

            Bukkit.broadcastMessage(tag.toString());

            return CraftItemStack.asCraftMirror(net.minecraft.server.v1_7_R3.ItemStack.createStack(tag));
        }
    }

    public static List<String> serializeHInventory(HInventory inventory) {
        return serializeHInventory((HIInventory) inventory.getInventory());
    }

    public static List<String> serializeHInventory(HIInventory inventory) {
        List<String> items = new ArrayList<String>();

        for (net.minecraft.server.v1_7_R3.ItemStack itemStack : inventory.getContents())
            items.add(serialize(itemStack));

        return items;
    }

    public static HInventory deSerializeHInventory(String items) {
        return deSerializeHInventory(GsonUtils.getGson().fromJson(items, ArrayList.class));
    }

    public static HInventory deSerializeHInventory(List<String> items) {
        HInventory inventory = new HInventory(null, InventoryType.CHEST, "Test");

        for (int i = 0; i < items.size(); i ++)
            if (!items.get(i).equalsIgnoreCase("AIR"))
                inventory.setItem(i, deSerialize(items.get(i)));

        return inventory;
    }

    public static String fixString(String tag) {
        String fixedString = "";

        for (int i = 0; i < tag.length(); i ++) {
            if (tag.charAt(i) == ',' && i + 1 < tag.length() && tag.charAt(i + 1) == '}')
                continue;

            fixedString += tag.charAt(i);
        }

        return fixedString;
    }

}
