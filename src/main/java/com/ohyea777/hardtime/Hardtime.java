package com.ohyea777.hardtime;

import com.ohyea777.hardtime.block.BlockRegistry;
import com.ohyea777.hardtime.cell.CellRegistry;
import com.ohyea777.hardtime.utils.SerializationUtils;
import com.ohyea777.hardtime.utils.VaultUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Hardtime extends JavaPlugin {

    public static Hardtime INSTANCE;

    private BlockRegistry blockRegistry;
    private CellRegistry cellRegistry;

    @Override
    public void onEnable() {
        INSTANCE = this;

        if (!VaultUtils.INSTANCE.init(INSTANCE)) {
            // TODO: Something on failure of initializing @VaultUtils
        }

        blockRegistry = new BlockRegistry();
        cellRegistry = new CellRegistry();
    }

    public BlockRegistry getBlockRegistry() {
        return blockRegistry;
    }

    public CellRegistry getCellRegistry() {
        return cellRegistry;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Players Only!");

            return true;
        }

        Player player = (Player) sender;

        player.sendMessage(SerializationUtils.serializeItem(player.getItemInHand()));
        player.getInventory().addItem(SerializationUtils.deserializeItem(SerializationUtils.serializeItem(player.getItemInHand())));

        return true;
    }
}
