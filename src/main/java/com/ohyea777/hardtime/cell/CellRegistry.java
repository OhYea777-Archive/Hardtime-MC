package com.ohyea777.hardtime.cell;

import com.ohyea777.hardtime.player.HardTimePlayer;
import com.ohyea777.hardtime.utils.LocationUtils;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CellRegistry {

    // TODO: Add registration of cells and attaching of Blocks etc.

    private Map<UUID, Cell> cellsById;
    private Map<UUID, List<Cell>> cellsByBlocks;
    private List<Cell> cells;

    private void initCellsForBlock(UUID block) {
        if (!cellsByBlocks.containsKey(block))
            cellsByBlocks.put(block, new ArrayList<Cell>());
    }

    public void registerCell(Cell cell) {
        initCellsForBlock(cell.getBlockId());

        List<Cell> cells = cellsByBlocks.get(cell.getBlockId());

        cells.add(cell);
        cellsByBlocks.put(cell.getBlockId(), cells);
    }

    public List<Cell> getCells() {
        return cells;
    }

    public Cell getCell(HardTimePlayer player) {
        if (player.hasCell() && cellsById.containsKey(player.getCellId()))
            return cellsById.get(player.getCellId());

        return null;
    }

    public boolean isLocationInCell(Location location) {
        for (Cell cell: getCells())
            return LocationUtils.isInside(location, cell.getMinCorner(), cell.getMaxCorner());

        return false;
    }

    public Cell getCellAtLocation(Location location) {
        for (Cell cell: getCells())
            if (LocationUtils.isInside(location, cell.getMinCorner(), cell.getMaxCorner()))
                return cell;

        return null;
    }

}
