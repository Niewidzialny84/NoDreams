package nodream.nodream;

import nodream.nodream.config.MetricsLite;
import nodream.nodream.config.Config;
import nodream.nodream.config.Commands;
import nodream.nodream.config.CommandsTab;
import nodream.nodream.phantoms.Phantoms;
import nodream.nodream.sleep.Sleep;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class NoDreams extends JavaPlugin {

    private Sleep sleep;
    private Phantoms phantoms;

    @Override
    public void onEnable() {
        MetricsLite metricsLite = new MetricsLite(this);

        saveDefaultConfig();

        sleep = new Sleep(this);
        phantoms = new Phantoms(this);
        Config.loadConfig(this);

        this.getCommand("nodreams").setExecutor(new Commands(this));
        this.getCommand("nodreams").setTabCompleter(new CommandsTab());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void RegisterEvent() {

        HandlerList.unregisterAll(this);

        if(Config.isEnableSleeping()) {
            Bukkit.getPluginManager().registerEvents(sleep, this);
            this.getLogger().info("NoDreams Sleeping is Enabled");
        } else {
            this.getLogger().info("NoDreams Sleeping is Disabled");
        }

        if(Config.isNoPhantoms()) {
        Bukkit.getPluginManager().registerEvents(phantoms,this);
            this.getLogger().info("Phantoms spawning is Disabled");
            phantoms.setMobDrop();
        } else {
            this.getLogger().info("Phantoms spawning is Enabled");
        }
    }
}
