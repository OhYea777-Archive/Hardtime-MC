package com.ohyea777.hardtime.items;

import com.ohyea777.hardtime.Hardtime;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.shininet.bukkit.itemrenamer.api.ItemsListener;
import org.shininet.bukkit.itemrenamer.api.RenamerAPI;
import org.shininet.bukkit.itemrenamer.api.RenamerPriority;
import org.shininet.bukkit.itemrenamer.api.RenamerSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRegistry implements Listener {

    private Map<String, IItem> items;

    public ItemRegistry() {
        items = new HashMap<String, IItem>();

        Hardtime.INSTANCE.getServer().getPluginManager().registerEvents(this, Hardtime.INSTANCE);

        init();
        registerPacketListener();
    }

    private void init() {
        registerItem(new SelectItem());
    }

    public void registerItem(IItem item) {
        if (!items.containsKey(strip(item.getLocalisedName()).toLowerCase())) {
            items.put(strip(item.getLocalisedName()).toLowerCase(), item);

            Hardtime.INSTANCE.getServer().getPluginManager().registerEvents(item, Hardtime.INSTANCE);
        }
    }

    public boolean containsItem(String name) {
        return items.containsKey(name.toLowerCase());
    }

    public IItem getItem(String name) {
        if (containsItem(name))
            return items.get(strip(name).toLowerCase());

        return null;
    }

    public boolean isIItem(ItemStack itemStack) {
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
            return ItemMetadata.hasMetadata(itemStack, "IItem");
        }

        return false;
    }

    public IItem getIItem(ItemStack itemStack) {
        if (isIItem(itemStack)) {
            String item = ItemMetadata.get(itemStack, "IItem");

            return getItem(item);
        }

        return null;
    }

    public List<IItem> getItems() {
        return new ArrayList<IItem>(items.values());
    }

    private String strip(String str) {
        return ChatColor.stripColor(str);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() != null && isIItem(event.getItem())) {
            IItem item = getIItem(event.getItem());

            if (item.hasPermission(event.getPlayer()))
                item.onItemUse(event.getAction(), event);
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getPlayer().getItemInHand() != null && isIItem(event.getPlayer().getItemInHand())) {
            IItem item = getIItem(event.getPlayer().getItemInHand());

            if (item.hasPermission(event.getPlayer()))
                item.onItemRightClickEntity(event);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            if (player.getItemInHand() != null && isIItem(player.getItemInHand())) {
                IItem item = getIItem(player.getItemInHand());

                if (item.hasPermission(player))
                    item.onItemLeftClickEntity(player, event);
            }
        }
    }

    private void registerPacketListener() {
        RenamerAPI.getAPI().addListener(Hardtime.INSTANCE, RenamerPriority.POST_NORMAL, new ItemsListener() {

            @Override
            public void onItemsSending(Player player, RenamerSnapshot itemStacks) {
                for (ItemStack itemStack : itemStacks) {
                    if (itemStack == null || !isIItem(itemStack))
                        continue;

                    if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
                        ItemMeta meta = itemStack.getItemMeta();
                        List<String> lore = new ArrayList<String>();

                        for (String str : meta.getLore())
                            if (!str.startsWith(ItemMetadata.PREFIX))
                                lore.add(str);

                        meta.setLore(lore);
                        itemStack.setItemMeta(meta);
                    }
                }
            }

        });
    }

}
