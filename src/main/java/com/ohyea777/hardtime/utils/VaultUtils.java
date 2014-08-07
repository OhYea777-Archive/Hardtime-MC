package com.ohyea777.hardtime.utils;

import com.ohyea777.hardtime.Hardtime;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;

public enum VaultUtils {

    INSTANCE;

    private Hardtime plugin;

    private Permission permissions;
    private Economy economy;
    private Chat chat;

    public boolean init(Hardtime plugin) {
        this.plugin = plugin;

        return setupPermissions() && setupEconomy() && setupChat();
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = plugin.getServer().getServicesManager().getRegistration(Permission.class);

        if (permissionProvider != null) {
            permissions = permissionProvider.getProvider();

            return true;
        } else {
            plugin.getLogger().severe("[VaultUtils] Failed to Load Permission Provider!");
        }

        return false;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager().getRegistration(Economy.class);

        if (economyProvider != null) {
            economy = economyProvider.getProvider();

            return true;
        } else {
            plugin.getLogger().severe("[VaultUtils] Failed to Load Economy Provider!");
        }

        return false;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = plugin.getServer().getServicesManager().getRegistration(Chat.class);

        if (chatProvider != null) {
            chat = chatProvider.getProvider();

            return true;
        } else {
            plugin.getLogger().severe("[VaultUtils] Failed to Load Chat Provider!");
        }

        return false;
    }

    public Permission getPermissions() {
        return permissions;
    }

    public Economy getEconomy() {
        return economy;
    }

    public Chat getChat() {
        return chat;
    }

}
