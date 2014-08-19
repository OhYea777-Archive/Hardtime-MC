package com.ohyea777.hardtime.cell;

import com.ohyea777.hardtime.block.Block;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CellBuilder {

    private final Cell cell;

    public CellBuilder(Cell cell) {
        this.cell = cell;
    }

    public CellBuilder() {
        cell = new Cell();

        cell.setCellId(UUID.randomUUID());
    }

    public CellBuilder withMinCorner(Location location) {
        cell.setMinCorner(location);

        if (cell.getWorld() == null)
            cell.setWorldObj(location.getWorld());

        return this;
    }

    public CellBuilder withMaxCorner(Location location) {
        cell.setMaxCorner(location);

        return this;
    }

    public CellBuilder withOwner(Player player) {
        cell.setPlayer(player);

        return this;
    }

    public CellBuilder withBlock(Block block) {
        cell.setBlock(block);

        return this;
    }

    public CellBuildResult getCellBuildResult() {
        if (cell.getMinCorner() == null) {
            return CellBuildResult.INVALID_MIN_CORNER;
        } else if (cell.getMaxCorner() == null) {
            return CellBuildResult.INVALID_MAX_CORNER;
        } else if (cell.getBlock() == null) {
            return CellBuildResult.INVALID_BLOCK;
        }

        return CellBuildResult.SUCCESS;
    }

    public Cell build() {
        if (getCellBuildResult() == CellBuildResult.SUCCESS) {
            return cell;
        }

        return null;
    }

    public enum CellBuildResult {

        INVALID_MIN_CORNER, INVALID_MAX_CORNER, INVALID_BLOCK, SUCCESS;

    }

}
