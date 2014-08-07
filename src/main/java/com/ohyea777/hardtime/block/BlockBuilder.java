package com.ohyea777.hardtime.block;

import org.bukkit.Location;

import java.util.UUID;

public class BlockBuilder {

    private final Block block;

    public BlockBuilder(Block block) {
        this.block = block;
    }

    public BlockBuilder() {
        this(new Block(UUID.randomUUID()));
    }

    public BlockBuilder withBlockName(String blockName) {
        block.setBlockName(blockName);

        return this;
    }

    public BlockBuilder withBlockId(UUID blockId) {
        block.setBlockId(blockId);

        return this;
    }

    public BlockBuilder withSpawn(Location location) {
        block.setSpawn(location);

        return this;
    }

    public BlockBuildResult getBlockBuildResult() {
        if (block.getBlockName() == null) {
            return BlockBuildResult.INVALID_NAME;
        } else if (block.getBlockId() == null) {
            return BlockBuildResult.INVALID_BLOCK_ID;
        } else if (block.getSpawn() == null) {
            return BlockBuildResult.INVALID_SPAWN;
        }

        return BlockBuildResult.SUCCESS;
    }

    public Block build() {
        if (getBlockBuildResult() == BlockBuildResult.SUCCESS) {
            return block;
        }

        return null;
    }

    public enum BlockBuildResult {

        INVALID_NAME, INVALID_BLOCK_ID, INVALID_SPAWN, SUCCESS;

    }

}
