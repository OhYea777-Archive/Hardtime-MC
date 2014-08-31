package com.ohyea777.hardtime.commands;

import com.ohyea777.hardtime.Hardtime;
import com.ohyea777.hardtime.libs.reflections.Reflections;
import com.ohyea777.hardtime.utils.ConfigUtils;
import net.minecraft.server.v1_7_R3.ChatSerializer;
import net.minecraft.server.v1_7_R3.IChatBaseComponent;
import net.minecraft.server.v1_7_R3.PacketPlayOutChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandRegistry implements CommandExecutor {

    private static final int COMMANDS_PER_PAGE = 5;

    private List<HCommand> commandList;
    private Map<String, HCommand> commands;
    private Map<String, HCommand> customCommands;

    public CommandRegistry() {
        this.commandList = new ArrayList<HCommand>();
        this.commands = new HashMap<String, HCommand>();
        this.customCommands = new HashMap<String, HCommand>();

        init();
    }

    public void init() {
        Reflections reflections = new Reflections(getClass().getPackage().getName());

        for (Class<? extends HCommand> clz : reflections.getSubTypesOf(HCommand.class)) {
            if (clz.isAnnotationPresent(CommandHandler.class)) {
                try {
                    HCommand command = clz.newInstance();

                    if (clz.getAnnotation(CommandHandler.class).value()) {
                        registerWithoutHelp(command);
                    } else {
                        registerCommand(command);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void registerCommand(HCommand command) {
        if (!commandList.contains(command)) {
            commandList.add(command);

            if (command.getCommand().equalsIgnoreCase("ht")){
                registerWithoutHelp(command);
            } else {
                CommandRegistrationFactory.buildCommand(command.getCommand()).withAliases(command.getAliases()).withPlugin(Hardtime.INSTANCE).withCommandExecutor(this).build();

                commands.put(command.getCommand().toLowerCase(), command);
            }
        }
    }

    public void registerWithoutHelp(HCommand command) {
        for (String alias : command.getAliases())
            if (!commands.containsKey(alias))
                commands.put(alias.toLowerCase(), command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ht")) {
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
                } catch (NumberFormatException e) {
                }

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
        } else if (customCommands.containsKey(cmd.getName().toLowerCase())) {
            if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("?"))) {
                if (customCommands.containsKey(cmd.getName().toLowerCase())) {
                    sender.sendMessage(customCommands.get(cmd.getName().toLowerCase()).getHelp());
                    return true;
                } else {
                    sender.sendMessage(ConfigUtils.INSTANCE.getString("invalid command", true));
                    return true;
                }
            } else if (!customCommands.get(cmd.getName().toLowerCase()).usesPermission() || sender.hasPermission(customCommands.get(cmd.getName().toLowerCase()).getPermission()))
                return customCommands.get(cmd.getName().toLowerCase()).onCommand(sender, cmd, label, args);
            else {
                sender.sendMessage(ConfigUtils.INSTANCE.getString("no permission", true));
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

                sendChatComponent(player, getComponent(ConfigUtils.INSTANCE.getHelpFormat(command.getName(), "/" + command.getCommand() != "ht" ? command.getCommand() : ("ht " + command.getAliases()[0]) + " help"), "/" + command.getCommand() != "ht" ? command.getCommand() : ("ht " + command.getAliases()[0]) + " help"));
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
