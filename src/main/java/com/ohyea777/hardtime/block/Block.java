package com.ohyea777.hardtime.block;

import com.ohyea777.hardtime.cell.Cell;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Block {

    private String blockName;

    private UUID blockId;
    private UUID world;

    private Integer x, y, z;

    private transient World worldObj;

    private transient Boolean modified;

    public Block() { }

    public Block(UUID blockId) {
        this.blockId = blockId;

        setModified(true);
    }

    public void init() {
        if (world != null)
            worldObj = Bukkit.getWorld(world);

        if (modified == null)
            modified = false;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;

        setModified(true);
    }

    public UUID getBlockId() {
        return blockId;
    }

    public void setBlockId(UUID blockId) {
        this.blockId = blockId;

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

    public Integer getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;

        setModified(true);
    }

    public Integer getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;

        setModified(true);
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;

        setModified(true);
    }

    public Location getSpawn() {
        if (worldObj != null && x != null && y != null && z != null) {
            return new Location(worldObj, x, y, z);
        }

        return null;
    }

    public void setSpawn(Location location) {
        setX(location.getBlockX());
        setY(location.getBlockY());
        setZ(location.getBlockZ());

        setWorld(location.getWorld().getUID());
        setWorldObj(location.getWorld());
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

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

}
