package com.ohyea777.hardtime.cell;

import com.ohyea777.hardtime.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Cell {

    private Integer minX, maxX;
    private Integer minY, maxY;
    private Integer minZ, maxZ;

    private UUID world;
    private UUID owner;
    private UUID block;

    private UUID cell;

    private transient World worldObj;
    private transient OfflinePlayer playerObj;
    private transient Block blockObj;

    private transient boolean modified;

    public Cell() { }

    public Cell(UUID cell) {
        this.cell = cell;
    }

    public void init() {
        if (world != null)
            worldObj = Bukkit.getWorld(world);

        if (owner != null)
            playerObj = Bukkit.getOfflinePlayer(owner);

        // TODO: Set blockObj

        modified = false;
    }

    public Location getMinCorner() {
        if (minX != null && minY != null && minZ != null && worldObj != null) {
            return new Location(worldObj, minX, minY, minZ);
        }

        return null;
    }

    public void setMinCorner(Location location) {
        setMinX(location.getBlockX());
        setMinY(location.getBlockY());
        setMinZ(location.getBlockZ());

        setWorld(location.getWorld().getUID());
        setWorldObj(location.getWorld());
    }

    public Location getMaxCorner() {
        if (maxX != null && maxY != null && maxZ != null && worldObj != null) {
            return new Location(worldObj, maxX, maxY, maxZ);
        }

        return null;
    }

    public void setMaxCorner(Location location) {
        setMaxX(location.getBlockX());
        setMaxY(location.getBlockY());
        setMaxZ(location.getBlockZ());

        setWorld(location.getWorld().getUID());
        setWorldObj(location.getWorld());
    }

    public Integer getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;

        setModified(true);
    }

    public Integer getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;

        setModified(true);
    }

    public Integer getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;

        setModified(true);
    }

    public Integer getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;

        setModified(true);
    }

    public Integer getMinZ() {
        return minZ;
    }

    public void setMinZ(int minZ) {
        this.minZ = minZ;

        setModified(true);
    }

    public Integer getMaxZ() {
        return maxZ;
    }

    public void setMaxZ(int maxZ) {
        this.maxZ = maxZ;

        setModified(true);
    }

    public UUID getWorld() {
        return world;
    }

    public void setWorld(UUID world) {
        this.world = world;

        worldObj = Bukkit.getWorld(world);

        setModified(true);
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;

        playerObj = Bukkit.getPlayer(owner);

        setModified(true);
    }

    public UUID getBlockId() {
        return getBlockId();
    }

    public void setBlockId(UUID block) {
        this.block = block;

        // if (block == null)
        // TODO: Set Block Object

        setModified(true);
    }

    public UUID getCellId() {
        return cell;
    }

    public void setCellId(UUID cell) {
        this.cell = cell;

        setModified(true);
    }

    public World getWorldObj() {
        return worldObj;
    }

    public void setWorldObj(World worldObj) {
        this.worldObj = worldObj;

        if (world == null || world != worldObj.getUID()) {
            world = worldObj.getUID();

            setModified(true);
        }
    }

    public OfflinePlayer getPlayer() {
        return playerObj;
    }

    public void setPlayer(Player playerObj) {
        this.playerObj = playerObj;

        if (owner == null || owner != playerObj.getUniqueId()) {
            owner = playerObj.getUniqueId();

            setModified(true);
        }
    }

    public Block getBlock() {
        return blockObj;
    }

    public void setBlock(Block blockObj) {
        this.blockObj = blockObj;

        if (block == null || block != blockObj.getBlockId()) {
            block = blockObj.getBlockId();

            setModified(true);
        }
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

}
