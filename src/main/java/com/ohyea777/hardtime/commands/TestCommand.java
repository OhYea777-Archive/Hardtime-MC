package com.ohyea777.hardtime.commands;

import com.ohyea777.hardtime.utils.ConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandHandler
public class TestCommand extends HCommand {

    @Override
    public String getName() {
        return "Test";
    }

    @Override
    public String[] getAliases() {
        return new String[] { "test", "t" };
    }

    @Override
    public String[] getHelp() {
        return new String[] {ConfigUtils.INSTANCE.getStringReplace("%prefix% &6" + getName() + " Help&8:", "%prefix%", "prefix", true), ConfigUtils.INSTANCE.getHelpFormat("Test", "To Test Stuffs Use: /ht test") };
    }

    @Override
    public boolean usesPermission() {
        return true;
    }

    @Override
    public String getPermission() {
        return "ht.test";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Players Only!");

            return true;
        }

        return true;
    }

}
