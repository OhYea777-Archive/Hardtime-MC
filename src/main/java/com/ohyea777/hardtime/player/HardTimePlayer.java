package com.ohyea777.hardtime.player;

import com.ohyea777.hardtime.Hardtime;
import com.ohyea777.hardtime.cell.Cell;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HardTimePlayer extends WrappedPlayer {

    private PlayerData playerData;

    public HardTimePlayer(Player player, PlayerData playerData) {
        super(player);

        this.playerData = playerData;
    }

    public boolean hasCell() {
        return playerData.hasCell();
    }

    public UUID getCellId() {
        return playerData.getCellId();
    }

    public Cell getCell() {
        return Hardtime.INSTANCE.getCellRegistry().getCell(this);
    }

}
