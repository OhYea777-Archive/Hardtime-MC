package com.ohyea777.hardtime.commands;

import com.ohyea777.hardtime.Hardtime;
import com.ohyea777.hardtime.utils.ConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandHandler(true)
public class ReloadComand extends HCommand {

    @Override
    public String getName() {
        return "Reload";
    }

    @Override
    public String[] getAliases() {
        return new String[] { "reload", "r" };
    }

    @Override
    public String[] getHelp() {
        return new String[] {ConfigUtils.INSTANCE.getStringReplace("%prefix% &6" + getName() + " Help&8:", "%prefix%", "prefix", true), ConfigUtils.INSTANCE.getHelpFormat("Reload", "To Reload Configurations Use /ht reload") };
    }

    @Override
    public boolean usesPermission() {
        return true;
    }

    @Override
    public String getPermission() {
        return "ht.reload";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage(ConfigUtils.INSTANCE.getString("reloaded", true));
        Hardtime.INSTANCE.reload();

        return true;
    }

}
