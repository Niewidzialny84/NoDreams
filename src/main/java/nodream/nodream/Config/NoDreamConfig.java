package nodream.nodream.Config;


import nodream.nodream.NoDreams;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class NoDreamConfig {

    private static boolean enableSleeping;
    private static boolean doDisplayMsg;
    private static String isSleepingMsg;
    private static String isNotSleepingMsg;
    private static String playersNeeded;
    private static String playerNeeded;
    private static String newDayMsg;
    private static int playersPercentage;

    private static boolean noPhantoms;
    private static String mobName;
    private static String reloadMsg;

    private static String valuesAre;
    private static String sleepingSetMsg;
    private static String phantomsSetMsg;
    private static String reloadHelpMsg;
    private static String sleepingHelpMsg;
    private static String phantomsHelpMsg;
    private static String warningHelpMsg;

    private static FileConfiguration config;

    private final static List<String> subCommands = new ArrayList<>();
    private final static List<String> subCommandsBool = new ArrayList<>();

    private static void load(FileConfiguration config_) {
        enableSleeping = config_.getBoolean("enableSleeping",true);
        isSleepingMsg = config_.getString("isSleepingMsg", "is sleeping.");
        isNotSleepingMsg = config_.getString("isNotSleepingMsg", "is not sleeping.");
        playersNeeded = config_.getString("playersNeeded", "players needed");
        playerNeeded = config_.getString("playerNeeded", "player needed");
        doDisplayMsg = config_.getBoolean("doDisplayMsg", true);
        newDayMsg = config_.getString("newDayMsg", "New day has come!");

        playersPercentage = config_.getInt("playersPercentage", 50);

        if (playersPercentage > 100 || playersPercentage < 0) {
            playersPercentage = 50;
        }

        noPhantoms = config_.getBoolean("noPhantoms",true);
        mobName = config_.getString("mobName", "ENDERMAN");
        reloadMsg = config_.getString("reloadMsg", "Config has been reloaded");

        valuesAre = config_.getString("valuesAre", "Values are");
        sleepingSetMsg = config_.getString("sleepingSetMsg", "Sleeping set to");
        phantomsSetMsg = config_.getString("phantomsSetMsg", "Phantoms set to");
        reloadHelpMsg = config_.getString("reloadHelpMsg", "reloads plugin including config data");
        sleepingHelpMsg = config_.getString("sleepingHelpMsg", "enables/disables sleeping feature");
        phantomsHelpMsg = config_.getString("phantomsHelpMsg",  "enables/disables phantoms spawning");
        warningHelpMsg = config_.getString("warningHelpMsg", "Warning: Changing sleeping or phantoms will have no effect after restar/reload");

        createSubCommandList();
        createSubCommandBoolList();
    }

    private static void createSubCommandList() {
        subCommands.clear();
        subCommands.add("help");
        subCommands.add("reload");
        subCommands.add("sleeping");
        subCommands.add("phantoms");
    }

    private static void createSubCommandBoolList() {
        subCommandsBool.clear();
        subCommandsBool.add("true");
        subCommandsBool.add("false");
    }

    public static void loadConfig(Plugin plugin) {
        config = plugin.getConfig();
        load(config);
        ((NoDreams)plugin).RegisterEvent();
    }

    public static boolean isEnableSleeping() {
        return enableSleeping;
    }

    public static void setEnableSleeping(boolean enableSleeping) {
        NoDreamConfig.enableSleeping = enableSleeping;
    }

    public static void setNoPhantoms(boolean noPhantoms) {
        NoDreamConfig.noPhantoms = noPhantoms;
    }

    public static List<String> getSubCommands() {
        return subCommands;
    }

    public static List<String> getSubCommandsBool() {
        return subCommandsBool;
    }

    public static boolean isDoDisplayMsg() {
        return doDisplayMsg;
    }

    public static String getIsSleepingMsg() {
        return isSleepingMsg;
    }

    public static String getIsNotSleepingMsg() {
        return isNotSleepingMsg;
    }

    public static String getPlayersNeeded() {
        return playersNeeded;
    }

    public static String getPlayerNeeded() {
        return playerNeeded;
    }

    public static String getNewDayMsg() {
        return newDayMsg;
    }

    public static int getPlayersPercentage() {
        return playersPercentage;
    }

    public static boolean isNoPhantoms() {
        return noPhantoms;
    }

    public static String getMobName() {
        return mobName;
    }

    public static String getReloadMsg() {
        return reloadMsg;
    }

    public static String getValuesAre() {
        return valuesAre;
    }

    public static String getSleepingSetMsg() {
        return sleepingSetMsg;
    }

    public static String getPhantomsSetMsg() {
        return phantomsSetMsg;
    }

    public static String getReloadHemlMsg() {
        return reloadHelpMsg;
    }

    public static String getSleepingHelpMsg() {
        return sleepingHelpMsg;
    }

    public static String getPhantomsHelpMsg() {
        return phantomsHelpMsg;
    }

    public static String getWarningHelpMsg() {
        return warningHelpMsg;
    }

}

