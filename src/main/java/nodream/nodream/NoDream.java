package nodream.nodream;

import org.bukkit.plugin.java.JavaPlugin;

public final class NoDream extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Sleep sleep = new Sleep(this);
        Phantoms phantoms = new Phantoms(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
