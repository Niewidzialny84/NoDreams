package nodream.nodream;

import nodream.nodream.Config.NoDreamConfig;
import nodream.nodream.Config.NoDreamReload;
import nodream.nodream.Config.NoDreamTabComplete;
import nodream.nodream.Phantoms.Phantoms;
import nodream.nodream.Sleep.Sleep;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class NoDream extends JavaPlugin {

    private Sleep sleep;
    private Phantoms phantoms;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        sleep = new Sleep(this);
        phantoms = new Phantoms(this);
        NoDreamConfig.loadConfig(this);

        this.getCommand("nodreams").setExecutor(new NoDreamReload(this));
        this.getCommand("nodreams").setTabCompleter(new NoDreamTabComplete());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void RegisterEvent() {

        HandlerList.unregisterAll();

        if(NoDreamConfig.isEnableSleeping()) {
            Bukkit.getPluginManager().registerEvents(sleep, this);
            this.getLogger().info("NoDream Sleeping is Enabled");
        } else {
            this.getLogger().info("NoDream Sleeping is Disabled");
        }

        if(NoDreamConfig.isNoPhantoms()) {
        Bukkit.getPluginManager().registerEvents(phantoms,this);
            this.getLogger().info("Phantoms spawning is Disabled");
            phantoms.setMobDrop(this);
        } else {
            this.getLogger().info("Phantoms spawning is Enabled");
        }
    }
}
