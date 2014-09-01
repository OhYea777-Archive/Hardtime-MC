package com.ohyea777.hardtime.commands;

import com.ohyea777.hardtime.Hardtime;
import com.ohyea777.hardtime.items.IItem;
import com.ohyea777.hardtime.items.ItemRegistry;
import com.ohyea777.hardtime.utils.ConfigUtils;
import com.ohyea777.hardtime.utils.SerializationUtils;
import net.minecraft.server.v1_7_R3.ChatSerializer;
import net.minecraft.server.v1_7_R3.IChatBaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandHandler(true)
public class SpawnCommand extends HCommand {

    @Override
    public String getName() {
        return "Spawn Item";
    }

    @Override
    public String[] getAliases() {
        return new String[] { "spawn", "spawnitem", "sitem", "spawni", "si", "s" };
    }

    @Override
    public String[] getHelp() {
        return new String[] { ConfigUtils.INSTANCE.getStringReplace("%prefix% &6" + getName() + " Help&8:", "%prefix%", "prefix", true), ConfigUtils.INSTANCE.getHelpFormat("Spawn", "To Spawn an Item Use /ht spawn <name>") };
    }

    @Override
    public boolean usesPermission() {
        return true;
    }

    @Override
    public String getPermission() {
        return "ht.spawn";
    }

    public ItemRegistry getItemRegistry() {
        return Hardtime.INSTANCE.getItemRegistry();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Players Only!");

            return true;
        }

        Player player = (Player) sender;

        if (getItemRegistry().containsItem(patchArgs(args))) {
            IItem item = getItemRegistry().getItem(patchArgs(args));

            if (item.hasPermission(player)) {
                sendChatComponent(player, getJson(ConfigUtils.INSTANCE.getString("spawned item", true).replace("%item%", ChatColor.stripColor(item.getLocalisedName())), item));
                player.getInventory().addItem(item.createItem());
            } else
                sender.sendMessage(ConfigUtils.INSTANCE.getString("no permission", true));

            return true;
        }

        sender.sendMessage(ConfigUtils.INSTANCE.getString("no permission", true));

        return true;
    }

    private IChatBaseComponent getJson(String text, IItem item) {
        return ChatSerializer.a(String.format("{\"text\":\"%s\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"%s\"}}", text, SerializationUtils.serialize(item.createItem()).replace("\"", "\\\"")));
    }

    private String patchArgs(String[] args) {
        StringBuilder builder = new StringBuilder(args.length >= 2 ? args[1] : "");

        if (args.length > 2)
            for (int i = 2; i < args.length; i ++)
                builder.append(" ").append(args[i]);

        return builder.toString();
    }

}
