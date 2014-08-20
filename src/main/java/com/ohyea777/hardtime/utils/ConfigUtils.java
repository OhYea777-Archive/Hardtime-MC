package com.ohyea777.hardtime.utils;

import com.ohyea777.hardtime.Hardtime;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ConfigUtils {

    INSTANCE;

    private Map<String, String> locationMappings;

    private ConfigUtils() {
        locationMappings = new HashMap<String, String>();

        init();
    }

    private void init() {
        registerMapping("prefix", "Options.Prefix");
        registerMapping("help", "Options.HelpFormat");
        registerMapping("invalid command", "Messages.InvalidCommand");
        registerMapping("command help", "Messages.Help");
        registerMapping("no permission", "Messages.NoPermission");
        registerMapping("reloaded", "Messages.Reloaded");
        registerMapping("spawned item", "Messages.SpawnedItem");
        registerMapping("invalid item", "Messages.InvalidItem");
    }

    public void registerMapping(String key, String location) {
        if (!locationMappings.containsKey(key.toLowerCase()))
            locationMappings.put(key.toLowerCase(), location);
    }

    private Configuration getConfig() {
        return Hardtime.INSTANCE.getConfig();
    }

    private String keyToLocation(String key) {
        return locationMappings.containsKey(key.toLowerCase()) ? locationMappings.get(key.toLowerCase()) : key;
    }

    private String translate(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public String getHelpFormat(String name, String command) {
        return translate(getString("help", true).replace("%name%", name).replace("%command%", command));
    }

    public Object get(String key) {
        key = keyToLocation(key);

        return getConfig().get(key);
    }

    public String getString(String key, boolean formatted) {
        key = keyToLocation(key);

        if (getConfig().isString(key))
            if (formatted) {
                return translate(getString(key, false).replace("%prefix%", getString("prefix", false)));
            } else
                return getConfig().getString(key);

        return "";
    }

    public String getStringReplace(String str, String placeHolder, String key, boolean formatted) {
        return formatted ? translate(str.replace(placeHolder, getString(key, false))) : str.replace(placeHolder, getString(key, false));
    }

    public List<String> getStringList(String key, boolean formatted) {
        key = keyToLocation(key);

        if (getConfig().isList(key))
            if (formatted) {
                List<String> stringList = new ArrayList<String>();

                for (String str : getConfig().getStringList(key))
                    stringList.add(translate(str.replace("%prefix%", getString("prefix", false))));

                return stringList;
            } else
                return getConfig().getStringList(key);

        return new ArrayList<String>();
    }

    public boolean getBoolean(String key) {
        key = keyToLocation(key);

        if (getConfig().isBoolean(key))
            return getConfig().getBoolean(key);

        return false;
    }

    public int getInt(String key) {
        key = keyToLocation(key);

        if (getConfig().isInt(key))
            return getConfig().getInt(key);

        return -1;
    }

}

