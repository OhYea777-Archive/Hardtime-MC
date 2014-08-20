package com.ohyea777.hardtime.commands;

import com.ohyea777.hardtime.Hardtime;
import com.ohyea777.hardtime.items.IItem;
import com.ohyea777.hardtime.utils.ConfigUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ListItemsCommand extends HCommand {

    private static final int ITEMS_PER_PAGE = 5;

    @Override
    public String getName() {
        return "List";
    }

    @Override
    public String[] getAliases() {
        return new String[] { "items", "listitems", "allitems", "listallitems", "listi", "itemlist", "litems" };
    }

    @Override
    public String[] getHelp() {
        return new String[] { ConfigUtils.INSTANCE.getStringReplace("%prefix% &6" + getName() + " Help&8:", "%prefix%", "prefix", true), ConfigUtils.INSTANCE.getHelpFormat("List", "To List All Items Use /ht items") };
    }

    @Override
    public boolean usesPermission() {
        return true;
    }

    @Override
    public String getPermission() {
        return "ht.itemlist";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Players Only!");

            return true;
        }

        Player player = (Player) sender;

        if (args.length == 3) {
            int page = 1;

            try {
                page = Integer.valueOf(args[2]);
            } catch (NumberFormatException e) { }

            getHelp(page, player);

            return true;
        }

        getHelp(1, player);

        return true;
    }

    public void getHelp(int page, Player player) {
        List<IItem> itemList = new ArrayList<IItem>();

        for (IItem item : Hardtime.INSTANCE.getItemRegistry().getItems())
            if (item.hasPermission(player))
                itemList.add(item);

        int pages = itemList.size() / ITEMS_PER_PAGE + 1;

        page = Math.min(page, itemList.size() / ITEMS_PER_PAGE + 1) > 0 ? Math.min(page, itemList.size() / ITEMS_PER_PAGE + 1) : 1;

        player.sendMessage(ConfigUtils.INSTANCE.getString("command help", true).replace("%name%", "Item List").replace("%page%", String.valueOf(page)).replace("%pages%", String.valueOf(pages)));

        int helpLength = 1;

        for (int i = helpLength; i < helpLength + ITEMS_PER_PAGE; i ++)
            if (itemList.size() > i - helpLength + page * ITEMS_PER_PAGE - ITEMS_PER_PAGE && i - helpLength + page * ITEMS_PER_PAGE - ITEMS_PER_PAGE > -1) {
                IItem item = itemList.get(i - helpLength + page * ITEMS_PER_PAGE - ITEMS_PER_PAGE);

                if (item == null)
                    continue;

                sendChatComponent(player, getComponent(ConfigUtils.INSTANCE.getHelpFormat(strip(item.getLocalisedName()), "To Spawn Use /ht spawn " + strip(item.getLocalisedName())), "/ht spawn " + strip(item.getLocalisedName())));
            }
    }

}
