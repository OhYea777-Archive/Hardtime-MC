package com.ohyea777.hardtime.commands;

import com.ohyea777.hardtime.utils.ConfigUtils;
import net.minecraft.server.v1_7_R3.ChatSerializer;
import net.minecraft.server.v1_7_R3.IChatBaseComponent;
import net.minecraft.server.v1_7_R3.PacketPlayOutChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandRegistry {

    private static final int COMMANDS_PER_PAGE = 5;

    private List<HCommand> commandList;
    private Map<String, HCommand> commands;

    public CommandRegistry() {
        this.commandList = new ArrayList<HCommand>();
        this.commands = new HashMap<String, HCommand>();

        init();
    }

    public void init() {
        registerCommand(new ReloadComand());
        registerCommand(new SpawnCommand());
        registerCommand(new ListItemsCommand());
    }

    public void registerCommand(HCommand command) {
        if (!commandList.contains(command))
            commandList.add(command);

        registerWithoutHelp(command);
    }

    public void registerWithoutHelp(HCommand command) {
        for (String alias : command.getAliases())
            if (!commands.containsKey(alias))
                commands.put(alias.toLowerCase(), command);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("?"))) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Players Only!");

                return true;
            }

            Player player = (Player) sender;

            getHelp(player, 1);

            return true;
        } else if (args.length == 2 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("?"))) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Players Only!");

                return true;
            }

            Player player = (Player) sender;
            int page = 1;

            try {
                page = Integer.valueOf(args[1]);
            } catch (NumberFormatException e) { }

            getHelp(player, page);

            return true;
        } else if (args.length == 2 && (args[1].equalsIgnoreCase("help") || args[1].equalsIgnoreCase("h") || args[1].equalsIgnoreCase("?"))) {
            if (commands.containsKey(args[0].toLowerCase())) {
                sender.sendMessage(commands.get(args[0].toLowerCase()).getHelp());
                return true;
            } else {
                sender.sendMessage(ConfigUtils.INSTANCE.getString("invalid command", true));
                return true;
            }
        } else if (args.length >= 1 && !args[0].equalsIgnoreCase("about")) {
            if (commands.containsKey(args[0].toLowerCase()))
                if (!commands.get(args[0].toLowerCase()).usesPermission() || sender.hasPermission(commands.get(args[0].toLowerCase()).getPermission()))
                    return commands.get(args[0].toLowerCase()).onCommand(sender, cmd, label, args);
                else {
                    sender.sendMessage(ConfigUtils.INSTANCE.getString("no permission", true));
                    return true;
                }
            else {
                sender.sendMessage(ConfigUtils.INSTANCE.getString("invalid command", true));
                return true;
            }
        }

        sender.sendMessage(new String[] { ConfigUtils.INSTANCE.getStringReplace("%prefix% &6About&8:", "%prefix%", "prefix", true), ConfigUtils.INSTANCE.getHelpFormat("Lead Programmer", "OhYea777 @BukkitDev")});

        return true;
    }

    public void getHelp(Player player, int page) {
        int pages = commandList.size() / COMMANDS_PER_PAGE + 1;

        page = Math.min(page, commandList.size() / COMMANDS_PER_PAGE + 1) > 0 ? Math.min(page, commandList.size() / COMMANDS_PER_PAGE + 1) : 1;

        player.sendMessage(ConfigUtils.INSTANCE.getString("command help", true).replace("%name%", "Help").replace("%page%", String.valueOf(page)).replace("%pages%", String.valueOf(pages)));

        int helpLength = 1;

        for (int i = helpLength; i < helpLength + COMMANDS_PER_PAGE; i ++)
            if (commandList.size() > i - helpLength + page * COMMANDS_PER_PAGE - COMMANDS_PER_PAGE && i - helpLength + page * COMMANDS_PER_PAGE - COMMANDS_PER_PAGE > -1) {
                HCommand command = commandList.get(i - helpLength + page * COMMANDS_PER_PAGE - COMMANDS_PER_PAGE);

                if (command == null)
                    continue;

                sendChatComponent(player, getComponent(ConfigUtils.INSTANCE.getHelpFormat(command.getName(), "/ht " + command.getAliases()[0] + " help"), "/ht " + command.getAliases()[0] + " help"));
            }
    }

    public void sendChatComponent(Player player, IChatBaseComponent component) {
        PacketPlayOutChat packet = new PacketPlayOutChat(component, true);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public IChatBaseComponent getComponent(String text, String command) {
        return ChatSerializer.a(String.format("{\"text\":\"%s\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"%s\"}}", text, command));
    }

}