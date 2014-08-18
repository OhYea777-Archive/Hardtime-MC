package com.ohyea777.hardtime.utils;

import net.minecraft.server.v1_7_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_7_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_7_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class SerializationUtils {

    public static String serializeItem(ItemStack item) {
        net.minecraft.server.v1_7_R3.ItemStack nms = CraftItemStack.asNMSCopy(item);

        if (nms == null) {
            return "NMS is  null!";
        }

        NBTTagCompound compound = new NBTTagCompound();
        nms.save(compound);

        return GsonUtils.getGson().toJson(compound.toString().replace("\"", ""));
    }

    public static ItemStack deserializeItem(String item) {
        NBTTagCompound compound = GsonUtils.getGson().fromJson(item, NBTTagCompound.class);

        return CraftItemStack.asBukkitCopy(net.minecraft.server.v1_7_R3.ItemStack.createStack(compound));
    }

}
