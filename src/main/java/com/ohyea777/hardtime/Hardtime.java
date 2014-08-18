package com.ohyea777.hardtime;

import com.ohyea777.hardtime.block.BlockRegistry;
import com.ohyea777.hardtime.cell.CellRegistry;
import com.ohyea777.hardtime.utils.VaultUtils;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Hardtime extends JavaPlugin implements Listener {

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

        getServer().getPluginManager().registerEvents(INSTANCE, INSTANCE);
    }

    public BlockRegistry getBlockRegistry() {
        return blockRegistry;
    }

    public CellRegistry getCellRegistry() {
        return cellRegistry;
    }

}
