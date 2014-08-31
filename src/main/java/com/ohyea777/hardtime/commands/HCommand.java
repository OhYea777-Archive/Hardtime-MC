package com.ohyea777.hardtime.commands;

import net.minecraft.server.v1_7_R3.ChatSerializer;
import net.minecraft.server.v1_7_R3.IChatBaseComponent;
import net.minecraft.server.v1_7_R3.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public abstract class HCommand {

    public abstract String getName();

    public String getCommand() {
        return "ht";
    }

    public abstract String[] getAliases();

    public abstract String[] getHelp();

    public boolean usesPermission() {
        return false;
    }

    public String getPermission() {
        return "";
    }

    public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    public void sendChatComponent(Player player, IChatBaseComponent component) {
        PacketPlayOutChat packet = new PacketPlayOutChat(component, true);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public IChatBaseComponent getComponent(String text, String command) {
        return ChatSerializer.a(String.format("{\"text\":\"%s\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"%s\"}}", text, command));
    }

    public String strip(String str) {
        return ChatColor.stripColor(str);
    }

}
