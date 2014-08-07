package com.ohyea777.hardtime.block;

import com.ohyea777.hardtime.Hardtime;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BlockRegistry {

    private Map<UUID, Block> blocks;
    private Map<String, UUID> namedBlocks;

    public BlockRegistry() {
        blocks = new HashMap<UUID, Block>();
        namedBlocks = new HashMap<String, UUID>();
    }

    public void addBlock(Block block) {
        blocks.put(block.getBlockId(), block);
        namedBlocks.put(block.getBlockName().toLowerCase(), block.getBlockId());
    }

    public void addBlocks(List<Block> blocks) {
        for (Block b : blocks) {
            addBlock(b);
        }
    }

    public void addNewBlock(Block block) {
        addBlock(block);
        saveBlock(block);
    }

    public boolean hasBlock(UUID block) {
        return blocks.containsKey(block);
    }

    public boolean hasBlock(String name) {
        return namedBlocks.containsKey(name.toLowerCase()) && hasBlock(namedBlocks.get(name.toLowerCase()));
    }

    public Block getBlock(UUID blockId) {
        return hasBlock(blockId) ? blocks.get(blockId) : null;
    }

    public Block getBlock(String name) {
        return hasBlock(name) ? getBlock(namedBlocks.get(name)) : null;
    }

    public void onClose() {
        Bukkit.getScheduler().runTaskAsynchronously(Hardtime.INSTANCE, new Runnable() {

            @Override
            public void run() {
                for (Block b : blocks.values()) {
                    if (b.isModified())
                        saveBlock(b);
                }
            }

        });
    }

    public void saveBlock(Block block) {
        // TODO: Save Block - SQL???
    }

}
