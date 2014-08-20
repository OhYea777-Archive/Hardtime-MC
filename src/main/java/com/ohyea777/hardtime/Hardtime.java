package com.ohyea777.hardtime;

import com.ohyea777.hardtime.block.BlockRegistry;
import com.ohyea777.hardtime.cell.CellRegistry;
import com.ohyea777.hardtime.commands.CommandRegistry;
import com.ohyea777.hardtime.items.ItemRegistry;
import com.ohyea777.hardtime.items.SelectItem;
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
    private CommandRegistry commandRegistry;

    @Override
    public void onEnable() {
        INSTANCE = this;

        if (!VaultUtils.INSTANCE.init(INSTANCE)) {
            // TODO: Something on failure of initializing @VaultUtils
        }

        saveResource("config.yml", true);

        blockRegistry = new BlockRegistry();
        cellRegistry = new CellRegistry();
        itemRegistry = new ItemRegistry();
        commandRegistry = new CommandRegistry();

        getServer().getPluginManager().registerEvents(INSTANCE, INSTANCE);
    }

    public void reload() {
        reloadConfig();
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

    public CommandRegistry getCommandRegistry() {
        return commandRegistry;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return getCommandRegistry().onCommand(sender, command, label, args);
    }
}
