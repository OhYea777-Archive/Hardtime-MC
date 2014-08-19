package com.ohyea777.hardtime;

import com.ohyea777.hardtime.block.BlockRegistry;
import com.ohyea777.hardtime.cell.CellRegistry;
import com.ohyea777.hardtime.items.ItemRegistry;
import com.ohyea777.hardtime.utils.VaultUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Hardtime extends JavaPlugin implements Listener {

    public static Hardtime INSTANCE;

    private BlockRegistry blockRegistry;
    private CellRegistry cellRegistry;
    private ItemRegistry itemRegistry;

    @Override
    public void onEnable() {
        INSTANCE = this;

        if (!VaultUtils.INSTANCE.init(INSTANCE)) {
            // TODO: Something on failure of initializing @VaultUtils
        }

        blockRegistry = new BlockRegistry();
        cellRegistry = new CellRegistry();
        itemRegistry = new ItemRegistry();

        getServer().getPluginManager().registerEvents(INSTANCE, INSTANCE);
    }

    public BlockRegistry getBlockRegistry() {
        return blockRegistry;
    }

    public CellRegistry getCellRegistry() {
        return cellRegistry;
    }

    public ItemRegistry getItemRegistry() {
        return itemRegistry;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Players only!");

            return true;
        }

        Player player = (Player) sender;

        player.setItemInHand(getItemRegistry().getItem("Selection Item").createItem());

        return true;
    }
}
